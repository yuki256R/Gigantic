package com.github.unchama.sql;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.BlockType;

public class SeichiTableManager extends TableManager{

	public SeichiTableManager(Sql sql) {
		super(sql);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	Boolean createTable() {
		String command;
		//create Table
		command =
				"CREATE TABLE IF NOT EXISTS "
				+ db + "." + table;
		//Unique Column add
		command += "(uuid varchar(128) unique)";
		//send
		if(!sendCommand(command)){
			plugin.getLogger().warning("Failed to Create " + table + " Table");
			return false;
		}

		//Column add
		command =
				"alter table " + db + "." + table + " ";
		//name add
		command += "add column if not exists name varchar(30) default null,";
		//level add
		command += "add column if not exists level int default 0,";
		//index add
		command += "add index if not exists uuid_index(uuid)";

		//send
		if(!sendCommand(command)){
			plugin.getLogger().warning("Failed to add Column in " + table + " Table");
			return false;
		}
		return true;
	}

	@Override
	public Boolean load(GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Boolean save(GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
