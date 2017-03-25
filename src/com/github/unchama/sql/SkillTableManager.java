package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.skill.Explosion;
import com.github.unchama.player.skill.SkillManager;
import com.github.unchama.player.skill.moduler.BreakRange;
import com.github.unchama.player.skill.moduler.Coordinate;
import com.github.unchama.player.skill.moduler.Volume;
import com.github.unchama.sql.moduler.PlayerTableManager;

public class SkillTableManager extends PlayerTableManager {

	public SkillTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		// Explosion
		command += "add column if not exists ex_width int default 1,"
				+ "add column if not exists ex_depth int default 1,"
				+ "add column if not exists ex_height int default 1,"
				+ "add column if not exists ex_zero_x int default 0,"
				+ "add column if not exists ex_zero_y int default 0,"
				+ "add column if not exists ex_zero_z int default 0,"
				;

		return command;
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {

		SkillManager sm = gp.getManager(SkillManager.class);

		// Explosion
		Explosion ex = sm.getSkill(Explosion.class);
		ex.setRange(new BreakRange());

		return true;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		SkillManager sm = gp.getManager(SkillManager.class);

		// Explosion
		Explosion ex = sm.getSkill(Explosion.class);
		ex.setRange(new BreakRange(
				new Volume(rs.getInt("ex_width"), rs.getInt("ex_depth"), rs.getInt("ex_height")),
				new Coordinate(rs.getInt("ex_zero_x"), rs.getInt("ex_zero_y"), rs.getInt("ex_zero_z"))
		));
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		SkillManager sm = gp.getManager(SkillManager.class);
		String command = "";
		// Explosion
		Explosion ex = sm.getSkill(Explosion.class);
		BreakRange range = ex.getRange();
		command += "ex_width = '" + range.getVolume().getWidth() + "',"
				+ "ex_depth = '" + range.getVolume().getDepth() + "',"
				+ "ex_height = '" + range.getVolume().getHeight() + "',"
				+ "ex_zero_x = '" + range.getZeropoint().getX() + "',"
				+ "ex_zero_y = '" + range.getZeropoint().getY() + "',"
				+ "ex_zero_z = '" + range.getZeropoint().getZ() + "',";

		return command;
	}

}
