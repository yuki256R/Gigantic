package com.github.unchama.player.gigantic;

import com.github.unchama.player.DataManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.UsingSql;
import com.github.unchama.sql.GiganticTableManager;

public class GiganticManager extends DataManager implements UsingSql{
	GiganticTableManager tm;

	public GiganticManager(GiganticPlayer gp){
		super(gp);
		this.tm = sql.getManager(GiganticTableManager.class);
	}

	@Override
	public void save(){
		tm.save(gp);
	}

	@Override
	public void load() {
		tm.load(gp);
	}
}
