package com.github.unchama.sql;

import java.sql.SQLException;
import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.BlockType;
import com.github.unchama.player.mineblock.MineBlock;

public class MineBlockTableManager extends TableManager{

	public MineBlockTableManager(Sql sql){
		super(sql);
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
		//MineBlock add
		for(BlockType bt : BlockType.values()){
			command += "add column if not exists " +
						bt.getColumnName() + " double unsigned default 0,";
		}
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

		HashMap<BlockType,MineBlock> datamap = gp.getMineBlockManager().datamap;

		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int count = -1;

 		command = "select count(*) as count from " + db + "." + table
 				+ " where uuid = '" + struuid + "'";

 		this.checkStatement();
 		try{
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				   count = rs.getInt("count");
				  }
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning("Failed to count player:" + gp.name);
			e.printStackTrace();
			return false;
		}

 		if(count == 0){
 			//uuid is not exist

 			//new uuid line create
 			command = "insert into " + db + "." + table
 	 				+ " (name,uuid) values('" + gp.name
 	 				+ "','" + struuid+ "')";
 			if(!sendCommand(command)){
 				plugin.getLogger().warning("Failed to create new row (player:" + gp.name + ")");
 				return false;
 			}

 			//datamap put
 			for(BlockType bt : BlockType.values()){
 				datamap.put(bt, new MineBlock());
 			}
 			return true;

 		}else if(count == 1){
 			//uuidが存在するときの処理
 			//update name
 			command = "update " + db + "." + table
 					+ " set name = '" + gp.name + "'"
 					+ " where uuid like '" + struuid + "'";
 			if(!sendCommand(command)){
 				plugin.getLogger().warning("Failed to update name (player:" + gp.name + ")");
 				return false;
 			}

 			command = "select * from " + db + "." + table
 					+ " where uuid = '" + struuid + "'";
			try {
				rs = stmt.executeQuery(command);
				while (rs.next()) {
					for(BlockType bt : BlockType.values()){
						double n = rs.getDouble(bt.getColumnName());
						datamap.put(bt, new MineBlock(n));
					}
				}
			} catch (SQLException e) {
				plugin.getLogger().warning("Failed to read count (player:" + gp.name + ")");
				e.printStackTrace();
				return false;
			}
 			return true;
 		}else{
 			//mysqlに該当するplayerdataが2個以上ある時エラーを吐く
 			plugin.getLogger().warning("Failed to read count (player:" + gp.name + ")");
 			return false;
 		}
	}


	@Override
	public Boolean save(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		HashMap<BlockType,MineBlock> datamap = gp.getMineBlockManager().datamap;
		int size = datamap.size();
		int i = 0;
		this.checkStatement();

		command = "update " + db + "." + table
				+ " set ";
		for(BlockType bt : datamap.keySet()){
			i++;
			command += bt.getColumnName() + " = '" + datamap.get(bt).getNum() + "'";
			if(i != size){
				command += ",";
			}
		}
		command += " where uuid = '" + struuid + "'";

		try {
			stmt.executeUpdate(command);
		}catch (SQLException e) {
			plugin.getLogger().warning("Failed to update MineBlock Data of Player:" + gp.name);
			e.printStackTrace();
			return false;
		}

		return true;
	}


}

















