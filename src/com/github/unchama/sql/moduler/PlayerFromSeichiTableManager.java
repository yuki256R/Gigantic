package com.github.unchama.sql.moduler;

import java.sql.SQLException;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;

public abstract class PlayerFromSeichiTableManager extends TableManager implements GiganticLoadable{

	public PlayerFromSeichiTableManager(Sql sql) {
		super(sql);
	}

	/**ex)
	 * command = "add column if not exists name varchar(30) default null,"
	 *
	 * @return command
	 */
	protected abstract String addOriginalColumn();
	/**set new player data
	 *
	 * @param gp
	 * @return command
	 */
	protected abstract void newPlayer(GiganticPlayer gp);
	/**take over player from playerdata
	 *
	 * @param gp
	 * @param mt
	 */
	protected abstract void takeoverPlayer(GiganticPlayer gp,PlayerDataTableManager mt);
	/**ex)
	for(BlockType bt : BlockType.values()){
		double n = rs.getDouble(bt.getColumnName());
		datamap.put(bt, new MineBlock(n));
		}
	 *
	 * @param gp
	 * @throws SQLException
	 */
	protected abstract void loadPlayer(GiganticPlayer gp)throws SQLException;
	/**ex)
		for(BlockType bt : datamap.keySet()){
			i++;
			command += bt.getColumnName() + " = '" + datamap.get(bt).getNum() + "',";
		}
	 *
	 * @param gp
	 * @return
	 */
	protected abstract String savePlayer(GiganticPlayer gp);

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
		//loginflag add
		command += "add column if not exists loginflag boolean default true,";
		//original column
		command += this.addOriginalColumn();
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
 			PlayerDataTableManager tm = Gigantic.seichisql.getManager(PlayerDataTableManager.class);

 			//new uuid line create
 			command = "insert into " + db + "." + table
 	 				+ " (name,uuid) values('" + gp.name
 	 				+ "','" + struuid+ "')";
 			if(!sendCommand(command)){
 				plugin.getLogger().warning("Failed to create new row (player:" + gp.name + ")");
 				return false;
 			}
 			int existtype = tm.isExist(gp);
 			if(existtype == 1){
 				this.takeoverPlayer(gp,tm);
 			}else if(existtype == 0){
 				this.newPlayer(gp);
 			}else{
 				plugin.getLogger().warning("Failed to count player:" + gp.name + "in SeichiAssistPlayerData");
 				return false;
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
					this.loadPlayer(gp);
				}
				rs.close();
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
		this.checkStatement();

		command = "update " + db + "." + table
				+ " set loginflag = 'false',";

		command += this.savePlayer(gp);

		command += " where uuid = '" + struuid + "'";

		command = command.replace(", where uuid", " where uuid");

		try {
			stmt.executeUpdate(command);
		}catch (SQLException e) {
			plugin.getLogger().warning("Failed to update " + table + "Data of Player:" + gp.name);
			e.printStackTrace();
			return false;
		}

		return true;
	}


}
