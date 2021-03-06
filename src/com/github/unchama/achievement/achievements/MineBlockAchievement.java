package com.github.unchama.achievement.achievements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.event.MineBlockIncrementEvent;

/**整地量に応じた実績クラス
 *
 * @author tar0ss
 *
 */
public class MineBlockAchievement extends GiganticAchievement implements Listener {

	/**整地量がこの値を超えた時に実績を解除します
	 *
	 */
	private final long unlock_num;

	public MineBlockAchievement(int id,long unlock_num) {
		super(id);
		this.unlock_num = unlock_num;
	}

	/**
	 * @return unlock_num
	 */
	public long getUnlockNum() {
		return unlock_num;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void MineBlockIncrementListener(MineBlockIncrementEvent event) {
		if (event.getNextAll() >= this.getUnlockNum())
			this.unlockAchievement(event.getGiganticPlayer());
	}

	@Override
	public String getUnlockInfo() {
		return "総整地量が" + getUnlockNum() + "を超える";
	}

	@Override
	public String getLockInfo() {
		return "総整地量が" + getUnlockNum() + "を超える";
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
