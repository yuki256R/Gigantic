package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.inventory.InventoryManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerTableManager;
import com.github.unchama.util.BukkitSerialization;
import com.github.unchama.yml.DebugManager.DebugEnum;

public final class InventoryTableManager extends PlayerTableManager {

	public InventoryTableManager(Sql sql) {
		super(sql);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists itemlist blob default null,";
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		InventoryManager m = gp.getManager(InventoryManager.class);
		String command = "";
		command += "itemlist = '"
				+ BukkitSerialization.toBase64(m.getItemList()) + "',";
		m.resetPlayerInventory();
		return command;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		InventoryManager m = gp.getManager(InventoryManager.class);
		List<ItemStack> itemlist;
		itemlist = BukkitSerialization.getItemStackListfromBase64(rs
				.getString("itemlist").toString());
		m.setPlayerInventory(itemlist);
		debug.sendMessage(PlayerManager.getPlayer(gp), DebugEnum.SQL, "インベントリーをロードしました．");
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		return true;
	}

}
