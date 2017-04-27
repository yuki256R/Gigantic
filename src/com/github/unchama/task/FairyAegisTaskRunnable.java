package com.github.unchama.task;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;

public class FairyAegisTaskRunnable extends BukkitRunnable {
	private Gigantic plugin = Gigantic.plugin;
	// private ConfigManager config =
	// Gigantic.yml.getManager(ConfigManager.class);
	private static List<Block> skilledblocklist = Gigantic.skilledblocklist;
	protected static List<BukkitTask> tasklist = Gigantic.tasklist;
	//private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@SuppressWarnings("unused")
	private GiganticPlayer gp;
	private Player player;
	private HashMap<Integer, List<Block>> breakMap;
	private int height;
	private final int minheight;
	private boolean soundflag;

	public FairyAegisTaskRunnable(GiganticPlayer gp,
			HashMap<Integer, List<Block>> breakMap, int maxheight,
			int minheight, boolean soundflag) {
		this.gp = gp;
		this.player = PlayerManager.getPlayer(gp);
		this.breakMap = breakMap;
		this.minheight = minheight;
		this.height = maxheight;
		this.soundflag = soundflag;
		if (soundflag)
			player.getWorld().playSound(player.getLocation(),
					Sound.ENTITY_WITHER_AMBIENT, 0.7F, 2.0F);
	}

	@Override
	public void run() {
		if (height < minheight) {
			breakMap.forEach((i, blist) -> {
				if (!blist.isEmpty())
					skilledblocklist.removeAll(blist);
			});
			cancel();
			tasklist.remove(this);
			return;
		}

		List<Block> breaklist = breakMap.get(height);
		// debug.sendMessage(DebugEnum.SKILL, "height:" + height + " breaknum:"
		// + breaklist.size());

		if (breaklist != null && !breaklist.isEmpty()) {

			breaklist.forEach(b -> {
				// 通常エフェクトの表示
					/*
					 * if (!b.equals(block)) w.playEffect(b.getLocation(),
					 * Effect.STEP_SOUND, b.getType());
					 */
					// ブロックを削除
					b.setType(Material.AIR);
					b.removeMetadata("Skilled", plugin);
				});

			Block soundblock = breaklist.get(0);

			if (soundflag)
				soundblock.getWorld().playSound(soundblock.getLocation(),
						Sound.ENTITY_VILLAGER_YES, 0.7F, 2.0F);
		}
		height--;
	}

}
