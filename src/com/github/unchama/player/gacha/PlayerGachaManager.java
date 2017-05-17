package com.github.unchama.player.gacha;

import java.util.LinkedHashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.PlayerGachaTableManager;
import com.github.unchama.util.Util;

public class PlayerGachaManager extends DataManager implements Initializable,UsingSql{
	Gacha gacha = Gigantic.gacha;
	PlayerGachaTableManager ptm;
	private LinkedHashMap<GachaType,GachaData> dataMap;
	MineBlockManager Mm;

	public PlayerGachaManager(GiganticPlayer gp) {
		super(gp);
		ptm = sql.getManager(PlayerGachaTableManager.class);
		dataMap = new LinkedHashMap<GachaType,GachaData>();
	}

	@Override
	public void save(Boolean loginflag) {
		ptm.save(gp, loginflag);
	}

	/**
	 * @return dataMap
	 */
	public LinkedHashMap<GachaType,GachaData> getDataMap() {
		return dataMap;
	}

	/**ガチャ券を与える
	 *
	 * @param ガチャの種類
	 * @param 与える数
	 */
	public void add(GachaType gt, int i) {
		dataMap.get(gt).add(i);
	}

	/**持っているガチャ券の数を取得する
	 *
	 * @param gigantic
	 * @return
	 */
	public long getTicket(GachaType gt) {
		return dataMap.get(gt).getTicket();
	}

	/**プレイヤーにガチャ券を付与します．
	 *
	 * @param player
	 * @param gt
	 * @param 64以上の値を入れることはできません．
	 */
	public void give(Player player, GachaType gt, int i) {
		if(i > 64)return;
		ItemStack ts = gacha.getManager(gt.getManagerClass()).getGachaTicket();
		ts.setAmount(i);
		Util.giveItem(player, ts,true);
		dataMap.get(gt).remove(i);
	}

	/**ガチャ券獲得までの残りブロック数を取得します
	 *
	 * @param gigantic
	 * @return
	 */
	public int getRemainingBlock(GachaType gt) {
		return 1000 - (int)(Mm.getAll(TimeType.UNLIMITED) % 1000);
	}

	@Override
	public void init() {
		Mm = gp.getManager(MineBlockManager.class);
	}

	/**ガチャを回します．
	 *
	 * @param ガチャの種類
	 */
	public ItemStack roll(GachaType gt) {
		GachaManager gm = gacha.getManager(gt.getManagerClass());
		GachaItem gi = gm.roll();
		dataMap.get(gt).use(gi.getRarity());
		return gi.getItem();

	}


}
