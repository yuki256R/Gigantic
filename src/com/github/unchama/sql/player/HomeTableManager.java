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
		//s1～s7 + deb(8,9) までのカラムを作成
		for(int i = 1 ; i <= 9 ; i++) {
			command += "add column if not exists homepoint_" + i + " blob default null,";
			command += "add column if not exists homename_" + i + " blob default null,";
		}
		//config.getServerNum()でぬるぽするため回避

		//サーバ数が増えた時のため
		//command += "add column if not exists homepoint_" + config.getServerNum() + " blob default null,";
		//command += "add column if not exists homename_" + config.getServerNum() + " blob default null,";

		return command;
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		HomeManager m = gp.getManager(HomeManager.class);

		for( int x = 0 ; x < config.getSubHomeMax() ; x++){
			m.setHomePoint(null, x);
			m.setHomeName("ホームポイント" + (x+1), x);
		}
		m.setChangeName(false);
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		HomeManager m = gp.getManager(HomeManager.class);

		m.SetHome(tm.getHomePoint(gp, config.getServerNum()));

		for( int x = 0 ; x < config.getSubHomeMax() ; x++){
			m.setHomeName("ホームポイント" + (x+1), x);
		}
		m.setChangeName(false);
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		HomeManager m = gp.getManager(HomeManager.class);
		if(rs.getString("homepoint_" + config.getServerNum()) != null)
			m.SetHome(rs.getString("homepoint_" + config.getServerNum()));
		else
			for( int x = 0 ; x < config.getSubHomeMax() ; x++)
				m.setHomePoint(null, x);
		if(rs.getString("homename_" + config.getServerNum()) != null)
			m.setName(rs.getString("homename_" + config.getServerNum()));
		else
			for( int x = 0 ; x < config.getSubHomeMax() ; x++)
				m.setHomeName("ホームポイント" + (x+1), x);
		m.setChangeName(false);
	}


	@Override
	protected String saveCommand(GiganticPlayer gp,boolean loginflag) {
		HomeManager m = gp.getManager(HomeManager.class);
		String command = "";
		command += "homepoint_" + config.getServerNum() + " = '" + m.HomeToString() + "',";
		command += "homename_" + config.getServerNum() + " = '" + m.getName() + "',";
		return command;
	}
}