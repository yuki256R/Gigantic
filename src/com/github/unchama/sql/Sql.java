package com.github.unchama.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.unchama.enumdata.TableEnum;
import com.github.unchama.gigantic.Config;
import com.github.unchama.gigantic.Gigantic;

public class Sql{
	private Gigantic plugin;
	private Config config;
	private final String url;
	private final String db;
	private final String id;
	private final String pw;

	public static String exc;
	public static Connection con = null;
	private Statement stmt = null;

	private ResultSet rs = null;

	//コンストラクタ
	public Sql(){
		this.plugin = Gigantic.plugin;
		this.config = Gigantic.config;
		this.url = config.getURL();
		this.db = config.getDB();
		this.id = config.getID();
		this.pw = config.getPW();

		if(!init()){
			plugin.getLogger().warning("データベース初期処理にエラーが発生しました");
		}
	}


	/**
	 * 接続関数
	 *
	 * @param url 接続先url
	 * @param id ユーザーID
	 * @param pw ユーザーPW
	 * @param db データベースネーム
	 * @param table テーブルネーム
	 * @return
	 */


	//初期処理を行う
	public boolean init(){
		//ドライバーインスタンス生成
		if(!createDriverInstance()){
			plugin.getLogger().warning("Mysqlドライバーのインスタンス生成に失敗しました");
			return false;
		}

		//sql鯖への接続
		if(!connectMySQL()){
			plugin.getLogger().warning("SQL接続に失敗しました");
			return false;
		}

		//DB作成
		if(!createDB()){
			plugin.getLogger().warning("データベース作成に失敗しました");
			return false;
		}

		//TABLE作成
		if(!createTable()){
			plugin.getLogger().warning("テーブル作成に失敗しました");
			return false;
		}

		//必要データは読み込み
		if(!initloadTable()){
			plugin.getLogger().warning("テーブルの初期ロードに失敗しました");
			return false;
		}

		return true;
	}
	/**初期処理時でのテーブル読み込み
	 *playerdataはオンラインのプレイヤーについて読み込み処理
	 *gachadata及び
	 *msgachadataは常に初期読み込み
	 *
	 * @return
	 */
	private boolean initloadTable() {
		//全てのテーブルの読み込み処理を行う
		for(TableEnum table : TableEnum.values()){
			if(table.getInitLoadFlag()){
			}
		}
		return false;
	}


	/**
	 * ドライバーインスタンス生成
	 *
	 * @return 成否
	 */
	private boolean createDriverInstance(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * SQL接続
	 *
	 * @return 成否
	 */
	private boolean connectMySQL(){
		try {
			if(stmt != null && !stmt.isClosed()){
				stmt.close();
				con.close();
			}
			con = (Connection) DriverManager.getConnection(url, id, pw);
			stmt = con.createStatement();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return false;
		}
		return true;
	}

	/**
	 * データベース作成
	 *
	 * @return 成否
	 */
	public boolean createDB(){
		if(db==null){
			return false;
		}
		String command;
		command = "CREATE DATABASE IF NOT EXISTS " + db
				+ " character set utf8 collate utf8_general_ci";
		return putCommand(command);
	}
	/**コマンド出力関数
	 *
	 * @param command
	 * @return
	 */
	private boolean putCommand(String command){
		checkConnection();
		try {
			stmt.executeUpdate(command);
			return true;
		}catch (SQLException e) {
			plugin.getLogger().warning("sqlクエリの実行に失敗しました。以下にエラーを表示します");
			exc = e.getMessage();
			e.printStackTrace();
			return false;
		}
	}
	/**接続確認
	 *
	 * @return 成否
	 */
	//接続正常ならtrue、そうでなければ再接続試行後正常でtrue、だめならfalseを返す
	public boolean checkConnection(){
		try {
			if(con.isClosed()){
				plugin.getLogger().warning("sqlConnectionクローズを検出。再接続試行");
				con = (Connection) DriverManager.getConnection(url, id, pw);
			}
			if(stmt.isClosed()){
				plugin.getLogger().warning("sqlStatementクローズを検出。再接続試行");
				stmt = con.createStatement();
				//connectDB();
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	//イクセプションった時に接続再試行
	    	plugin.getLogger().warning("sqlExceptionを検出。再接続試行");
	    	if(connectMySQL()){
	    		plugin.getLogger().info("sqlコネクション正常");
	    		return true;
	    	}else{
	    		plugin.getLogger().warning("sqlコネクション不良を検出");
	    		return false;
	    	}
		}
		return true;
	}

	/**全テーブル生成
	 *
	 * @return
	 */
	public boolean createTable(){
		String command;
		//全てのテーブルを作成する
		for(TableEnum table : TableEnum.values()){
			//テーブル生成コマンド
			command =
					"CREATE TABLE IF NOT EXISTS "
					+ db + "." + table.getTableName();
			//ユニークカラムの作成コマンド追加
			command += table.getUniqueCreateCommand();
			//一度コマンドを送信
			if(!putCommand(command)){
				plugin.getLogger().warning(table.getTableName() + "テーブル<作成>に失敗しました．");
				return false;
			}

			//テーブルのカラムを追加するコマンド
			command =
					"alter table " + db + "." + table.getTableName() + " ";
			//カラム追加情報の入力コマンド
			command += table.getColumnDataCommand();

			if(!putCommand(command)){
				plugin.getLogger().warning(table.getTableName() + "テーブル<カラム追加>に失敗しました．");
				return false;
			}
		}
		return true;
	}
	/**
	 * コネクション切断処理
	 *
	 * @return 成否
	 */
	private boolean disconnect(){
	    if (con != null){
	    	try{
	    		stmt.close();
				con.close();
	    	}catch (SQLException e){
	    		e.printStackTrace();
	    		return false;
	    	}
	    }
	    return true;
	}

	public void onDisable() {
		//sql切断
		if(!disconnect()){
			plugin.getLogger().warning("データベース切断に失敗しました");
		}

	}
}
