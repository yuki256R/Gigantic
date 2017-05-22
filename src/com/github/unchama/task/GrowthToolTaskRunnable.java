package com.github.unchama.task;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class GrowthToolTaskRunnable extends BukkitRunnable {
	private static List<Player> takeList = new ArrayList<Player>();

	/**
	 * お喋り間隔で呼び出し、リストをクリアする
	 */
	@Override
	public void run() {
		takeList.clear();
	}

	/**
	 * 引数PlayerのGrowthToolがお喋り可能かを返却する。
	 *
	 * @param player
	 * @return
	 */
	public static boolean canTalk(Player player) {
		if (takeList.contains(player)) {
			return false;
		}
		takeList.add(player);
		return true;
	}
}
