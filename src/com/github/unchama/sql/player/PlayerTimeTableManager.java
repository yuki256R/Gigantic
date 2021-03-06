package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.time.PlayerTimeManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

/**
 * @author tar0ss
 *
 */
public class PlayerTimeTableManager extends PlayerFromSeichiTableManager {
	public PlayerTimeTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists playtick int default 0,"
				+ "add column if not exists totalidletick int default 0,"
				+ "add column if not exists totaljoin int default 0,"
				+ "add column if not exists chainjoin int default 0,"
				+ "add column if not exists lastquit datetime default null,"
				+ "add column if not exists lastcheckdate varchar(12) default null,"
				;
		return command;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		PlayerTimeManager m = gp.getManager(PlayerTimeManager.class);
		m.setPlaytick(rs.getInt("playtick"));
		m.setTotalIdletick(rs.getInt("totalidletick"));
		m.reloadSevertick();
		m.setTotalJoin(rs.getInt("totaljoin"));
		m.setChainJoin(rs.getInt("chainjoin"));
		m.lastQuitNum(rs.getString("lastquit"));
		m.setLastCheckDate(rs.getString("lastcheckdate"));
	}

	@Override
	protected String saveCommand(GiganticPlayer gp,boolean loginflag) {
		PlayerTimeManager m = gp.getManager(PlayerTimeManager.class);
		String command = "";
		command += "playtick = '" + m.getPlaytick() + "',"
				+ "totalidletick = '" + m.getTotalIdletick() + "',"
				+ "totaljoin = '" + m.getTotalJoin() + "',"
				+ "chainjoin = '" + m.getChainJoin() + "',"
				+ "lastquit =  cast( now() as datetime )" + ","
				+ "lastcheckdate = '" + m.getLastCheckDate() + "',"
				;
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		PlayerTimeManager m = gp.getManager(PlayerTimeManager.class);
		m.setPlaytick(tm.getPlayTick(gp));
		m.setTotalJoin(tm.getTotalJoin(gp));
		m.setChainJoin(tm.getChainJoin(gp));
		m.lastQuitNum(tm.getLastQuit(gp));
		m.setLastCheckDate(tm.getLastCheckDate(gp));
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		PlayerTimeManager m = gp.getManager(PlayerTimeManager.class);
		m.setPlaytick(0);
		m.setLocation(null);
		m.setIdletime(0);
		m.setTotalIdletick(0);
		m.setTotalJoin(0);
		m.setChainJoin(0);
		m.lastQuitNum(null);
	}

	// 指定nameのプレイヤーのlastquitをセレクト
	public String Lastquit(String name) {
		this.checkStatement();
		String lastquit = "";
		String command = "select lastquit from " + db + "." + table
				+ " where name = '" + name + "'";
		// 保存されているデータをロード
		try {
			rs = stmt.executeQuery(command);
			while(rs.next()) {
				   lastquit = rs.getString("lastquit");
				}
			rs.close();
			return lastquit;
		}
		catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to multiload in " + table + " Table");
			e.printStackTrace();
		}

		return null;
	}
}
