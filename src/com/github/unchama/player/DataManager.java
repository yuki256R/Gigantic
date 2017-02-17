package com.github.unchama.player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.Sql;

public abstract class DataManager{
	protected Sql sql = Gigantic.sql;
	protected Gigantic plugin = Gigantic.plugin;
	protected GiganticPlayer gp;

	protected DataManager(GiganticPlayer gp){
		this.gp = gp;
	}

	public abstract void save();
	public abstract void load();
}
