package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fishinglevel.FishingLevelManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerTableManager;

/**
*
* @author ten_niti
*
*/
public class FishingLevelTableManager extends PlayerTableManager {

	public FishingLevelTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists exp double default 0,";

		return command;
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		FishingLevelManager m = gp.getManager(FishingLevelManager.class);
		m.setExp(0);
		return false;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		FishingLevelManager m = gp.getManager(FishingLevelManager.class);
		m.setExp(rs.getDouble("exp"));
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		FishingLevelManager m = gp.getManager(FishingLevelManager.class);
		String command = "";
		command += "exp = '" + m.getExp() + "',";
		return command;
	}
}
