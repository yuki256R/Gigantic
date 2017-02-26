package com.github.unchama.gigantic;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.unchama.player.GiganticPlayer;

public class PlayerManager {
	private static Gigantic plugin = Gigantic.plugin;
	//private static Sql sql = Gigantic.sql;

	private static HashMap<UUID,GiganticPlayer> gmap = new HashMap<UUID,GiganticPlayer>();




	/**hashmap_add
	 *
	 * @param player
	 */
	public static void join(Player player){
		UUID uuid = player.getUniqueId();
		GiganticPlayer gp;
		if(gmap.containsKey(uuid)){
			plugin.getLogger().warning("Player:" + player.getName() + "was already joined");
			player.sendMessage(ChatColor.RED + "既にログインしています．一度ログアウトを行い，時間が経ってからログインし直してください．");
			return;
		}
		gp = new GiganticPlayer(player);
		//一度全てのsqlデータをロードしておく．
		gp.load();
		//ロード後の初期化処理を行う
		gp.init();
		gmap.put(uuid, gp);
	}


	/**hashmap_remove
	 *
	 * @param player
	 */
	public static void quit(Player player){
		UUID uuid = player.getUniqueId();
		GiganticPlayer gp = gmap.get(uuid);
		//終了前最終処理を行う
		gp.fin();
		//最終データをsqlにセーブ
		gp.save();
		gmap.remove(uuid);
	}

	/**Player -> GiganticPlayer
	 *
	 * @param player
	 * @return GiganticPlayer
	 */
	public static GiganticPlayer getGiganticPlayer(Player player){
		GiganticPlayer gplayer = gmap.get(player.getUniqueId());
		if(gplayer == null){
			plugin.getLogger().warning("can't get GP because" + player.getName() + " is not joined");
		}
		return gplayer;
	}

	/**GiganticPlayer -> Player
	 *
	 * @param GiganticPlayer
	 * @return Player
	 */
	public static Player getPlayer(GiganticPlayer gp){
		return plugin.getServer().getPlayer(gp.uuid);
	}


	public static void onDisable() {
		for(Player p : plugin.getServer().getOnlinePlayers()){
			quit(p);
		}

	}


	public static void onEnable() {
		for(Player p : plugin.getServer().getOnlinePlayers()){
			join(p);
		}
	}



}
