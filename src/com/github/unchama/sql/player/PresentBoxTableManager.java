package com.github.unchama.sql.player;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.presentbox.PresentBoxManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;
import com.github.unchama.util.BukkitSerialization;
import com.github.unchama.util.InventoryUtil;

/**
*
* @author ten_niti
*
*/
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
	protected String saveCommand(GiganticPlayer gp,boolean loginflag) {
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
		PresentBoxManager m = gp.getManager(PresentBoxManager.class);
		m.createInventory();
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		PresentBoxManager m = gp.getManager(PresentBoxManager.class);
		m.createInventory();
		Inventory inventory;
		try {
			inventory = BukkitSerialization.getInventoryfromBase64(rs
					.getString("inventory").toString());
			m.addInventory(inventory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 全てのUUIDを取得する
	public List<String> getUUIDs() {
		List<String> ret = null;
		this.checkStatement();
		String command = "select uuid from " + db + "." + table + " where 1";
		// 保存されているデータをロード
		try {
			ret = new ArrayList<String>();
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				// uuidを取得
				ret.add(rs.getString("uuid"));
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to multiload in " + table + " Table");
			e.printStackTrace();
		}
		return ret;
	}

	// 指定のUUIDのプレイヤーのプレゼントボックスにアイテムを追加する（オフラインのプレイヤーにのみ）
	public boolean addItem(String uuid, ItemStack item) {
		Inventory inventory = null;
		this.checkStatement();
		String command = "select inventory from " + db + "." + table
				+ " where uuid = '" + uuid + "'";
		// 保存されているデータをロード
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				inventory = BukkitSerialization.getInventoryfromBase64(rs
						.getString("inventory").toString());
			}
			rs.close();

			if (inventory == null) {
				return false;
			}

			// インベントリにアイテムを追加
			boolean isAddSuccess = InventoryUtil.addItemStack(inventory,
					item, true);
			if (!isAddSuccess) {
				return false;
			}

			command = "update " + db + "." + table + " set inventory = '"
					+ BukkitSerialization.toBase64(inventory) + "' "
					+ "where uuid = '" + uuid + "'";
			if (!sendCommand(command)) {
				plugin.getLogger()
						.warning(
								"Failed to multi create new row in " + table
										+ " Table");
			} else {
				return true;
			}
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to multiload in " + table + " Table");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
