package com.github.unchama.player.gachastack;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.GachaStackTableManager;
import com.github.unchama.util.Util;

import de.tr7zw.itemnbtapi.NBTItem;

/**
*
* @author ten_niti
*
*/
public class GachaStackManager extends DataManager implements UsingSql{
	private Gacha gacha = Gigantic.gacha;
	private Map<GachaType, Map<Integer, Integer>> itemMap = new HashMap<GachaType, Map<Integer, Integer>>();

	private GachaStackTableManager tm = sql.getManager(GachaStackTableManager.class);

	public GachaStackManager(GiganticPlayer gp) {
		super(gp);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	public Map<GachaType, Map<Integer, Integer>> getMap(){
		return itemMap;
	}
	public void setMap(Map<GachaType, Map<Integer, Integer>> map){
		itemMap = map;
	}

	// NBTタグを見てガチャアイテムならスタックする
	public boolean add(ItemStack itemstack){
		// SeichiAssist時代のガチャ券ならギガンティックガチャチケットにスタック
		if(Util.isOldGachaTicket(itemstack)){
			return addItem(itemstack, GachaType.GIGANTIC, 0);
		}

		NBTItem nbti = new NBTItem(itemstack);
		GachaType type = GachaManager.getGachaType(nbti);
		if(type != null){
			int id = GachaManager.getGachaID(nbti);
			return addItem(itemstack, type, id);
		}else{
			return false;
		}
	}

	private boolean addItem(ItemStack itemstack, GachaType type, int id){
		if(!itemMap.containsKey(type)){
			itemMap.put(type, new HashMap<Integer, Integer>());
		}
		Map<Integer, Integer> map = itemMap.get(type);

		// 参考のガチャアイテムを取得
		GachaManager gm = gacha.getManager(type.getManagerClass());
		if(!gm.getGachaItemMap().containsKey(id)){
			return false;
		}
		GachaItem gi = gm.getGachaItem(id);
		// 耐久度が減ったりしてたらアウト
		if(gi.getDurability() != itemstack.getDurability()){
			return false;
		}

		int amount = 0;
		if(map.containsKey(id)){
			amount = map.get(id);
		}
		amount += itemstack.getAmount();
		map.put(id, amount);

		return true;
	}

	// 指定のガチャアイテムの現在値を返す
	public int getAmount(GachaType type, int id){
		int ret = 0;
		if(!itemMap.containsKey(type)){
			return ret;
		}
		Map<Integer, Integer> map = itemMap.get(type);
		if(!map.containsKey(id)){
			return ret;
		}
		ret = map.get(id);
		return ret;
	}

	// アイテムの取り出し
	public boolean takeOutGachaItem(Player player,GachaType type, int id){
		if(!itemMap.containsKey(type)){
			return false;
		}
		Map<Integer, Integer> map = itemMap.get(type);
		if(!map.containsKey(id)){
			return false;
		}
		int amount = map.get(id);
		if(amount <= 0){
			return false;
		}
		GachaManager gm = gacha.getManager(type.getManagerClass());
		if(!gm.getGachaItemMap().containsKey(id)){
			return false;
		}

		// 一度にスタックできる限り取り出す
		GachaItem gi = gm.getGachaItem(id);
		ItemStack item = gi.getItem(player);
		int stackSize = item.getMaxStackSize();
		if(stackSize > amount){
			stackSize = amount;
		}
		amount -= stackSize;
		item.setAmount(stackSize);

		// アイテムの付与とスタックの減算
		Util.giveItem(player, item, true);
		map.put(id, amount);

		return true;
	}
}
