package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.region.RegionManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

public class RegionTableManager extends PlayerFromSeichiTableManager {

	public RegionTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists rgnum int default 0,";
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		RegionManager m = gp.getManager(RegionManager.class);
		String command = "";
		command += "rgnum = '" + m.getRgnum() + "',";
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		RegionManager m = gp.getManager(RegionManager.class);
		m.setRgnum(tm.getRgnum(gp));
	}


	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		RegionManager m = gp.getManager(RegionManager.class);
		m.setRgnum(0);
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		RegionManager m = gp.getManager(RegionManager.class);
		m.setRgnum(rs.getInt("rgnum"));
	}
}