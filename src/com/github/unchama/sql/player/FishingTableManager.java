package com.github.unchama.sql.player;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fishing.FishingManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerTableManager;
import com.github.unchama.util.BukkitSerialization;

/**
*
* @author ten_niti
*
*/
public class FishingTableManager extends PlayerTableManager {

	public FishingTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists IdleFishingCount int default 0,"
				+ "add column if not exists ActiveFishingCount int default 0,"
				+ "add column if not exists CoolerBox blob default null,"
				;
		return command;
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		FishingManager m = gp.getManager(FishingManager.class);
		m.setIdleFishingCount(0);
		m.setActiveFishingCount(0);
		Inventory inventory = Bukkit.getServer().createInventory(null, m.getSize(),
				m.getName());
		m.SetInventory(inventory);
		return true;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		FishingManager m = gp.getManager(FishingManager.class);
		m.setIdleFishingCount(rs.getInt("IdleFishingCount"));
		m.setActiveFishingCount(rs.getInt("ActiveFishingCount"));
		Inventory inventory;
		try {
			String str = rs.getString("CoolerBox");
			if(str != null){
				inventory = BukkitSerialization.getInventoryfromBase64(str.toString());
			}else{
				inventory = Bukkit.getServer().createInventory(null, m.getSize(),
						m.getName());
			}
			m.SetInventory(inventory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		FishingManager m = gp.getManager(FishingManager.class);
		String command = "";

		command += "IdleFishingCount = '" + m.getIdleFishingCount() + "',"
				+ "ActiveFishingCount = '" + m.getActiveFishingCount() + "',"
				+ "CoolerBox = '" + BukkitSerialization.toBase64(m.getCoolerBox()) + "',";

		return command;
	}


}
