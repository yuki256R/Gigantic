package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.skill.ExplosionManager;
import com.github.unchama.player.skill.moduler.BreakRange;
import com.github.unchama.player.skill.moduler.Coordinate;
import com.github.unchama.player.skill.moduler.Volume;
import com.github.unchama.sql.moduler.PlayerTableManager;

public class ExplosionTableManager extends PlayerTableManager{

	public ExplosionTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		// Explosion
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
		ExplosionManager m = gp.getManager(ExplosionManager.class);
		m.setRange(new BreakRange());
		return true;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		ExplosionManager m = gp.getManager(ExplosionManager.class);
		m.setRange(new BreakRange(
				new Volume(rs.getInt("width"), rs.getInt("depth"), rs.getInt("height")),
				new Coordinate(rs.getInt("zero_x"), rs.getInt("zero_y"), rs.getInt("zero_z"))
		));

		m.unlocked(rs.getBoolean("unlocked"));

	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		String command = "";
		// Explosion
		ExplosionManager m = gp.getManager(ExplosionManager.class);
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
