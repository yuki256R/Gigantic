package com.github.unchama.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.UUID;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.player.dimensionalinventory.DimensionalInventoryManager;
import com.github.unchama.player.donate.DonateDataManager;
import com.github.unchama.player.fishing.FishingManager;
import com.github.unchama.player.fishinglevel.FishingLevelManager;
import com.github.unchama.player.gacha.PlayerGachaManager;
import com.github.unchama.player.gachastack.GachaStackManager;
import com.github.unchama.player.gigantic.GiganticManager;
import com.github.unchama.player.huntinglevel.HuntingLevelManager;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.point.GiganticPointManager;
import com.github.unchama.player.point.UnchamaPointManager;
import com.github.unchama.player.presentbox.PresentBoxManager;
import com.github.unchama.player.region.RegionManager;
import com.github.unchama.player.seichiskill.SkillEffectManager;
import com.github.unchama.player.seichiskill.active.CondensationManager;
import com.github.unchama.player.seichiskill.active.ExplosionManager;
import com.github.unchama.player.seichiskill.active.FairyAegisManager;
import com.github.unchama.player.seichiskill.active.MagicDriveManager;
import com.github.unchama.player.seichiskill.active.RuinFieldManager;
import com.github.unchama.player.settings.PlayerSettingsManager;
import com.github.unchama.player.time.PlayerTimeManager;
import com.github.unchama.player.toolpouch.ToolPouchManager;
import com.github.unchama.sql.donate.DonateTableManager;
import com.github.unchama.sql.gacha.GiganticGachaTableManager;
import com.github.unchama.sql.gacha.OldGachaTableManager;
import com.github.unchama.sql.gacha.PremiumGachaTableManager;
import com.github.unchama.sql.moduler.PlayerTableManager;
import com.github.unchama.sql.moduler.RankingTableManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;
import com.github.unchama.sql.moduler.TableManager;
import com.github.unchama.sql.player.BuildTableManager;
import com.github.unchama.sql.player.CondensationTableManager;
import com.github.unchama.sql.player.DimensionalInventoryTableManager;
import com.github.unchama.sql.player.ExplosionTableManager;
import com.github.unchama.sql.player.FairyAegisTableManager;
import com.github.unchama.sql.player.FishingLevelTableManager;
import com.github.unchama.sql.player.FishingTableManager;
import com.github.unchama.sql.player.GachaStackTableManager;
import com.github.unchama.sql.player.GiganticTableManager;
import com.github.unchama.sql.player.HuntingLevelTableManager;
import com.github.unchama.sql.player.HuntingPointTableManager;
import com.github.unchama.sql.player.MagicDriveTableManager;
import com.github.unchama.sql.player.ManaTableManager;
import com.github.unchama.sql.player.MineBlockTableManager;
import com.github.unchama.sql.player.MineStackTableManager;
import com.github.unchama.sql.player.PlayerGachaTableManager;
import com.github.unchama.sql.player.PlayerSettingsTableManager;
import com.github.unchama.sql.player.PlayerTimeTableManager;
import com.github.unchama.sql.player.PresentBoxTableManager;
import com.github.unchama.sql.player.RegionTableManager;
import com.github.unchama.sql.player.RuinFieldTableManager;
import com.github.unchama.sql.player.SkillEffectTableManager;
import com.github.unchama.sql.player.ToolPouchTableManager;
import com.github.unchama.sql.point.GiganticPointTableManager;
import com.github.unchama.sql.ranking.BuildRankingTableManager;
import com.github.unchama.sql.ranking.FishingExpRankingTableManager;
import com.github.unchama.sql.ranking.HuntingExpRankingTableManager;
import com.github.unchama.sql.ranking.LoginTimeRankingTableManager;
import com.github.unchama.sql.ranking.MineBlockRankingTableManager;
import com.github.unchama.sql.point.UnchamaPointTableManager;
import com.github.unchama.task.LimitedRankingLoadTaskRunnable;
import com.github.unchama.task.RankingLoadTaskRunnable;
import com.github.unchama.task.RankingSendTaskRunnable;
import com.github.unchama.task.RankingUpdateTaskRunnable;
import com.github.unchama.yml.ConfigManager;

/**
 * @author tar0ss
 *
 */
public class Sql {
	// TableManagerとそれに対応するDataManagerClass
	public static enum ManagerType {
		GIGANTICGACHA(GiganticGachaTableManager.class), //
		PREMIUMGACHA(PremiumGachaTableManager.class), //
		OLDGACHA(OldGachaTableManager.class), //
		GIGANTIC(GiganticTableManager.class, GiganticManager.class), //
		PLAYERSETTINGS(PlayerSettingsTableManager.class,
				PlayerSettingsManager.class), //
		MINEBLOCK(MineBlockTableManager.class, MineBlockManager.class), //
		MANA(ManaTableManager.class, ManaManager.class), //
		MINESTACK(MineStackTableManager.class, MineStackManager.class), //
		TOOLPOUCH(ToolPouchTableManager.class, ToolPouchManager.class), //
		EXPLOSION(ExplosionTableManager.class, ExplosionManager.class), //
		MAGICDRIVE(MagicDriveTableManager.class, MagicDriveManager.class), //
		CONDENSATION(CondensationTableManager.class, CondensationManager.class), //
		RUINFIELD(RuinFieldTableManager.class, RuinFieldManager.class), //
		FAIRYAEGIS(FairyAegisTableManager.class, FairyAegisManager.class), //
		BUILD(BuildTableManager.class, BuildManager.class), //
		PLAYERGACHA(PlayerGachaTableManager.class, PlayerGachaManager.class), //
		REGION(RegionTableManager.class, RegionManager.class), //
		PLAYERTIME(PlayerTimeTableManager.class, PlayerTimeManager.class), //
		HUNTINGPOINT(HuntingPointTableManager.class, HuntingPointManager.class), //
		HUNTINGLEVEL(HuntingLevelTableManager.class, HuntingLevelManager.class), //
		DIMENSIONALINVENTORY(DimensionalInventoryTableManager.class,
				DimensionalInventoryManager.class), //
		PRESENTBOX(PresentBoxTableManager.class, PresentBoxManager.class), //
		MINEBLOCKRANKING(MineBlockRankingTableManager.class), //
		BUILDRANKING(BuildRankingTableManager.class),//
		LOGINTIMERANKING(LoginTimeRankingTableManager.class),//
		HUNTINGEXPRANKING(HuntingExpRankingTableManager.class),//
		FISHINGEXPRANKING(FishingExpRankingTableManager.class),//
		DONATEDATA(DonateTableManager.class, DonateDataManager.class),
		GACHASTACK(GachaStackTableManager.class, GachaStackManager.class),//
		FISHINGLEVEL(FishingLevelTableManager.class, FishingLevelManager.class),//
		FISHING(FishingTableManager.class, FishingManager.class),//
		PLAYEREFFECT(SkillEffectTableManager.class, SkillEffectManager.class), //
		UNCHAMAPOINT(UnchamaPointTableManager.class, UnchamaPointManager.class),
		GIGANTICPOINT(GiganticPointTableManager.class, GiganticPointManager.class),
		;

		private Class<? extends TableManager> tablemanagerClass;
		private Class<? extends DataManager> datamanagerClass;

		ManagerType(Class<? extends TableManager> tablemanagerClass) {
			this.tablemanagerClass = tablemanagerClass;
		}

		ManagerType(Class<? extends TableManager> tablemanagerClass,
				Class<? extends DataManager> datamanagerClass) {
			this.tablemanagerClass = tablemanagerClass;
			this.datamanagerClass = datamanagerClass;
		}

		public Class<? extends TableManager> getTableManagerClass() {
			return tablemanagerClass;
		}

		/**
		 * nullを返す場合があります．
		 *
		 * @return
		 */
		@Deprecated
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

		/**
		 * class名からテーブル名を取得します．
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

		/**
		 * class名からデータマネージャークラスを取得します． nullを返す場合があります．
		 *
		 * @param _class
		 * @return
		 */
		@Deprecated
		public static Class<? extends DataManager> getDataManagerClassbyClass(
				Class<? extends TableManager> _class) {
			for (ManagerType mt : ManagerType.values()) {
				if (mt.getTableManagerClass().equals(_class)) {
					return mt.getDataManagerClass();
				}
			}
			return null;
		}

		/**
		 * class名からテーブルマネージャークラスを取得します． nullを返す場合があります．
		 *
		 * @param _class
		 * @return
		 */
		@Deprecated
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
			Properties p = con.getClientInfo();
			/*
			 * validationQuery コネクションの有効性検証用のクエリ。このクエリは少なくとも1行を返すSQL
			 * SELECT文でなければなりません。
			 *
			 * testOnBorrow trueに設定すると、プールからコネクションを取得する際に検証を行います。
			 *
			 * testWhileIdle
			 * trueに設定すると、監視スレッドがアイドル状態のコネクションの生存確認を行う際に、有効性の検証も行います
			 * 。検証に失敗した場合は、プールから削除されます。
			 */
			p.setProperty("validationQuery", "SELECT 1 FROM DUAL");
			p.setProperty("testonBorrow", "true");
			p.setProperty("testWhileIdle", "true");
			con.setClientInfo(p);
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
		boolean flag = false;
		// 各テーブル用メソッドに受け渡し
		for (ManagerType mt : ManagerType.values()) {
			try {
				this.managermap.put(mt.getTableManagerClass(), mt
						.getTableManagerClass().getConstructor(Sql.class)
						.newInstance(this));
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				plugin.getLogger().warning(
						"Failed to create instance of " + mt.name());
				e.printStackTrace();
				flag = true;
				continue;
			}
		}
		if (flag) {
			plugin.getLogger().warning("テーブルインスタンスの生成に失敗しました．");
			return false;
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
			else if (mt == DonateTableManager.class) {
				((DonateTableManager) managermap.get(mt)).multiload(new HashMap<UUID, GiganticPlayer>(tmpmap));
			}
		}
	}

	/*
	 * 毎分のランキング処理
	 */
	public void update() {
		int delay = 1;
		for (Class<? extends TableManager> mt : managermap.keySet()) {
			if (RankingTableManager.class.isAssignableFrom(mt)) {
				RankingTableManager rtm = (RankingTableManager) managermap
						.get(mt);
				new RankingSendTaskRunnable(rtm).runTaskLaterAsynchronously(
						plugin, delay);
				delay++;
				new RankingUpdateTaskRunnable(rtm).runTaskLaterAsynchronously(
						plugin, delay);
				delay++;

			}
		}

	}

	/**期間式ランキングのアップデート
	 *
	 * @param timeType
	 */
	public void update(TimeType tt) {
		int delay = 1;
		for (Class<? extends TableManager> mt : managermap.keySet()) {
			if (RankingTableManager.class.isAssignableFrom(mt)) {
				RankingTableManager rtm = (RankingTableManager) managermap
						.get(mt);
				new LimitedRankingLoadTaskRunnable(rtm, tt).runTaskLaterAsynchronously(
						plugin, delay);
				delay++;

			}
		}
	}

	/**
	 * gpが初期化を終了した後に処理される
	 *
	 * @param gp
	 */
	public void onAvailavle(GiganticPlayer gp) {
		for (Class<? extends TableManager> mt : managermap.keySet()) {
			if (RankingTableManager.class.isAssignableFrom(mt)) {
				RankingTableManager rtm = (RankingTableManager) managermap
						.get(mt);
				rtm.join(gp);
			}
		}
	}

	public void loadRankingData() {
		for (Class<? extends TableManager> mt : managermap.keySet()) {
			if (RankingTableManager.class.isAssignableFrom(mt)) {
				RankingTableManager rtm = (RankingTableManager) managermap
						.get(mt);
				new RankingLoadTaskRunnable(rtm).runTaskTimerAsynchronously(
						plugin, 5, 5);

			}
		}
	}

}
