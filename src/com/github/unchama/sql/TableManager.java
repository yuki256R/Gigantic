package com.github.unchama.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.unchama.gigantic.Gigantic;



public class TableManager {
	private Gigantic plugin = Gigantic.plugin;
	private Sql sql;
	private Connection con;
	private Statement stmt;

	public TableManager(){
		sql = Gigantic.sql;
		con = sql.getConnection();
		sql.checkConnection();
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			plugin.getLogger().warning("Unknow Error:TableManager");
			e.printStackTrace();
		}
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
