package com.github.unchama.player;

import com.github.unchama.sql.GiganticTableManager;

public class GiganticManager extends DataManager{
	GiganticTableManager tm;

	public GiganticManager(GiganticPlayer gp){
		super(gp);
		this.tm = sql.getGiganticTableManager();
		if(!tm.load(gp.uuid)){
			plugin.getLogger().warning("Failed to load Gigantic of player:" + gp.name);
		}
	}
}
