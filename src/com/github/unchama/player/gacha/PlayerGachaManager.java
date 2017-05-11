package com.github.unchama.player.gacha;

import java.util.LinkedHashMap;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.PlayerGachaTableManager;

public class PlayerGachaManager extends DataManager implements UsingSql{
	PlayerGachaTableManager ptm;
	private LinkedHashMap<GachaType,GachaData> dataMap;

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
	public void give(GachaType gt, int i) {
		dataMap.get(gt).give(i);
	}

}
