package com.github.unchama.sql;

import com.github.unchama.player.mineblock.BlockType;

public class MineBlockTableManager extends TableManager{


	public MineBlockTableManager(){
		super();
		
	}

	@Override
	Boolean createTable() {
		String command;
		//create Table
		command =
				"CREATE TABLE IF NOT EXISTS "
				+ this.getDataBaseName() + "." + this.getTableName();
		//Unique Column add
		command += "(uuid varchar(128) unique)";
		//send
		if(!sendCommand(command)){
			plugin.getLogger().warning("Failed to Create " + this.getTableName() + " Table");
			return false;
		}

		//Column add
		command =
				"alter table " + this.getDataBaseName() + "." + this.getTableName() + " ";
		//name add
		command += "add column if not exists name varchar(30) default null";
		//MineBlock add
		for(BlockType bt : BlockType.values()){
			command += ",add column if not exists " + 
						bt.name() + " bigint unsigned default 0";
		}
		//index add
		command += ",add index if not exists uuid_index(uuid)";

		//send
		if(!sendCommand(command)){
			plugin.getLogger().warning("Failed to add Column in " + this.getTableName() + " Table");
			return false;
		}
		return true;
	}
}
