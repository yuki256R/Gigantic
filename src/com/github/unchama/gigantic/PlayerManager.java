package com.github.unchama.gigantic;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.sql.player.GiganticTableManager;
import com.github.unchama.task.GiganticInitializeTaskRunnable;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class PlayerManager {
	private static Gigantic plugin = Gigantic.plugin;
	private static DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	// ロード済みのGiganticPlayerMap
	private static HashMap<UUID, GiganticPlayer> gmap = new HashMap<UUID, GiganticPlayer>();
	// ロード待機中のGiganticPlayerMap
	private static HashMap<UUID, GiganticPlayer> waitingloadmap = new HashMap<UUID, GiganticPlayer>();

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
		player.sendMessage(ChatColor.GREEN
				+ "データをロードしています．しばらくお待ちください．．．");
		gp = new GiganticPlayer(player);
		waitingloadmap.put(uuid, gp);
		gmap.put(uuid, gp);
	}

	/**
	 * hashmap_remove
	 *
	 * @param player
	 */
	public static void quit(Player player) {
		UUID uuid = player.getUniqueId();
		GiganticPlayer gp = gmap.get(uuid);
		if (gp == null)
			return;
		if (gp.getStatus().equals(GiganticStatus.AVAILABLE) && gp.isloaded()) {
			// 終了前最終処理を行う
			gp.fin();

			if (gp.getStatus().equals(GiganticStatus.FINALIZE)) {
				// 最終データをsqlにセーブ
				gp.save(false);

			}
		}
		gmap.remove(uuid);
	}

	/**
	 * Player -> GiganticPlayer
	 *
	 * @param player
	 * @return GiganticPlayer
	 */
	public static GiganticPlayer getGiganticPlayer(Player player) {
		if (!gmap.containsKey(player.getUniqueId())) {
			Bukkit.getLogger().warning(player.getName() + " -> PlayerData not found.");
			return null;
		}
		return getGiganticPlayer(player.getUniqueId());
	}

	/**
	 * UUID -> GiganticPlayer
	 *
	 * @param uuid
	 * @return
	 */
	public static GiganticPlayer getGiganticPlayer(UUID uuid){
		GiganticPlayer gp = gmap.get(uuid);
		if( gp == null){
			Bukkit.getLogger().warning(uuid.toString() + " -> PlayerData not found.");
			return null;
		}else{
			return gp;
		}
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

	/**現在保存しているGpを全て取得します．
	 *
	 * @return
	 */
	public static Collection<GiganticPlayer> getGiganticPlayerList() {
		return gmap.values();
	}

	public static void onDisable() {
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			p.closeInventory();
			quit(p);
		}
	}

	public static void onEnable() {
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			join(p);
		}
	}

	public static void multiload() {
		debug.info(DebugEnum.SQL, "並列読み込みを開始します．");
		//空ならreturn
		if (waitingloadmap.isEmpty()) {
			debug.info(DebugEnum.SQL, "読み込み不要");
			return;
		}
		//waitingloadmap内をコピー
		HashMap<UUID, GiganticPlayer> tmpmap = new HashMap<UUID, GiganticPlayer>(waitingloadmap);
		//waitingloadmapを空にする．
		waitingloadmap.clear();

		String message = "対象プレイヤー(";
		for (GiganticPlayer gp : tmpmap.values()) {
			message += gp.name + " ";
		}
		debug.info(DebugEnum.SQL, message + ")...");

		// 全てのsqlデータをロード
		Gigantic.sql.multiload(new HashMap<UUID, GiganticPlayer>(tmpmap));

		new GiganticInitializeTaskRunnable(new HashMap<UUID, GiganticPlayer>(tmpmap)).runTaskTimerAsynchronously(
				plugin, 11, 20);

	}

	/**playerのステータスを確認します．
	 *
	 * @param player
	 * @return
	 */
	public static GiganticStatus getStatus(Player player) {
		GiganticPlayer gp = getGiganticPlayer(player);
		return getStatus(gp);
	}

	/**GiganticPlayerのステータスを確認します．
	 *
	 * @param gp
	 * @return
	 */
	public static GiganticStatus getStatus(GiganticPlayer gp) {
		return gp == null ? GiganticStatus.NOT_LOADED : gp.getStatus();
	}

	/**UUID からプレイヤーネームを取得します
	 *
	 * @param uuid
	 * @return
	 */
	public static String getName(UUID uuid) {
		return Gigantic.sql.getManager(GiganticTableManager.class).getName(uuid);
	}

	/**Name からUUIDを取得します
	 *
	 * @param name
	 * @return uuid
	 */
	public static UUID getUUID(String name) {
		return Gigantic.sql.getManager(GiganticTableManager.class).getUUID(name);
	}

	/**Name -> GiganticPlayer
	 *
	 * @param name
	 * @return gp
	 */
	public static GiganticPlayer getGiganticPlayer(String name){
		return gmap.get(getUUID(name));
	}

}
