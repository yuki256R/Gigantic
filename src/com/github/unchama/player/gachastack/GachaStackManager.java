package com.github.unchama.player.gachastack;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;

import de.tr7zw.itemnbtapi.NBTItem;

public class GachaStackManager extends DataManager implements UsingSql{
	private Map<GachaType, Map<Integer, Integer>> itemMap = new HashMap<GachaType, Map<Integer, Integer>>();

	public GachaStackManager(GiganticPlayer gp) {
		super(gp);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void save(Boolean loginflag) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public Map<GachaType, Map<Integer, Integer>> getMap(){
		return itemMap;
	}

	// NBTタグを見てガチャアイテムならスタックする
	public boolean add(ItemStack itemstack){
		NBTItem nbti = new NBTItem(itemstack);
		GachaType gt = GachaManager.getGachaType(nbti);
		if(gt != null){
			if(!itemMap.containsKey(gt)){
				itemMap.put(gt, new HashMap<Integer, Integer>());
			}
			Map<Integer, Integer> map = itemMap.get(gt);
			int id = GachaManager.getGachaID(nbti);
			int amount = 0;
			if(map.containsKey(id)){
				amount = map.get(id);
			}
			amount++;
			map.put(id, amount);

			return true;
		}else{
			return false;
		}
	}
}
