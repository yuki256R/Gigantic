package com.github.unchama.sql.player;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.presentbox.PresentBoxManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;
import com.github.unchama.util.BukkitSerialization;

public class PresentBoxTableManager extends PlayerFromSeichiTableManager {

	public PresentBoxTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists inventory blob default null,";
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		PresentBoxManager m = gp.getManager(PresentBoxManager.class);
		String command = "";
		command += "inventory = '"
				+ BukkitSerialization.toBase64(m.getInventory()) + "',";

		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		PresentBoxManager m = gp.getManager(PresentBoxManager.class);
		m.createInventory();
		m.addInventory(tm.getShareInv(gp).toArray(new ItemStack[0]));
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {

	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		PresentBoxManager m = gp.getManager(PresentBoxManager.class);
		m.createInventory();
		Inventory inventory;
		try {
			inventory = BukkitSerialization.getInventoryfromBase64(rs.getString("inventory").toString());
			m.addInventory(inventory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
