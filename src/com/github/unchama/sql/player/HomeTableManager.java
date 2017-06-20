package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.home.HomeManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;
import com.github.unchama.yml.ConfigManager;

/**
 *
 * @author yuki_256
 *
 */
public class HomeTableManager extends PlayerFromSeichiTableManager {

	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	public HomeTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists homepoint blob default null,";
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		HomeManager m = gp.getManager(HomeManager.class);
		String command = "";
		command += "homepoint = '" + m.HomeToString() + "',";
		return command;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		HomeManager m = gp.getManager(HomeManager.class);
		m.SetHome(rs.getString("homepoint"));
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		HomeManager m = gp.getManager(HomeManager.class);

		for(int i = 1 ; i <= 7 ; i++){
			m.SeichiFromSetHome(tm.getHomePoint(gp, i), i);
		}
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
	}
}