package com.github.unchama.sql;

import com.github.unchama.sql.moduler.TableManager;

public class GachaTableManager extends TableManager{

	public GachaTableManager(Sql sql) {
		super(sql);
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
		// name add
		//command += "add column if not exists name varchar(30) default null,";
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
