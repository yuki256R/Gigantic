package com.github.unchama.sql.moduler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;
import com.github.unchama.task.GiganticLoadTaskRunnable;

/**
 * 通常のプレイヤーデータのセーブ・ロードをする場合はこのクラスを継承してください．
 * SeichiAssistからのデータを引き継ぐ場合はPlayerFromSeichiTableManagerを継承してください
 *
 * @author tar0ss
 *
 */
public abstract class PlayerTableManager extends TableManager implements
		GiganticLoadable {

	public PlayerTableManager(Sql sql) {
		super(sql);
	}

	/**
	 * ex) command = "add column if not exists name varchar(30) default null,"
	 *
	 * @return command
	 */
	protected abstract String addOriginalColumn();

	/**
	 * set new player data
	 *
	 * @param gp
	 * @return 失敗：false
	 */
	protected abstract boolean newPlayer(GiganticPlayer gp);

	/**
	 * ex) for(BlockType bt : BlockType.values()){ double n =
	 * rs.getDouble(bt.getColumnName()); datamap.put(bt, new MineBlock(n)); }
	 *
	 * @param gp
	 * @throws SQLException
	 */
	public abstract void loadPlayer(GiganticPlayer gp , ResultSet rs) throws SQLException;

	/**
	 * ex) for(BlockType bt : datamap.keySet()){ i++; command +=
	 * bt.getColumnName() + " = '" + datamap.get(bt).getNum() + "',"; }
	 *
	 * @param gp
	 * @return
	 */
	protected abstract String savePlayer(GiganticPlayer gp);

	@Override
	Boolean createTable() {
		String command;
		// create Table
		command = "CREATE TABLE IF NOT EXISTS " + db + "." + table;
		// Unique Column add
		command += "(uuid varchar(128) unique)";
		// send
		if (!sendCommand(command)) {
			plugin.getLogger().warning("Failed to Create " + table + " Table");
			return false;
		}

		// Column add
		command = "alter table " + db + "." + table + " ";
		// name add
		command += "add column if not exists name varchar(30) default null,";
		// loginflag add
		command += "add column if not exists loginflag boolean default true,";
		// original column
		command += this.addOriginalColumn();
		// index add
		command += "add index if not exists uuid_index(uuid)";

		// send
		if (!sendCommand(command)) {
			plugin.getLogger().warning(
					"Failed to add Column in " + table + " Table");
			return false;
		}
		return true;
	}

	@Override
	public Boolean load(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int count = this.isExist(gp);

		if (count == 0) {
			// uuid is not exist
			// new uuid line create
			command = "insert into " + db + "." + table
					+ " (name,uuid) values('" + gp.name + "','" + struuid
					+ "')";
			if (!sendCommand(command)) {
				plugin.getLogger().warning(
						"Failed to create new row (player:" + gp.name + ")");
				return false;
			}
			if (!this.newPlayer(gp))
				return false;
			return true;

		} else if (count == 1) {
			// uuidが存在するときの処理
			new GiganticLoadTaskRunnable(this,gp).runTaskTimerAsynchronously(plugin, 20, 20);
		} else {
			// mysqlに該当するplayerdataが2個以上ある時エラーを吐く
			plugin.getLogger().warning(
					"Failed to read count (player:" + gp.name + ")");
			return false;
		}
		return true;
	}

	/**tableにデータが保存されているとき1，されていないとき0を，データの取得ができなかった場合は-1を返します
	 *
	 * @param gp
	 * @return
	 * @throws SQLException
	 */
	private int isExist(GiganticPlayer gp){
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int count = -1;
		command = "select count(*) as count from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();

		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				count = rs.getInt("count");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

		if(count != 0 && count != 1){
			return -1;
		}else{
			return count;
		}


	}

	@Override
	public Boolean save(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		this.checkStatement();

		command = "update " + db + "." + table + " set loginflag = 'false',";

		command += this.savePlayer(gp);

		command += " where uuid = '" + struuid + "'";

		command = command.replace(", where uuid", " where uuid");

		try {
			stmt.executeUpdate(command);
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to update " + table + "Data of Player:" + gp.name);
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
