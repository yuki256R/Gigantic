package com.github.unchama.seichi.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.ConfigManager;

public class SeichiAssistSql{
	static enum SeichiTableManagerType{
		PLAYERDATA(PlayerDataTableManager.class),
		//DONATEDATA,
		//MSGACHADATA,
		//GACHADATA,
		;
		private Class<? extends SeichiTableManager> managerClass;

		SeichiTableManagerType(Class<? extends SeichiTableManager> managerClass){
			this.managerClass = managerClass;
		}

		public Class<? extends SeichiTableManager> getManagerClass(){
			return managerClass;
		}
		/**sqlのテーブル名を取得する
		 *
		 * @return
		 */
		public String getTableName(){
			return this.name().toLowerCase();
		}

		public static String getTableNamebyClass(Class<? extends SeichiTableManager> class1) {
			for(SeichiTableManagerType mt : SeichiTableManagerType.values()){
				if(mt.getManagerClass().equals(class1)){
					return mt.getTableName();
				}
			}
			return "example";
		}
	}
	Gigantic plugin;
	ConfigManager config;
	private String url;
	private String db;
	private String id;
	private String pw;
	private Connection con = null;
	private Statement stmt = null;

	private HashMap<Class<? extends SeichiTableManager>,SeichiTableManager> managermap = new HashMap<Class<? extends SeichiTableManager>,SeichiTableManager>();

	//コンストラクタ
	public SeichiAssistSql(){
		this.plugin = Gigantic.plugin;
		this.config = Gigantic.yml.getManager(ConfigManager.class);
		this.url = config.getSeichiURL();
		this.db = config.getSeichiDB();
		this.id = config.getSeichiID();
		this.pw = config.getSeichiPW();


		//SQL接続，データベース作成
		if(!init()){
			plugin.getLogger().warning("SeichiAssistデータベース初期処理にエラーが発生しました");
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends SeichiTableManager> T getManager(Class<T> type){
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

		//sql鯖への接続
		if(!connectMySQL()){
			plugin.getLogger().warning("SQL接続に失敗しました");
			return false;
		}
		//Table
		if(!createTableManager()){
			plugin.getLogger().warning("SeichiAssistテーブルマネージャーの初期化に失敗しました");
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
	/**createtableStatement
	 *
	 * @return
	 */
	private boolean createTableManager(){
		if(!this.managermap.isEmpty() || this.managermap != null){
			managermap.clear();
		}
		//各テーブル用メソッドに受け渡し
		for(SeichiTableManagerType mt : SeichiTableManagerType.values()){
			try {
				this.managermap.put(mt.getManagerClass(),mt.getManagerClass().getConstructor(SeichiAssistSql.class).newInstance(this));
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
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
