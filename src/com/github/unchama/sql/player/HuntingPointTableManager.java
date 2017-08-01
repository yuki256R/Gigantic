package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;
import com.github.unchama.yml.HuntingPointDataManager;
import com.github.unchama.yml.HuntingPointDataManager.HuntMobData;

/**
*
* @author ten_niti
*
*/
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
		Map<String, HuntMobData> names = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		for (String name : names.keySet()) {
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
		Map<String, HuntMobData> names = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		for (String name : names.keySet()) {
			m.addCurrent(name, rs.getInt(name + currentFoot));
			m.addTotal(name, rs.getInt(name + totalFoot));
		}
	}

	@Override
	protected String saveCommand(GiganticPlayer gp,boolean loginflag) {
		HuntingPointManager m = gp.getManager(HuntingPointManager.class);
		String command = "";
		Map<String, HuntMobData> names = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		for (String name : names.keySet()) {
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
		Map<String, HuntMobData> names = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		for (String name : names.keySet()) {
			m.addCurrent(name, 0);
			m.addTotal(name, 0);
		}
	}

}
