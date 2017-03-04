package com.github.unchama.player.achievement;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.AchievementTableManager;

public class AchievementManager extends DataManager implements UsingSql,
		Initializable {
	AchievementTableManager tm;

	protected AchievementManager(GiganticPlayer gp) {
		super(gp);
		this.tm = sql.getManager(AchievementTableManager.class);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
