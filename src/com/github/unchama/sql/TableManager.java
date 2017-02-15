package com.github.unchama.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.unchama.gigantic.Gigantic;



public abstract class TableManager {
	protected Gigantic plugin = Gigantic.plugin;
	private Sql sql;
	private String db;
	private Connection con;
	private Statement stmt;
	private String tablename;

	public TableManager(){
		sql = Gigantic.sql;
		db = sql.getDataBaseName();
		con = sql.getConnection();
		this.tablename = Sql.TableManagerType.getTableNamebyClass(this.getClass());
		sql.checkConnection();
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			plugin.getLogger().warning("Unknow Error:TableManager");
			e.printStackTrace();
		}
		this.createTable();
	}
	
	abstract Boolean createTable();
	
	protected String getTableName(){
		return this.tablename;
	}
	
	protected void checkStatement(){
		try {
			if(stmt.isClosed()){
				plugin.getLogger().warning("Statement is Closed. Creating Statement...");
				stmt = con.createStatement();
			}
		} catch (SQLException e) {
			plugin.getLogger().warning("Statement is Closed by Fatal Error. ReConnecting MySql...");
			sql.checkConnection();
			e.printStackTrace();
		}
	}
	
	protected String getDataBaseName(){
		return this.db;
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
			plugin.getLogger().warning("Failed to send Command in " + this.getTableName() + " Table");
			e.printStackTrace();
			return false;
		}
	}





	/*
	public static boolean isSaved(UUID uuid) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}


	public static GiganticPlayer loadGiganticPlayer(UUID uuid) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


	public static void saveGiganticPlayer(UUID uuid, GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public static void saveGiganticPlayer(HashMap<UUID, GiganticPlayer> gmap) {
		// TODO 自動生成されたメソッド・スタブ

	}
	*/

}
