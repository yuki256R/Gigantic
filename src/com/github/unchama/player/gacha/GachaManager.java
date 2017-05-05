package com.github.unchama.player.gacha;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.PlayerGachaTableManager;

public class GachaManager extends DataManager implements Initializable,UsingSql{
	PlayerGachaTableManager ptm;

	public GachaManager(GiganticPlayer gp) {
		super(gp);
		ptm = sql.getManager(PlayerGachaTableManager.class);
	}

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void save(Boolean loginflag) {
		ptm.save(gp, loginflag);
	}

}
