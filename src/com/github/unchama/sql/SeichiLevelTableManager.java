package com.github.unchama.sql;

import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichi.SeichiLevelManager;

public class SeichiLevelTableManager extends PlayerTableManager{

	public SeichiLevelTableManager(Sql sql) {
		super(sql);
	}
	@Override
	String addOriginalColumn() {
		String command = "";
		//level add
		command += "add column if not exists level int default 0,";

		return command;
	}

	@Override
	void insertNewPlayer(GiganticPlayer gp){
		SeichiLevelManager m = gp.getSeichiManager();
		m.setSeichiLevel(0);
	}

	@Override
	void loadPlayer(GiganticPlayer gp) throws SQLException {
		SeichiLevelManager m = gp.getSeichiManager();
		m.setSeichiLevel(rs.getInt("level"));
	}

	@Override
	String savePlayer(GiganticPlayer gp) {
		SeichiLevelManager m = gp.getSeichiManager();
		String command = "";
		command += "level = '" + m.getSeichiLevel().getLevel() + "',";
		return command;
	}

}
