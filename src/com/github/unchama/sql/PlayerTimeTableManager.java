package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.time.PlayerTimeManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;


public class PlayerTimeTableManager extends PlayerFromSeichiTableManager{
    public PlayerTimeTableManager(Sql sql) {
        super(sql);
    }

	@Override
	protected String addColumnCommand() {
		String command = "";
        command += "add column if not exists playtick int default 0,"
        		+ "add column if not exists totalidletick int default 0,";
		return command;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		PlayerTimeManager m = gp.getManager(PlayerTimeManager.class);
		m.playtick = rs.getInt("playtick");
		m.totalidletick = rs.getInt("totalidletick");
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		PlayerTimeManager m = gp.getManager(PlayerTimeManager.class);
        String command = "";
        command += "playtick = '" + m.playtick + "',"
        		+ "totalidletick = '" + m.totalidletick + "',";
        return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		PlayerTimeManager m = gp.getManager(PlayerTimeManager.class);
		m.playtick = tm.getPlayTick(gp);
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		PlayerTimeManager m = gp.getManager(PlayerTimeManager.class);
		m.servertick = 0;
		m.playtick = 0;
		m.loc = null;
		m.totalidletick = 0;
		m.idletime = 0;
		m.playtick = 0;
	}
}
