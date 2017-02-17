package com.github.unchama.player.gigantic;

import com.github.unchama.player.DataManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.GiganticTableManager;

public class GiganticManager extends DataManager{
	GiganticTableManager tm;

	public GiganticManager(GiganticPlayer gp){
		super(gp);
		this.tm = sql.getGiganticTableManager();
	}

	@Override
	public void save() {
		tm.save(gp);
	}

	@Override
	public void load() {
		tm.load(gp);
	}
}
