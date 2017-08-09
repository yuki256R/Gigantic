package com.github.unchama.achievement.achievements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.event.MineBlockIncrementEvent;

public final class MineBlockLuckyNumberAchievement extends GiganticAchievement implements Listener {

	private final int id;
	/**整地量がこの時に実績を解除します
	 *
	 */
	private final long unlock_num;

	private final long balance;

	public MineBlockLuckyNumberAchievement(int id,long unlock_num) {
		super();
		this.id = id;
		this.unlock_num = unlock_num;
		this.balance = (long)Math.pow(10, Long.toString(unlock_num).length()) ;
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

	/**
	 * @return balance
	 */
	private long getBalancer() {
		return this.balance;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void MineBlockIncrementListener(MineBlockIncrementEvent event) {
		if (event.getNextAll() % this.getBalancer()  == this.getUnlockNum())
			this.unlockAchievement(event.getGiganticPlayer());
	}

}
