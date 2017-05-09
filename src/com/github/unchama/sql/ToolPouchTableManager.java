package com.github.unchama.sql;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.toolpouch.ToolPouchManager;
import com.github.unchama.sql.moduler.PlayerTableManager;
import com.github.unchama.util.BukkitSerialization;

public class ToolPouchTableManager extends PlayerTableManager {

	public ToolPouchTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists pouchdata blob default null,";
		return command;
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		ToolPouchManager m = gp.getManager(ToolPouchManager.class);
		Inventory pouch = Bukkit.getServer().createInventory(null, m.getSize(),
				m.getName());
		m.setPouch(pouch);
		return true;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		ToolPouchManager m = gp.getManager(ToolPouchManager.class);
		Inventory pouch;
		try {
			pouch = BukkitSerialization.getInventoryfromBase64(rs.getString("pouchdata").toString());
			m.setPouch(pouch);
			m.resize();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		ToolPouchManager m = gp.getManager(ToolPouchManager.class);
		String command = "";

		command += "pouchdata = '" + BukkitSerialization.toBase64(m.getPouch()) + "',";

		return command;
	}

}
