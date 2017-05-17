package com.github.unchama.sql.moduler;

import com.github.unchama.sql.Sql;

public abstract class RankingTableManager extends TableManager {

	public RankingTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected Boolean createTable() {
		String command;
		// create Table
		command = "CREATE TABLE IF NOT EXISTS " + db + "." + table;
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

}
