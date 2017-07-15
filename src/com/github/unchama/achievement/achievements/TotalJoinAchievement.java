package com.github.unchama.achievement.achievements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.event.TotalJoinIncrementEvent;

public final class TotalJoinAchievement extends GiganticAchievement implements Listener{
	private final int id;
	/**合計ログイン日数がこの値以上の時に実績を解除します
	 *
	 */
	private final long unlock_join;

	public TotalJoinAchievement(int id,long unlock_join) {
		super();
		this.id = id;
		this.unlock_join = unlock_join;
	}

	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * @return unlock_join
	 */
	public long getUnlockJoin() {
		return unlock_join;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void TotalJoinIncrementListener(TotalJoinIncrementEvent event) {
		if (event.getNextAll() >= this.getUnlockJoin())
			this.unlockAchievement(event.getGiganticPlayer());
	}
}
