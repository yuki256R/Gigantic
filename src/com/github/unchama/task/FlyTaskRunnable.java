package com.github.unchama.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.fly.ExperienceManager;
import com.github.unchama.player.fly.FlyManager;
import com.github.unchama.yml.ConfigManager;

public class FlyTaskRunnable extends BukkitRunnable {

	//configから1分で減る経験値(fly用)を取得->fly許可(4/5実装済み)

	Gigantic plugin = Gigantic.plugin;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	private List<Player> playerlist;
	private int size;
	private int count;

	public FlyTaskRunnable(List<Player> playerlist) {
		this.playerlist = new ArrayList<Player>(playerlist);
		this.size = this.playerlist.size();
		this.count = -1;
	}
	@Override
	public void run() {
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
							//経験値変更用のクラスを作成
							ExperienceManager expman = new ExperienceManager(player);

							int minus = -config.getFlyExp();//減る量(負)
							
							if(gp.getManager(FlyManager.class).getEndlessflyflag()){
								if(!expman.hasExp(config.getFlyExp())){
									player.sendMessage(ChatColor.RED + "Fly効果の発動に必要な経験値が不足しているため");
									player.sendMessage(ChatColor.RED + "Fly効果を終了しました");
									gp.getManager(FlyManager.class).setFlytime(0);
									gp.getManager(FlyManager.class).setFlyflag(false);
									gp.getManager(FlyManager.class).setEndlessflyflag(false);
									player.setAllowFlight(false);
									player.setFlying(false);
								}else{
									player.setAllowFlight(true);
									player.sendMessage(ChatColor.GREEN + "Fly効果は無期限で継続中です");
									expman.changeExp(minus);
								}
							}else if(gp.getManager(FlyManager.class).getFlyflag()){
								int flytime = gp.getManager(FlyManager.class).getFlytime();
								if(flytime <= 0){
									player.sendMessage(ChatColor.GREEN + "Fly効果が終了しました");
									gp.getManager(FlyManager.class).setFlyflag(false);
									player.setAllowFlight(false);
									player.setFlying(false);
								}else if(!expman.hasExp(config.getFlyExp())){
									player.sendMessage(ChatColor.RED + "Fly効果の発動に必要な経験値が不足しているため");
									player.sendMessage(ChatColor.RED + "Fly効果を終了しました");
									gp.getManager(FlyManager.class).setFlytime(0);
									gp.getManager(FlyManager.class).setFlyflag(false);
									player.setAllowFlight(false);
									player.setFlying(false);
								}else{
									player.setAllowFlight(true);
									player.sendMessage(ChatColor.GREEN + "Fly効果はあと" + flytime + "分です");
									gp.getManager(FlyManager.class).setFlytime(flytime-1);
									expman.changeExp(minus);
								}
							}
						}
					}
				}
			}
		});
	}
}