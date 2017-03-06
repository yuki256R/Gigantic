package com.github.unchama.task;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.yml.DebugManager;

/**定期セーブに使用するタスクです．
 *
 * @author tar0ss
 *
 */
public class GiganticSaveTaskRunnable extends BukkitRunnable{
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	private List<Player> playerlist;
	private int size;
	private int count;

	public GiganticSaveTaskRunnable(List<Player> playerlist) {
		this.playerlist = new ArrayList<Player>(playerlist);
		this.size = this.playerlist.size();
		this.count = -1;
	}

	@Override
	public void run() {

		Bukkit.getScheduler().runTask(plugin, new Runnable(){
			@Override
			public void run() {
				count++;
				if(count >= size){
					cancel();
					return;
				}else{
					Player player = Bukkit.getServer().getPlayer(playerlist.get(count).getUniqueId());
					if(player != null){
						GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
						if(gp != null){
							gp.save(true);
						}
					}
				}
			}
		});
	}


}
