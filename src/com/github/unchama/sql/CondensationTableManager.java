package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.active.CondensationManager;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.sql.moduler.PlayerTableManager;

public class CondensationTableManager extends PlayerTableManager{

	public CondensationTableManager(Sql sql) {
		super(sql);
	}
	@Override
	protected String addColumnCommand() {
		String command = "";
		// Condensation
		command += "add column if not exists width int default 1,"
				+ "add column if not exists depth int default 1,"
				+ "add column if not exists height int default 1,"
				+ "add column if not exists zero_x int default 0,"
				+ "add column if not exists zero_y int default 0,"
				+ "add column if not exists zero_z int default 0,"
				+ "add column if not exists unlocked boolean default false,"
				;

		return command;
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		CondensationManager m = gp.getManager(CondensationManager.class);
		Volume v = m.getDefaultVolume();
		Coordinate c = new Coordinate((v.getWidth() - 1) / 2,v.getHeight() - 1,(v.getDepth() - 1) / 2);
		m.setRange(new BreakRange(v,c));
		return true;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		CondensationManager m = gp.getManager(CondensationManager.class);
		m.setRange(new BreakRange(
				new Volume(rs.getInt("width"), rs.getInt("depth"), rs.getInt("height")),
				new Coordinate(rs.getInt("zero_x"), rs.getInt("zero_y"), rs.getInt("zero_z"))
		));

		m.unlocked(rs.getBoolean("unlocked"));

	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		String command = "";
		// Condensation
		CondensationManager m = gp.getManager(CondensationManager.class);
		BreakRange range = m.getRange();
		command += "width = '" + range.getVolume().getWidth() + "',"
				+ "depth = '" + range.getVolume().getDepth() + "',"
				+ "height = '" + range.getVolume().getHeight() + "',"
				+ "zero_x = '" + range.getZeropoint().getX() + "',"
				+ "zero_y = '" + range.getZeropoint().getY() + "',"
				+ "zero_z = '" + range.getZeropoint().getZ() + "',"
				+ "unlocked = " + Boolean.toString(m.isunlocked()) + ",";

		return command;
	}
}
