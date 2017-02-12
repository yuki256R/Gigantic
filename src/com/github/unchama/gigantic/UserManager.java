package com.github.unchama.gigantic;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.SqlManager;

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

		//sqlにデータが保存されているか判定する．
		if(SqlManager.isSaved(uuid)){
			gp = SqlManager.loadGiganticPlayer(uuid);
		}else{
			gp = new GiganticPlayer(player);
		}
		gmap.put(uuid, gp);
	}


	public static void quit(Player player){
		UUID uuid = player.getUniqueId();
		GiganticPlayer gp = gmap.get(uuid);
		SqlManager.saveGiganticPlayer(uuid,gp);
	}


}
