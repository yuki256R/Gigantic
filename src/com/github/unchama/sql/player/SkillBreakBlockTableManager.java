package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.SkillBreakBlockManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerTableManager;

public class SkillBreakBlockTableManager extends PlayerTableManager{

	public SkillBreakBlockTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";

		for (ActiveSkillType type : ActiveSkillType.values()) {
			String typename = type.toString();

			command += "add column if not exists " + typename + " double default 0.0,";
		}
		return command;
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		SkillBreakBlockManager bbmanager = gp.getManager(SkillBreakBlockManager.class);
		bbmanager.startup();
		return false;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		HashMap<ActiveSkillType, Double> setmap = new HashMap<>();

		for (ActiveSkillType type : ActiveSkillType.values()) {
			setmap.put(type, rs.getDouble(type.toString()));
		}
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		SkillBreakBlockManager bbmanager = gp.getManager(SkillBreakBlockManager.class);
		HashMap<ActiveSkillType, Double> savemap = bbmanager.getNumMap();

		String command = "";

		for (ActiveSkillType type : ActiveSkillType.values()) {
			if (savemap.containsKey(type)) {
				String typename = type.toString();

				command += typename + " = '" + savemap.get(typename) + "',";
			}
		}
		return command;
	}



}
