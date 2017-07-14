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

	private final int id;

	private static int FIRSTID = 7000;

	public SpecialAchievement(int id) {
		super();
		this.id = id;
	}

	@Override
	public int getID() {
		return this.id;
	}



	@EventHandler(priority = EventPriority.MONITOR)
	public void GiganticPlayerAvailableListener(GiganticPlayerAvailableEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		AchievementManager m = gp.getManager(AchievementManager.class);
		BitSet flagSet = m.getAchivGivenFlagSet();
		if(flagSet.get(this.getID() - FIRSTID)){
			this.unlockAchievement(event.getGiganticPlayer());
		}
	}
}
