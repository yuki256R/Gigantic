package com.github.unchama.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.moduler.PlayerTableManager;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class GiganticMultiLoadTaskRunnable extends BukkitRunnable {
	Gigantic plugin = Gigantic.plugin;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	PlayerTableManager tm;
	// 試行回数
	int attempt;
	// 最大試行回数
	int max_attempt;
	// マルチスレッド用ステートメント
	Statement stmt = null;
	ResultSet rs = null;
	// ロードするプレイヤーマップ
	HashMap<UUID, GiganticPlayer> loadmap;
	HashMap<UUID, GiganticPlayer> loginmap;

	public GiganticMultiLoadTaskRunnable(PlayerTableManager tm,
			HashMap<UUID, GiganticPlayer> loadmap) {
		this.loadmap = loadmap;
		this.loginmap = new HashMap<UUID, GiganticPlayer>();
		this.tm = tm;
		this.attempt = -1;
		this.max_attempt = config.getMaxAttempt();
		try {
			this.stmt = tm.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				attempt++;
				// 試行回数
				if (attempt - 1 >= max_attempt) {
					cancel();
					// 強制ロード
					debug.info(DebugEnum.SQL, "Table:" + tm.table
							+ " 強制ロードします．");
					multiload(true);
					return;
				}
				// loadmapがemptyなら終了
				if (loadmap.isEmpty()) {
					debug.info(DebugEnum.SQL, "Table:" + tm.table
							+ " 並列処理を終了します．");
					cancel();
					return;
				}

				debug.info(DebugEnum.SQL, "Table:" + tm.table + " "
						+ (attempt + 1) + "回目...");
				// checklogin
				checklogin();

				if (loadmap.isEmpty()) {
					debug.info(DebugEnum.SQL, "Table:" + tm.table
							+ " 並列処理を終了します．");
					cancel();
					return;
				}
				// multiload
				multiload(false);

				if (!loginmap.isEmpty()) {
					// setloginflag = true
					setLogin();
				}

			}

			@SuppressWarnings("unchecked")
			private void checklogin() {
				((HashMap<UUID, GiganticPlayer>) loadmap.clone()).forEach((
						uuid, gp) -> {

					Player player = PlayerManager.getPlayer(gp);
					if (player == null) {
						loadmap.remove(uuid);
						debug.info(DebugEnum.SQL, "Table:" + tm.table + " "
								+ gp.name + "は不在のためロード不要");
					}
				});
			}

			/**
			 * 強制的にロードする場合はforce = trueにすること
			 *
			 * @param force
			 */
			private void multiload(boolean force) {
				String command = "";
				tm.checkStatement(stmt);
				// select * from gigantic.mineblock where (uuid = '????' || uuid
				// =
				// '???')
				command = "select * from " + tm.db + "." + tm.table
						+ " where uuid in (";
				for (UUID uuid : loadmap.keySet()) {
					command += "'" + uuid.toString().toLowerCase() + "',";
				}
				command = command.substring(0, command.length() - 1);
				command += ")";
				if (!force) {
					command += "&& loginflag = false";
				}

				// 保存されているデータをロード
				try {
					rs = stmt.executeQuery(command);
					while (rs.next()) {
						// uuidを取得
						UUID uuid = UUID.fromString(rs.getString("uuid"));
						GiganticPlayer gp = loadmap.get(uuid);
						debug.info(DebugEnum.SQL, "Table:" + tm.table + " "
								+ gp.name + "のデータをロードしました．");
						// load
						tm.loadPlayer(gp, rs);
						// DataManagerにloadedフラグを送る
						tm.setLoaded(gp, true);
						// loginmapに追加
						loginmap.put(uuid, loadmap.get(uuid));
						// loadmapから削除
						loadmap.remove(uuid);
					}
					rs.close();
				} catch (SQLException e) {
					plugin.getLogger().warning(
							"Failed to multiload in " + tm.table + " Table");
					e.printStackTrace();
				}
			}

			private void setLogin() {
				String command = "";
				// プレイヤーネームをアップデート，loginflag = trueにする
				command = "update " + tm.db + "." + tm.table
						+ " set loginflag = true where uuid in (";
				for (UUID uuid : loginmap.keySet()) {
					command += "'" + uuid.toString().toLowerCase() + "',";
				}
				command = command.substring(0, command.length() - 1);
				command += ")";

				if (!tm.sendCommand(command, stmt)) {
					plugin.getLogger().warning(
							"Failed to setlogin in " + tm.table + " Table");
				}
			}

		});
	}

}
