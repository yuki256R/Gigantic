package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

/**
 * @author tar0ss
 *
 */
public class ManaTableManager extends PlayerFromSeichiTableManager {

	public ManaTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists mana double default 0.0,";
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp,boolean loginflag) {
		ManaManager m = gp.getManager(ManaManager.class);
		String command = "";
		command += "mana = '" + m.getMana() + "',";
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		ManaManager m = gp.getManager(ManaManager.class);
		m.setMana(tm.getMana(gp));
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		ManaManager m = gp.getManager(ManaManager.class);
		m.setMana(0);
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		ManaManager m = gp.getManager(ManaManager.class);
		m.setMana(rs.getDouble("mana"));
	}

}
