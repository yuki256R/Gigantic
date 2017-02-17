package com.github.unchama.gigantic;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;

public class UserManager {
	private static Gigantic plugin = Gigantic.plugin;
	private static Sql sql = Gigantic.sql;

	public static HashMap<UUID,GiganticPlayer> gmap = new HashMap<UUID,GiganticPlayer>();




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
		gp.load();
		gmap.put(uuid, gp);
	}


	public static void quit(Player player){
		UUID uuid = player.getUniqueId();
		GiganticPlayer gp = gmap.get(uuid);
		gp.save();
		gmap.remove(uuid);
	}

	public static GiganticPlayer getGiganticPlayer(Player player){
		GiganticPlayer gplayer = gmap.get(player.getUniqueId());
		if(gplayer == null){
			plugin.getLogger().warning(player.getName() + " is not joined");
		}
		return gplayer;
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
