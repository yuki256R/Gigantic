package com.github.unchama.achievement;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.PlayerTimeIncrementEvent;

public final class PlayTickAchievement extends GiganticAchievement implements Listener{

	private final int id;
	/**playtickがこの値を超えた時に実績を解除します
	 *
	 */
	private final long unlock_tick;

	public PlayTickAchievement(int id,long unlock_tick) {
		super();
		this.id = id;
		this.unlock_tick = unlock_tick;
	}

	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * @return unlock_num
	 */
	public long getUnlockTick() {
		return unlock_tick;
	}


	@EventHandler
	public void PlayerTimeIncrementListener(PlayerTimeIncrementEvent event){
		if(event.getNextTick() >= this.getUnlockTick()){
			this.unlockAchievement(event.getGiganticPlayer());
		}
	}

}
