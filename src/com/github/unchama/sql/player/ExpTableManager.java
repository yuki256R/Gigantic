package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.exp.ExpManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

public final class ExpTableManager extends PlayerFromSeichiTableManager {

	public ExpTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists exp REAL default 0,"
				;

		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		String command = "";
		ExpManager m = gp.getManager(ExpManager.class);
		command += "exp = '" + m.getExp() + "',";
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		ExpManager m = gp.getManager(ExpManager.class);
		m.setExp(tm.getExp(gp));
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		ExpManager m = gp.getManager(ExpManager.class);
		m.setExp(0.0F);
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		ExpManager m = gp.getManager(ExpManager.class);
		m.setExp(rs.getFloat("exp"));
	}

}
