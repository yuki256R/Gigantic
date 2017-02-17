package com.github.unchama.sql;

import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;


public class GiganticTableManager extends TableManager{

	public GiganticTableManager(Sql sql){
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
		command += "add column if not exists name varchar(30) default null";
		/*//loginflag add
		command += ",add column if not exists loginflag boolean default false";
		*/
		//index add
		command += ",add index if not exists uuid_index(uuid)";

		//send
		if(!sendCommand(command)){
			plugin.getLogger().warning("Failed to add Column in " + table + " Table");
			return false;
		}
		return true;
	}

	@Override
	public Boolean load(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int count = -1;
 		//uuidがsqlデータ内に存在するか検索
 		//command:
 		//select count(*) from playerdata where uuid = 'struuid'
 		command = "select count(*) as count from " + db + "." + table
 				+ " where uuid = '" + struuid + "'";
 		//sqlコネクションチェック(mysql接続が切れたときの為のフェイルセーフ機構(ダメならリログすれば直る))
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
 			//uuidが存在しない時の処理

 			//新しくuuidとnameを設定し行を作成
 			//insert into playerdata (name,uuid) VALUES('unchima','UNCHAMA')
 			command = "insert into " + db + "." + table
 	 				+ " (name,uuid) values('" + gp.name
 	 				+ "','" + struuid+ "')";
 			if(!sendCommand(command)){
 				plugin.getLogger().warning("Failed to create new row (player:" + gp.name + ")");
 				return false;
 			}
 			/*
 			//初見さんにLv1メッセージを送信
 			p.sendMessage(SeichiAssist.config.getLvMessage(1));
 			//初見さんであることを全体告知
 			plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "【初見キタ】" + p.getName() + "のプレイヤーデータ作成完了");
 			Util.sendEveryMessage(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+name+"さんは初参加です。整地鯖へヨウコソ！" + ChatColor.RESET +" - " + ChatColor.YELLOW + ChatColor.UNDERLINE +  "http://seichi.click");
 			Util.sendEverySound(Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
 			//初見プレイヤーに木の棒、エリトラ、ピッケルを配布
 			p.getInventory().addItem(new ItemStack(Material.STICK));
 			p.getInventory().addItem(new ItemStack(Material.ELYTRA));
 			p.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
 			p.getInventory().addItem(new ItemStack(Material.DIAMOND_SPADE));
 			MebiusListener.give(p);
 			*/
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
 			return true;
 		}else{
 			//mysqlに該当するplayerdataが2個以上ある時エラーを吐く
 			plugin.getLogger().warning("Failed to read count (player:" + gp.name + ")");
 			return false;
 		}
	}

	@Override
	public Boolean save(GiganticPlayer gp) {
		//String command = "";
		//final String struuid = gp.uuid.toString().toLowerCase();

		//no data that should be saved
		return true;
	}


}
