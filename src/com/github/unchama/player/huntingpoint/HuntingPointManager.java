package com.github.unchama.player.huntingpoint;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.HuntingPointTableManager;

public class HuntingPointManager extends DataManager implements UsingSql {


	HuntingPointTableManager hm = sql.getManager(HuntingPointTableManager.class);

	public HuntingPointManager(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public void save(Boolean loginflag) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
