package com.github.unchama.achievement.achievements;

import java.util.BitSet;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.event.GiganticPlayerAvailableEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.achievement.AchievementManager;

public final class SpecialAchievement extends GiganticAchievement implements Listener {

	private final String info;

	private static int FIRSTID = 7000;

	public SpecialAchievement(int id, String info) {
		super(id);
		this.info = info;
	}

	/**
	 * @return info
	 */
	public String getInfo() {
		return info;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void GiganticPlayerAvailableListener(GiganticPlayerAvailableEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		AchievementManager m = gp.getManager(AchievementManager.class);
		BitSet flagSet = m.getAchivGivenFlagSet();
		if (flagSet.get(this.getID() - FIRSTID)) {
			this.unlockAchievement(event.getGiganticPlayer());
		}
	}

	@Override
	public String getUnlockInfo() {
		return info;
	}

	@Override
	public String getLockInfo() {
		return info;
	}

	@Override
	public int getPoint() {
		return 10;
	}

	@Override
	public int getUsePoint() {
		return 0;
	}

	@Override
	public boolean isPurchasable() {
		return false;
	}
}
