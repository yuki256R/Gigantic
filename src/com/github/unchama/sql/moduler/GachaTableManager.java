package com.github.unchama.sql.moduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.Sql;
import com.github.unchama.util.BukkitSerialization;

public abstract class GachaTableManager extends TableManager {
	private Gacha gacha = Gigantic.gacha;
	private GachaManager gm;

	public GachaTableManager(Sql sql) {
		super(sql);
		this.gm = gacha.getManager(GachaType.getManagerClassbyTable(this
				.getClass()));
		load();
	}

	/**
	 * データをロードする．
	 *
	 */
	public void load() {
		String command = "";
		this.checkStatement();
		// select * from table
		command = "select * from " + db + "." + table;

		// 保存されているデータをロード
		try {
			rs = stmt.executeQuery(command);
			gm.load(rs);
			rs.close();
		} catch (SQLException | IOException e) {
			plugin.getLogger().warning(
					"Failed to load gacha in " + table + " Table");
			e.printStackTrace();
		}
	}

	@Override
	protected Boolean createTable() {
		String command;
		// create Table
		command = "CREATE TABLE IF NOT EXISTS " + db + "." + table;
		// Unique Column add
		command += "(id int unique)";
		// send
		if (!sendCommand(command)) {
			plugin.getLogger().warning("Failed to Create " + table + " Table");
			return false;
		}

		// Column add
		command = "alter table " + db + "." + table + " ";

		command += "add column if not exists itemstack blob default null,";
		command += "add column if not exists amount int default 1,";
		command += "add column if not exists rarity int default 0,";
		command += "add column if not exists probability double default 0.0,";
		command += "add column if not exists locked bit default false,";

		// index add
		command += "add index if not exists id_index(id)";

		// send
		if (!sendCommand(command)) {
			plugin.getLogger().warning(
					"Failed to add Column in " + table + " Table");
			return false;
		}
		return true;
	}

	/**成功したときtrueを返す
	 *
	 * @return
	 */
	public boolean save() {

		// まずmysqlのガチャテーブルを初期化(中身全削除)
		String command = "truncate table " + db + "." + table;
		if (!sendCommand(command)) {
			return false;
		}

		command = "insert into " + db + "." + table
				+ " (id,itemstack,amount,rarity,probability,locked)"
				+ " values";
		// 次に現在のgachadatalistでmysqlを更新
		for (Map.Entry<Integer, GachaItem> entry : gm.getGachaItemMap()
				.entrySet()) {
			int id = entry.getKey();
			GachaItem gi = entry.getValue();
			ItemStack is = gi.getItem();
			command += "(" + Integer.toString(id) + "," + "'"
					+ BukkitSerialization.toBase64(is) + "'," + gi.getAmount()
					+ "," + gi.getRarity().getId() + "," + gi.getProbability()
					+ "," + gi.isLocked() + "),";
		}

		command += "last";

		command = command.replaceAll(",last", "");

		if (!sendCommand(command)) {
			plugin.getLogger().warning(
					"Failed to save in " + table + " Table");
			return false;
		}

		return true;
	}

}
