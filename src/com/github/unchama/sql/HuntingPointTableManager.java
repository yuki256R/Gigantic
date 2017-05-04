package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;
import com.github.unchama.yml.HuntingPointDataManager;

public class HuntingPointTableManager extends PlayerFromSeichiTableManager {
	HuntingPointDataManager huntingPointData = Gigantic.yml
			.getManager(HuntingPointDataManager.class);

	// 現在値
	private final String currentFoot = "_current";
	// 累計値
	private final String totalFoot = "_max";

	public HuntingPointTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		List<String> names = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		for (String name : names) {
			command += "add column if not exists " + name + currentFoot
					+ " int default 0,";
			command += "add column if not exists " + name + totalFoot
					+ " int default 0,";
		}

		return command;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		HuntingPointManager m = gp.getManager(HuntingPointManager.class);
		List<String> names = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		for (String name : names) {
			m.setCurrentPoint(name, rs.getInt(name + currentFoot));
			m.setTotalPoint(name, rs.getInt(name + totalFoot));
		}
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		HuntingPointManager m = gp.getManager(HuntingPointManager.class);
		String command = "";
		List<String> names = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		for (String name : names) {
			command += name + currentFoot + " = '" + m.getCurrentPoint(name)
					+ "',";
			command += name + totalFoot + " = '" + m.getTotalPoint(name) + "',";
		}
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		// Giganticからの実装
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		HuntingPointManager m = gp.getManager(HuntingPointManager.class);
		List<String> names = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		for (String name : names) {
			m.setCurrentPoint(name, 0);
			m.setTotalPoint(name, 0);
		}
	}

}
