package com.github.unchama.sql.moduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.task.GiganticMultiLoadTaskRunnable;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * 通常のプレイヤーデータのセーブ・ロードをする場合はこのクラスを継承してください．
 * SeichiAssistからのデータを引き継ぐ場合はPlayerFromSeichiTableManagerを継承してください
 *
 * @author tar0ss
 *
 */
public abstract class PlayerTableManager extends TableManager implements
		GiganticLoadable {
	Class<? extends DataManager> datamanagerclass;

	@SuppressWarnings("deprecation")
	public PlayerTableManager(Sql sql) {
		super(sql);
		this.datamanagerclass = Sql.ManagerType
				.getDataManagerClassbyClass(this.getClass());
	}

	/**
	 * テーブル作成時に追加するカラムをコマンドとして返り値としてください． ex) command =
	 * "add column if not exists name varchar(30) default null,"
	 * "add"で始まり，","で終わるようにしてください．
	 *
	 * @return command
	 */
	protected abstract String addColumnCommand();

	/**
	 * 新しくデータを作成してください
	 *
	 * set new player data
	 *
	 * @param gp
	 * @return 失敗：false
	 */
	protected abstract boolean newPlayer(GiganticPlayer gp);

	/**
	 * GiganticPlayerのデータをResultSetから受け取るメソッド
	 *
	 *
	 * @param gp
	 * @throws SQLException
	 */
	public abstract void loadPlayer(GiganticPlayer gp, ResultSet rs)
			throws SQLException;

	/**
	 * データ保存時のコマンドを返り値としてください． ex) for(BlockType bt : datamap.keySet()){ i++;
	 * command += bt.getColumnName() + " = '" + datamap.get(bt).getNum() + "',";
	 * }
	 *
	 * @param gp
	 * @param loginflag
	 * @return
	 */
	protected abstract String saveCommand(GiganticPlayer gp, boolean loginflag);

	/**
	 * このクラスで使われるDataManagerClassを返します．
	 *
	 * @return
	 */
	private Class<? extends DataManager> getDataManagerClass() {
		return this.datamanagerclass;
	}
	/**プレイヤーデータのロード状態変更メソッド
	 *
	 * @param gp プレイヤー
	 * @param loaded ロードされた時true
	 */
	public void setLoaded(GiganticPlayer gp,boolean loaded) {
		if (this.getDataManagerClass() != null) {
			gp.getManager(this.getDataManagerClass()).setLoaded(loaded);
		}
	}


	@Override
	protected Boolean createTable() {
		String command;
		// create Table
		command = "CREATE TABLE IF NOT EXISTS " + db + "." + table;
		// Unique Column add
		command += "(uuid varchar(128) primary key not null)";
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
		String tmp = this.addColumnCommand();
		if(tmp != null)	command += tmp;
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
	public Boolean multiload(HashMap<UUID, GiganticPlayer> tmpmap) {
		HashMap<UUID, GiganticPlayer> loadmap = new HashMap<UUID, GiganticPlayer>();

		String command = "";
		this.checkStatement();
		// select * from gigantic.mineblock where (uuid = '????' || uuid =
		// '???')
		command = "select * from " + db + "." + table + " where uuid in (";
		for (UUID uuid : tmpmap.keySet()) {
			command += "'" + uuid.toString().toLowerCase() + "',";
		}
		command = command.substring(0, command.length() - 1);
		command += ")";

		if (tmpmap.isEmpty()) {
			return true;
		}
		// 保存されているデータをロード
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				// uuidを取得
				UUID uuid = UUID.fromString(rs.getString("uuid"));
				// loadmapに追加
				loadmap.put(uuid, tmpmap.get(uuid));
				// tmpmapから削除
				tmpmap.remove(uuid);
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to multiload in " + table + " Table");
			e.printStackTrace();
		}

		new GiganticMultiLoadTaskRunnable(this,
				new HashMap<UUID, GiganticPlayer>(loadmap))
				.runTaskTimerAsynchronously(plugin, 10, 20);

		// 残りのプレイヤーは新規作成
		command = "insert into " + db + "." + table + " (name,uuid) values ";
		for (GiganticPlayer gp : tmpmap.values()) {
			command += "('" + gp.name + "','"
					+ gp.uuid.toString().toLowerCase() + "'),";
		}
		command = command.substring(0, command.length() - 1);

		if (tmpmap.isEmpty()) {
			return true;
		}

		if (!sendCommand(command)) {
			plugin.getLogger().warning(
					"Failed to multi create new row in " + table + " Table");
		}

		for (GiganticPlayer gp : tmpmap.values()) {
			// 新しいデータを生成
			debug.info(DebugEnum.SQL, "Table:" + table + " " + gp.name + "のデータを新規作成");
			this.newPlayer(gp);
			this.setLoaded(gp,true);
		}
		return true;
	}

	@Override
	public Boolean save(GiganticPlayer gp, boolean loginflag) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		this.checkStatement();

		command = "update " + db + "." + table + " set name = '" + gp.name
				+ "',loginflag = " + Boolean.toString(loginflag) + ",";

		String tmp = this.saveCommand(gp,loginflag);
		if(tmp != null)command += tmp;

		command += " where uuid = '" + struuid + "'";

		command = command.replace(", where uuid", " where uuid");

		try {
			stmt.executeUpdate(command);
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to update " + table + " Data of Player:" + gp.name);
			e.printStackTrace();
			return false;
		}
		debug.info(DebugEnum.SQL, "Table:" + table + " " + gp.name + "のデータを保存");

		return true;
	}

}
