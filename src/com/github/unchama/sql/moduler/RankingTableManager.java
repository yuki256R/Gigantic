package com.github.unchama.sql.moduler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;
import com.github.unchama.yml.DebugManager.DebugEnum;

public abstract class RankingTableManager extends TableManager {
	private List<String> columnName;

	public RankingTableManager(Sql sql) {
		super(sql);
	}


	@Override
	protected Boolean createTable() {
		String command;
		columnName = new ArrayList<String>();
		// create Table
		command = "CREATE TABLE IF NOT EXISTS " + db + "." + table + " (datetime timestamp)";
		// send
		if (!sendCommand(command)) {
			plugin.getLogger().warning("Failed to Create " + table + " Table");
			return false;
		}

		// Column add
		command = "alter table " + db + "." + table + " ";
		//UUID
		command += "add column if not exists uuid varchar(128) default null,";

		// original column
		String tmp = this.addColumnCommand(columnName);
		if(tmp != null)	command += tmp;

		command += "last";

		command = command.replace(",last", "");
		// send
		if (!sendCommand(command)) {
			plugin.getLogger().warning(
					"Failed to add Column in " + table + " Table");
			return false;
		}
		return true;
	}
	/*
	 * カラム追加コマンド
	 */
	protected abstract String addColumnCommand(List<String> columnName);

	//ランキングデータを送信する．
	public void send() {

		String command = "";
		this.checkStatement();

		command = "insert into " + db + "." + table + " (uuid,";

		for(String n : columnName){
			command += n + ",";
		}
		command += "last";

		command = command.replace(",last", ") values ");

		command += this.getValuesData();


		command += "last";

		command = command.replace(",last", "");

		if(command.contains("last")){
			return;
		}

		try {
			stmt.executeUpdate(command);
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to insert " + table);
			e.printStackTrace();
			return;
		}
		debug.info(DebugEnum.SQL, "Table:" + table + " 更新されました");
		this.reset();
	}


	protected abstract String getValuesData();


	protected abstract void reset();


	/**プレイヤのデータが利用可能になった時に呼び出される．
	 *
	 * @param gp
	 */
	public abstract void onJoin(GiganticPlayer gp);


	public abstract boolean isEmpty();



}
