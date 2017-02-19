package com.github.unchama.player.seichi;

import com.github.unchama.player.DataManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.SeichiLevelTableManager;

public class SeichiLevelManager extends DataManager{
	private SeichiLevel seichilevel;
	SeichiLevelTableManager tm;

	protected SeichiLevelManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getSeichiTableManager();
	}

	@Override
	public void save() {
		tm.save(gp);
	}

	@Override
	public void load() {
		tm.load(gp);
	}

	public void setSeichiLevel(int level){
		this.seichilevel = SeichiLevel.get(level);
	}

	public SeichiLevel getSeichiLevel() {
		return this.seichilevel;
	}
}
