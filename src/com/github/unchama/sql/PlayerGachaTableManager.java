package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gacha.GachaData;
import com.github.unchama.player.gacha.PlayerGachaManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class PlayerGachaTableManager extends PlayerFromSeichiTableManager {

	public PlayerGachaTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		// MineBlock add
		for (GachaType gt : GachaType.values()) {
			command += "add column if not exists statistic_" + gt.name()
					+ "_ticket bigint default 0,";
			command += "add column if not exists " + gt.name()
					+ "_ticket bigint default 0,";
			command += "add column if not exists " + gt.name()
					+ "_apolo bigint default 0,";
		}
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		PlayerGachaManager m = gp.getManager(PlayerGachaManager.class);
		LinkedHashMap<GachaType, GachaData> dataMap = m.getDataMap();
		String command = "";
		for (Entry<GachaType, GachaData> e : dataMap.entrySet()) {
			GachaType gt = e.getKey();
			GachaData gd = e.getValue();
			command += "statistic_" + gt.name() + "_ticket = '"
					+ gd.getStatisticTicket() + "',";
			command += gt.name() + "_ticket = '" + gd.getTicket() + "',";
			if (gd.isReceived()) {
				command += gt.name() + "_apolo = " + gt.name() + "_apolo - "
						+ gd.getApologizeTicket() + ",";
			}
		}
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		PlayerGachaManager m = gp.getManager(PlayerGachaManager.class);
		LinkedHashMap<GachaType, GachaData> dataMap = m.getDataMap();
		for (GachaType gt : GachaType.values()) {
			if (gt.equals(GachaType.GIGANTIC)) {
				int ticket = tm.getGachaTicket(gp);
				int apolo = tm.getSorryForBugs(gp);
				dataMap.put(gt, new GachaData(ticket, ticket, apolo));
			} else {
				dataMap.put(gt, new GachaData(0, 0, 0));
			}
		}
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		PlayerGachaManager m = gp.getManager(PlayerGachaManager.class);
		LinkedHashMap<GachaType, GachaData> dataMap = m.getDataMap();
		for (GachaType gt : GachaType.values()) {
			dataMap.put(gt, new GachaData(0, 0, 0));
		}
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		PlayerGachaManager m = gp.getManager(PlayerGachaManager.class);
		LinkedHashMap<GachaType, GachaData> dataMap = m.getDataMap();
		for (GachaType gt : GachaType.values()) {
			dataMap.put(
					gt,
					new GachaData(rs.getLong("statistic_" + gt.name()
							+ "_ticket"), rs.getLong(gt.name() + "_ticket"), rs
							.getLong(gt.name() + "_apolo")));
		}
	}

	/**詫び券を付与する．
	 *
	 * @param gt
	 * @param num
	 * @param name
	 * @return 成功したときtrue
	 */
	public boolean updateApologizeTicket(GachaType gt, int num, String name) {
		String command = "";
		this.checkStatement();

		command = "update " + db + "." + table + " set " + gt.name()
				+ "_apolo = " + gt.name() + "_apolo + " + num;

		if (!name.equals("all")) {
			command += " where name = '" + name + "'";
		}

		try {
			stmt.executeUpdate(command);
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to update ApologizeTicket" + table
							+ " Data of Player:" + name);
			e.printStackTrace();
			return false;
		}
		debug.info(DebugEnum.SQL, "Table:" + table + " " + name + "の詫び券データを"
				+ num + "加算");

		return true;
	}

}
