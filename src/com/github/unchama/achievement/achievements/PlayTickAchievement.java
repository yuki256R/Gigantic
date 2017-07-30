package com.github.unchama.achievement.achievements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.event.PlayerTimeIncrementEvent;
import com.github.unchama.util.Converter;

public final class PlayTickAchievement extends GiganticAchievement implements Listener{
	/**playtickがこの値を超えた時に実績を解除します
	 *
	 */
	private final long unlock_tick;

	public PlayTickAchievement(int id,long unlock_tick) {
		super(id);
		this.unlock_tick = unlock_tick;
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

	@Override
	public String getUnlockInfo() {
		return "累計接続時間が" + Converter.toTimeString(this.getUnlockTick()) + "を超える";
	}

	@Override
	public String getLockInfo() {
		return "累計接続時間が" + Converter.toTimeString(this.getUnlockTick()) + "を超える";
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
