package com.github.unchama.player.gigantic;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.GiganticTableManager;

/**
 * @author tar0ss
 *
 */
public class GiganticManager extends DataManager implements UsingSql{
	GiganticTableManager tm;

	public GiganticManager(GiganticPlayer gp){
		super(gp);
		this.tm = sql.getManager(GiganticTableManager.class);
	}

	@Override
	public void save(Boolean loginflag){
		tm.save(gp,loginflag);
	}

}
