package com.github.unchama.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.achievement.AchievementManager;
import com.github.unchama.player.gigantic.GiganticManager;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.seichiskill.CondensationManager;
import com.github.unchama.player.seichiskill.ExplosionManager;
import com.github.unchama.player.seichiskill.MagicDriveManager;
import com.github.unchama.sql.moduler.PlayerTableManager;
import com.github.unchama.sql.moduler.TableManager;
import com.github.unchama.yml.ConfigManager;

public class Sql {
	//TableManagerとそれに対応するDataManagerClass
	public static enum ManagerType {
		GIGANTIC(GiganticTableManager.class,GiganticManager.class),
		MINEBLOCK(MineBlockTableManager.class,MineBlockManager.class),
		MANA(ManaTableManager.class,ManaManager.class),
		MINESTACK(MineStackTableManager.class,MineStackManager.class),
		ACHIEVEMENT(AchievementTableManager.class,AchievementManager.class),
		EXPLOSION(ExplosionTableManager.class,ExplosionManager.class),
		MAGICDRIVE(MagicDriveTableManager.class,MagicDriveManager.class),
		CONDENSATION(CondensationTableManager.class,CondensationManager.class),
		;

		private Class<? extends TableManager> tablemanagerClass;
		private Class<? extends DataManager> datamanagerClass;

		ManagerType(Class<? extends TableManager> tablemanagerClass ,Class<? extends DataManager> datamanagerClass) {
			this.tablemanagerClass = tablemanagerClass;
			this.datamanagerClass = datamanagerClass;
		}

		public Class<? extends TableManager> getTableManagerClass() {
			return tablemanagerClass;
		}
		public Class<? extends DataManager> getDataManagerClass() {
			return datamanagerClass;
		}

		/**
		 * sqlのテーブル名を取得する
		 *
		 * @return
		 */
		public String getTableName() {
			return this.name().toLowerCase();
		}

		/**class名からテーブル名を取得します．
		 *
		 * @param _class
		 * @return
		 */
		public static String getTableNamebyClass(
				Class<? extends TableManager> _class) {
			for (ManagerType mt : ManagerType.values()) {
				if (mt.getTableManagerClass().equals(_class)) {
					return mt.getTableName();
				}
			}
			return "example";
		}
		/**class名からデータマネージャークラスを取得します．
		 *
		 * @param _class
		 * @return
		 */
		public static Class<? extends DataManager> getDataManagerClassbyClass(
				Class<? extends TableManager> _class) {
			for (ManagerType mt : ManagerType.values()) {
				if (mt.getTableManagerClass().equals(_class)) {
					return mt.getDataManagerClass();
				}
			}
			return null;
		}
		/**class名からテーブルマネージャークラスを取得します．
		 *
		 * @param _class
		 * @return
		 */
		public static Class<? extends TableManager> getTableManagerClassbyClass(
				Class<? extends DataManager> _class) {
			for (ManagerType mt : ManagerType.values()) {
				if (mt.getDataManagerClass().equals(_class)) {
					return mt.getTableManagerClass();
				}
			}
			return null;
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

	private LinkedHashMap<Class<? extends TableManager>, TableManager> managermap = new LinkedHashMap<Class<? extends TableManager>, TableManager>();

	// コンストラクタ
	public Sql() {
		this.plugin = Gigantic.plugin;
		this.config = Gigantic.yml.getManager(ConfigManager.class);
		this.url = config.getURL();
		this.db = config.getDB();
		this.id = config.getID();
		this.pw = config.getPW();

		// SQL接続，データベース作成
		if (!init()) {
			plugin.getLogger().warning("データベース初期処理にエラーが発生しました");
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends TableManager> T getManager(Class<T> type) {
		return (T) managermap.get(type);
	}

	/**
	 * 接続関数
	 *
	 * @param url
	 *            接続先url
	 * @param id
	 *            ユーザーID
	 * @param pw
	 *            ユーザーPW
	 * @param db
	 *            データベースネーム
	 * @return
	 */

	// 初期処理を行う
	private boolean init() {
		// ドライバーインスタンス生成
		if (!createDriverInstance()) {
			plugin.getLogger().warning("Mysqlドライバーのインスタンス生成に失敗しました");
			return false;
		}

		// sql鯖への接続
		if (!connectMySQL()) {
			plugin.getLogger().warning("SQL接続に失敗しました");
			return false;
		}

		// DB作成
		if (!createDB()) {
			plugin.getLogger().warning("データベース作成に失敗しました");
			return false;
		}

		// Table
		if (!createTableManager()) {
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
	private boolean createDriverInstance() {
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
	private boolean connectMySQL() {
		try {
			if (stmt != null && !stmt.isClosed()) {
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
	private boolean createDB() {
		if (db == null) {
			return false;
		}
		String command;
		command = "CREATE DATABASE IF NOT EXISTS " + db
				+ " character set utf8 collate utf8_general_ci";
		return sendCommand(command);
	}

	/**
	 * createtableStatement
	 *
	 * @return
	 */
	private boolean createTableManager() {
		if (!this.managermap.isEmpty() || this.managermap != null) {
			managermap.clear();
		}
		// 各テーブル用メソッドに受け渡し
		for (ManagerType mt : ManagerType.values()) {
			try {
				this.managermap.put(mt.getTableManagerClass(), mt.getTableManagerClass()
						.getConstructor(Sql.class).newInstance(this));
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * コマンド出力関数
	 *
	 * @param command
	 * @return
	 */
	private boolean sendCommand(String command) {
		checkConnection();
		try {
			stmt.executeUpdate(command);
			return true;
		} catch (SQLException e) {
			plugin.getLogger().warning("sqlクエリの実行に失敗しました。以下にエラーを表示します");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 再接続処理
	 *
	 */
	private void reconnectMySQL() {
		int maxcount = 5;
		int count = 0;

		do {
			plugin.getLogger().warning(
					"Reconnecting...(" + Integer.toString(count) + "times)...");
			this.connectMySQL();
			count++;
		} while (count < maxcount || this.isClosed());

		boolean endflag = false;
		if (this.isClosed()) {
			plugin.getLogger().warning("再接続処理に失敗しました");
			endflag = true;
		}

		if (endflag) {
			plugin.getPluginLoader().disablePlugin(plugin);
		}
	}

	/**
	 * コネクションのクローズ判定
	 *
	 * @return 可否
	 */
	private boolean isClosed() {
		try {
			if (con.isClosed()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			plugin.getLogger().warning("Connection is Closed by Fatal Error");
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * 接続確認
	 *
	 * @return
	 */
	public void checkConnection() {
		if (this.isClosed()) {
			reconnectMySQL();
		}
		try {
			if (stmt == null || stmt.isClosed()) {
				plugin.getLogger().warning(
						"Statement is Closed. Creating Statement...");
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
	public boolean disconnect() {
		if (con != null) {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public void onDisable() {
		// sql切断
		if (!disconnect()) {
			plugin.getLogger().warning("データベース切断に失敗しました");
		}
	}

	/**
	 *
	 * @return Connection
	 */
	public Connection getConnection() {
		return con;
	}

	public String getDataBaseName() {
		return this.db;
	}

	public void multiload(HashMap<UUID, GiganticPlayer> tmpmap) {
		// 各テーブル用メソッドに受け渡し
		for (Class<? extends TableManager> mt : managermap.keySet()) {
			if (PlayerTableManager.class.isAssignableFrom(mt)) {
				PlayerTableManager ptm = (PlayerTableManager) managermap
						.get(mt);
				ptm.multiload(new HashMap<UUID, GiganticPlayer>(tmpmap));
			}
		}
	}

}
