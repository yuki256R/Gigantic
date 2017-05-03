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
import com.github.unchama.player.time.PlayerTimeManager;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class PlayerTimeTaskRunnable extends BukkitRunnable{
	Gigantic plugin = Gigantic.plugin;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	private List<Player> playerlist;
	private int size;
	private int count;

	public PlayerTimeTaskRunnable(List<Player> playerlist) {
		this.playerlist = new ArrayList<Player>(playerlist);
		this.size = this.playerlist.size();
		this.count = -1;
	}

	@Override
	public void run() {
		//TODO:ここで1分ごとに1minを0に設定
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
							PlayerTimeManager timeMng = gp.getManager(PlayerTimeManager.class);

							//プレイ時間追加
							int getservertick = player.getStatistic(org.bukkit.Statistic.PLAY_ONE_TICK);
							int getincrease = getservertick - timeMng.servertick;
							timeMng.servertick = getservertick;
							timeMng.playtick += getincrease;

							//放置判定
							if(player.getLocation().equals(timeMng.loc)){
								if(timeMng.isIdle()){
									//既に放置中なら累計放置時間を追加
									timeMng.totalidletick += getincrease;
								}
								timeMng.idletime ++;
							}else{
								timeMng.loc = player.getLocation();
								timeMng.idletime = 0;
							}
							debug.sendMessage(player,DebugEnum.GUI,"プレイ時間更新"
									+ ":servertick " + timeMng.servertick
									+ ":playtick " + timeMng.playtick
									+ ":totalidletick " + timeMng.totalidletick
									+ ":idletime " + timeMng.idletime
									);


//							if(SeichiAssist.DEBUG){
//								p.sendMessage("総プレイ時間に追加したtick:" + getincrease);
//							}
//								if(SeichiAssist.DEBUG){
//									Util.sendEveryMessage(playerdata.name + "のidletime加算" + playerdata.idletime);
//								}
//								if(SeichiAssist.DEBUG){
//									Util.sendEveryMessage(playerdata.name + "のidletimeリセット");
//								}
						}
					}
				}
			}
		});
	}
}
