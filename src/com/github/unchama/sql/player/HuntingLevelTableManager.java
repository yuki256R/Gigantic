package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntinglevel.HuntingLevelManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerTableManager;

public class HuntingLevelTableManager extends PlayerTableManager {

	public HuntingLevelTableManager(Sql sql) {
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
		HuntingLevelManager m = gp.getManager(HuntingLevelManager.class);
		m.setExp(0);
		return false;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		HuntingLevelManager m = gp.getManager(HuntingLevelManager.class);
		m.setExp(rs.getDouble("exp"));
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		HuntingLevelManager m = gp.getManager(HuntingLevelManager.class);
		String command = "";
		command += "exp = '" + m.getExp() + "',";
		return command;
	}
}
