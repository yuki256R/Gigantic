package com.github.unchama.sql.moduler;

import java.io.IOException;
import java.sql.SQLException;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.Sql;

public abstract class GachaTableManager extends TableManager {
	private Gacha gacha = Gigantic.gacha;
	private GachaManager gm;

	public GachaTableManager(Sql sql) {
		super(sql);
		this.gm = gacha.getManager(GachaType.getManagerClassbyTable(this.getClass()));
		load();
	}

	/**
	 * データをロードする．
	 *
	 */
	private void load() {
		String command = "";
		this.checkStatement();
		// select * from table
		command = "select * from " + db + "." + table;

		// 保存されているデータをロード
		try {
			rs = stmt.executeQuery(command);
			gm.load(rs);
			rs.close();
		} catch (SQLException  | IOException e) {
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
		command += "add column if not exists itemstack blob default null,";

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

}
