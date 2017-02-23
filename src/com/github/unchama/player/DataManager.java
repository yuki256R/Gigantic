package com.github.unchama.player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.Sql;
import com.github.unchama.yml.ConfigManager;

public abstract class DataManager{
	protected Sql sql = Gigantic.sql;
	protected Gigantic plugin = Gigantic.plugin;
	protected ConfigManager config = Gigantic.yml.getConfigManager();
	protected GiganticPlayer gp;

	protected DataManager(GiganticPlayer gp){
		this.gp = gp;
	}

}
