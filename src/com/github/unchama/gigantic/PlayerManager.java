package com.github.unchama.gigantic;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.task.putGiganticMapTaskRunnable;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class PlayerManager {
	private static Gigantic plugin = Gigantic.plugin;
	private static DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	// ロード済みのGiganticPlayerMap
	public static HashMap<UUID, GiganticPlayer> gmap = new HashMap<UUID, GiganticPlayer>();
	// ロード待機中のGiganticPlayerMap
	public static HashMap<UUID, GiganticPlayer> waitingloadmap = new HashMap<UUID, GiganticPlayer>();

	/**
	 * hashmap_add
	 *
	 * @param player
	 */
	public static void join(Player player) {
		UUID uuid = player.getUniqueId();
		GiganticPlayer gp;
		if (gmap.containsKey(uuid)) {
			plugin.getLogger().warning(
					"Player:" + player.getName() + "was already joined");
			player.sendMessage(ChatColor.RED
					+ "既にログインしています．一度ログアウトを行い，時間が経ってからログインし直してください．");
			return;
		}
		gp = new GiganticPlayer(player);
		waitingloadmap.put(uuid, gp);
	}

	/**
	 * hashmap_remove
	 *
	 * @param player
	 */
	public static void quit(Player player) {
		UUID uuid = player.getUniqueId();
		GiganticPlayer gp = gmap.get(uuid);
		if(gp == null){
			return ;
		}
		// 終了前最終処理を行う
		gp.fin();
		// 最終データをsqlにセーブ
		gp.save(false);
		gmap.remove(uuid);
	}

	/**
	 * Player -> GiganticPlayer
	 *
	 * @param player
	 * @return GiganticPlayer
	 */
	public static GiganticPlayer getGiganticPlayer(Player player) {
		GiganticPlayer gplayer = gmap.get(player.getUniqueId());
		return gplayer;
	}

	/**
	 * GiganticPlayer -> Player
	 *
	 * @param GiganticPlayer
	 * @return Player
	 */
	public static Player getPlayer(GiganticPlayer gp) {
		return plugin.getServer().getPlayer(gp.uuid);
	}

	public static void onDisable() {
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			quit(p);
		}
	}

	public static void onEnable() {
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			join(p);
		}
	}

	public static void multiload(){
		debug.info(DebugEnum.SQL, "並列読み込みを開始します．");
		//空ならreturn
		if(waitingloadmap.isEmpty()){
			debug.info(DebugEnum.SQL, "読み込み不要");
			return;
		}
		//waitingloadmap内をコピー
		HashMap<UUID, GiganticPlayer> tmpmap = new HashMap<UUID, GiganticPlayer>(waitingloadmap);
		//waitingloadmapを空にする．
		waitingloadmap.clear();

		String message = "対象プレイヤー(";
		for(GiganticPlayer gp : tmpmap.values()){
			message += gp.name + " ";
		}
		debug.info(DebugEnum.SQL, message + ")...");

		// 全てのsqlデータをロード
		Gigantic.sql.multiload(new HashMap<UUID, GiganticPlayer>(tmpmap));

		new putGiganticMapTaskRunnable(new HashMap<UUID, GiganticPlayer>(tmpmap)).runTaskTimerAsynchronously(plugin, 11, 20);

	}

}
