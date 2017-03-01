package com.github.unchama.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.moduler.PlayerTableManager;
import com.github.unchama.yml.ConfigManager;

/**
 * GiganticPlayerデータのロードを行います．
 *
 * @author tar0ss
 *
 */
public class GiganticLoadTaskRunnable extends BukkitRunnable {
	Gigantic plugin = Gigantic.plugin;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	PlayerTableManager tm;
	GiganticPlayer gp;
	// 試行回数
	int attempt;
	// 最大試行回数
	int max_attempt;
	// マルチスレッド用ステートメント
	Statement stmt = null;
	ResultSet rs = null;

	final String struuid;

	/**
	 * 
	 * @param tm PlayerTableManager
	 * @param gp GiganticPlayer
	 */
	public GiganticLoadTaskRunnable(PlayerTableManager tm, GiganticPlayer gp) {
		this.tm = tm;
		this.gp = gp;
		this.struuid = gp.uuid.toString().toLowerCase();
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
		Bukkit.getScheduler().runTask(plugin, new Runnable(){
			@Override
			public void run() {
				// 試行回数
				if (attempt++ >= max_attempt) {
					cancel();
					load();
					return;
				}

				Player player = PlayerManager.getPlayer(gp);

				// プレイヤーが鯖にいなければ終了
				if (player == null) {
					cancel();
					return;
				}

				// プレイヤーが移動前の鯖をログアウトして正しくセーブしているか判定
				if (!isSafe()) {
					return;
				}

				//ログイン状態にする．
				setLogin();

				//必要なデータをロードする．
				load();
			}
			private void load() {
				String command = "";
				// 接続をチェック
				tm.checkStatement(stmt);
				//必要なデータをロードする．
				command = "select * from " + tm.db + "." + tm.table + " where uuid = '"
						+ struuid + "'";
				try {
					rs = stmt.executeQuery(command);
					while (rs.next()) {
						tm.loadPlayer(gp,rs);
					}
					rs.close();
				} catch (SQLException e) {
					plugin.getLogger().warning(
							"Failed to read count (player:" + gp.name + ")");
					e.printStackTrace();
				}

			}

			private void setLogin() {
				String command = "";
				// プレイヤーネームをアップデート，loginflag = trueにする
				command = "update " + tm.db + "." + tm.table + " set name = '"
						+ gp.name + "'" + ",set loginflag = true"
						+ " where uuid like '" + struuid + "'";
				if (!tm.sendCommand(command,stmt)) {
					plugin.getLogger().warning(
							"Failed to update name (player:" + gp.name + ")");
				}
			}

			private boolean isSafe() {
				String command = "";
				boolean flag = false;
				// 接続をチェック
				tm.checkStatement(stmt);
				// ログインフラグの確認を行う
				command = "select loginflag from " + tm.db + "." + tm.table
						+ " where uuid = '" + struuid + "'";
				try {
					rs = stmt.executeQuery(command);
					while (rs.next()) {
						flag = rs.getBoolean("loginflag");
					}
					rs.close();
				} catch (SQLException e) {
					plugin.getLogger().warning(
							"Failed to check loginflag (player:" + gp.name + ")");
					e.printStackTrace();
					return false;
				}
				return flag == false ? true : false;
			}
			
		});

	}



}
