package com.github.unchama.sql.dimensionalinventory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.dimensionalinventory.DimensionalInventoryManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;
import com.github.unchama.util.BukkitSerialization;

public class DimensionalInventoryTableManager extends PlayerFromSeichiTableManager{

	public DimensionalInventoryTableManager(Sql sql) {
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
		DimensionalInventoryManager m = gp.getManager(DimensionalInventoryManager.class);
		String command = "";

		command += "inventory = '" + BukkitSerialization.toBase64(m.getInventory()) + "',";

		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		DimensionalInventoryManager m = gp.getManager(DimensionalInventoryManager.class);
		m.SetInventory(tm.getInventory(gp));
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		DimensionalInventoryManager m = gp.getManager(DimensionalInventoryManager.class);
		Inventory inventory = Bukkit.getServer().createInventory(null, m.getSize(),
				m.getName());
		m.SetInventory(inventory);
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		DimensionalInventoryManager m = gp.getManager(DimensionalInventoryManager.class);
		Inventory inventory;
		try {
			inventory = BukkitSerialization.getInventoryfromBase64(rs.getString("inventory").toString());
			m.SetInventory(inventory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
