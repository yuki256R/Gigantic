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
import com.github.unchama.player.mineboost.MineBoostManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

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

	public AddPotionTaskRunnable(List<Player> playerlist) {
		this.playerlist = new ArrayList<Player>(playerlist);
		this.size = this.playerlist.size();
		this.count = -1;
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
							mm.updataMinuteMine();
							mm.refresh();
						}
						debug.sendMessage(
								player,
								DebugEnum.MINEBOOST,
								"updata MinuteMine for player:"
										+ player.getName());
					}
				}
			}

		});

	}

}
