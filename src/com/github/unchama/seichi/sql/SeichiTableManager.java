package com.github.unchama.seichi.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.unchama.gigantic.Gigantic;



public abstract class SeichiTableManager {
	protected Gigantic plugin = Gigantic.plugin;
	private SeichiAssistSql sql;
	protected final String db;
	private Connection con;
	protected Statement stmt = null;
	protected ResultSet rs;
	protected final String table;

	public SeichiTableManager(SeichiAssistSql sql){
		this.sql = sql;
		this.db = sql.getDataBaseName();
		this.con = sql.getConnection();
		this.table = SeichiAssistSql.SeichiTableManagerType.getTableNamebyClass(this.getClass());
		this.checkStatement();
	}


	protected void checkStatement(){
		try {
			if(stmt == null || stmt.isClosed()){
				plugin.getLogger().warning("(" + table + ")Statement is Closed. Creating Statement...");
				stmt = con.createStatement();
			}
		} catch (SQLException e) {
			plugin.getLogger().warning("Statement is Closed by Fatal Error. ReConnecting MySql...");
			sql.checkConnection();
			e.printStackTrace();
		}
	}


	/**コマンド出力関数
	 *
	 * @param command
	 * @return
	 */
	protected boolean sendCommand(String command){
		checkStatement();
		try {
			stmt.executeUpdate(command);
			return true;
		}catch (SQLException e) {
			plugin.getLogger().warning("Failed to send Command in " + table + " Table");
			e.printStackTrace();
			return false;
		}
	}

}
