package com.github.unchama.achievement.achievements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.event.GiganticPlayerAvailableEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.point.UnchamaPointManager;

public class UnchamaPointAchievement extends GiganticAchievement implements Listener {

	private final int id;
	/**整地量がこの値を超えた時に実績を解除します
	 *
	 */
	private final long unlock_num;

	public UnchamaPointAchievement(int id,long unlock_num) {
		super();
		this.id = id;
		this.unlock_num = unlock_num;
	}

	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * @return unlock_num
	 */
	public long getUnlockNum() {
		return unlock_num;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void GiganticPlayerAvailableListener(GiganticPlayerAvailableEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		UnchamaPointManager m = gp.getManager(UnchamaPointManager.class);
		if (m.getDefaultPoint() >= this.getUnlockNum())
			this.unlockAchievement(event.getGiganticPlayer());
	}

	@Override
	public String getUnlockInfo() {
		return "投票数が" + this.getUnlockNum() + "を超える";
	}

	@Override
	public String getLockInfo() {
		return "投票数が" + this.getUnlockNum() + "を超える";
	}

}
