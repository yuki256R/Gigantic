package com.github.unchama.task;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class BuildTaskRunnable extends BukkitRunnable{

	Gigantic plugin = Gigantic.plugin;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	
	private List<Player> playerlist;
	private int size;
	private int count;

	public BuildTaskRunnable(List<Player> playerlist) {
		this.playerlist = new ArrayList<Player>(playerlist);
		this.size = this.playerlist.size();
		this.count = -1;
	}
	
	@Override
	public void run() {
		//ここで1分ごとに1minを0に設定
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run(){
				count++;
				if(count >= size || playerlist.isEmpty()){
					cancel();
					return;
				}else{
					Player player = Bukkit.getServer().getPlayer(playerlist.get(count).getUniqueId());
					if(player != null){
						GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
						GiganticStatus gs = PlayerManager.getStatus(gp);
						if(gs.equals(GiganticStatus.AVAILABLE)){
							gp.getManager(BuildManager.class).setBuild_Num_1min(0);
							debug.sendMessage(player,DebugEnum.BUILD,"1分経過--Build_Num_1minを0にリセット。");
						}
					}
				}
			}
		});
	}

}
