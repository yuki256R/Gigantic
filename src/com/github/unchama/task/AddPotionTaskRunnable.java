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
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.mineboost.BoostType;
import com.github.unchama.player.mineboost.MineBoostManager;
import com.github.unchama.yml.DebugManager;

/**
 * MineBoostにおける採掘速度上昇を全プレイヤーに付加します．
 *
 * @author tar0ss
 *
 */
public class AddPotionTaskRunnable extends BukkitRunnable {
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	private List<Player> playerlist;
	private int size;
	private int count;
	private int minute;

	public AddPotionTaskRunnable(List<Player> playerlist, int minute) {
		this.playerlist = new ArrayList<Player>(playerlist);
		this.size = this.playerlist.size();
		this.count = -1;
		this.minute = minute;
	}

	@Override
	public void run() {

		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				count++;
				if (count >= size || playerlist.isEmpty()) {
					cancel();
					return;
				} else {
					Player player = Bukkit.getServer().getPlayer(
							playerlist.get(count).getUniqueId());
					if (player != null) {
						GiganticPlayer gp = PlayerManager
								.getGiganticPlayer(player);
						GiganticStatus gs = PlayerManager.getStatus(gp);
						if (gs.equals(GiganticStatus.AVAILABLE)) {
							MineBoostManager mm = gp
									.getManager(MineBoostManager.class);
							mm.updata(BoostType.MINUTE_MINE);;
							mm.refresh();
							MineBlockManager mb = gp.getManager(MineBlockManager.class);
							mb.resetTimeCount(TimeType.A_MINUTE);
							if(minute % 30 == 0){
								mb.resetTimeCount(TimeType.HALF_HOUR);
							}
						}
					}
				}
			}

		});

	}

}
