package com.github.unchama.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.ConfigManager;

public class Sql{
	static enum TableManagerType{
		GIGANTIC(GiganticTableManager.class),
		MINEBLOCK(MineBlockTableManager.class),
		;
		private Class<? extends TableManager> managerClass;

		TableManagerType(Class<? extends TableManager> managerClass){
			this.managerClass = managerClass;
		}

		public Class<? extends TableManager> getManagerClass(){
			return managerClass;
		}
		/**sqlのテーブル名を取得する
		 *
		 * @return
		 */
		public String getTableName(){
			return this.name().toLowerCase();
		}

		public static String getTableNamebyClass(Class<? extends TableManager> _class) {
			for(TableManagerType mt : TableManagerType.values()){
				if(mt.getManagerClass().equals(_class)){
					return mt.getTableName();
				}
			}
			return "example";
		}
	}
	Gigantic plugin;
	ConfigManager config;
	private final String url;
	private final String db;
	private final String id;
	private final String pw;
	private Connection con = null;
	private Statement stmt = null;

	private HashMap<Class<? extends TableManager>,TableManager> managermap = new HashMap<Class<? extends TableManager>,TableManager>();


	//コンストラクタ
	public Sql(){
		this.plugin = Gigantic.plugin;
		this.config = Gigantic.yml.getManager(ConfigManager.class);
		this.url = config.getURL();
		this.db = config.getDB();
		this.id = config.getID();
		this.pw = config.getPW();


		//SQL接続，データベース作成
		if(!init()){
			plugin.getLogger().warning("データベース初期処理にエラーが発生しました");
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	}


	@SuppressWarnings("unchecked")
	public <T extends TableManager> T getManager(Class<T> type){
		return (T) managermap.get(type);
	}
	/**
	 * 接続関数
	 *
	 * @param url 接続先url
	 * @param id ユーザーID
	 * @param pw ユーザーPW
	 * @param db データベースネーム
	 * @return
	 */


	//初期処理を行う
	private boolean init(){
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

		//Table
		if(!createTableManager()){
			plugin.getLogger().warning("テーブルマネージャーの初期化に失敗しました");
			return false;
		}

		return true;
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
	private boolean createDB(){
		if(db==null){
			return false;
		}
		String command;
		command = "CREATE DATABASE IF NOT EXISTS " + db
				+ " character set utf8 collate utf8_general_ci";
		return sendCommand(command);
	}
	/**createtableStatement
	 *
	 * @return
	 */
	private boolean createTableManager(){
		if(!this.managermap.isEmpty() || this.managermap != null){
			managermap.clear();
		}
		//各テーブル用メソッドに受け渡し
		for(TableManagerType mt : TableManagerType.values()){
			try {
				this.managermap.put(mt.getManagerClass(),mt.getManagerClass().getConstructor(Sql.class).newInstance(this));
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**コマンド出力関数
	 *
	 * @param command
	 * @return
	 */
	private boolean sendCommand(String command){
		checkConnection();
		try {
			stmt.executeUpdate(command);
			return true;
		}catch (SQLException e) {
			plugin.getLogger().warning("sqlクエリの実行に失敗しました。以下にエラーを表示します");
			e.printStackTrace();
			return false;
		}
	}
	/**再接続処理
	 *
	 */
	private void reconnectMySQL() {
		int maxcount = 5;
		int count = 0;

		do{
			plugin.getLogger().warning("Reconnecting...(" + Integer.toString(count) + "times)...");
			this.connectMySQL();
			count++;
		}while(count < maxcount || this.isClosed());

		boolean endflag = false;
		if(this.isClosed()){
			plugin.getLogger().warning("再接続処理に失敗しました");
			endflag = true;
		}

		if(endflag){
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	}

	/**コネクションのクローズ判定
	 *
	 * @return 可否
	 */
	private boolean isClosed() {
		try {
			if(con.isClosed()){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			plugin.getLogger().warning("Connection is Closed by Fatal Error");
			e.printStackTrace();
			return true;
		}
	}

	/**接続確認
	 *
	 * @return
	 */
	public void checkConnection(){
		if(this.isClosed()){
			reconnectMySQL();
		}
		try {
			if(stmt == null || stmt.isClosed()){
				plugin.getLogger().warning("Statement is Closed. Creating Statement...");
				stmt = con.createStatement();
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	plugin.getPluginLoader().disablePlugin(plugin);
		}
	}
	/**
	 * コネクション切断処理
	 *
	 * @return 成否
	 */
	public boolean disconnect(){
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

	/**
	 *
	 * @return Connection
	 */
	public Connection getConnection(){
		return con;
	}

	public String getDataBaseName(){
		return this.db;
	}



}
