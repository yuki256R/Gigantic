package com.github.unchama.achievement.achievements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.achievement.AchievementEnum;
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

	public MineBlockAchievement(long unlock_num) {
		super();
		this.unlock_num = unlock_num;
	}

	@Override
	public int getID() {
		return AchievementEnum.MINEBLOCK.getID();
	}

	/**
	 * @return unlock_num
	 */
	public long getUnlock_num() {
		return unlock_num;
	}

	@EventHandler
	public void MineBlockIncrementListener(MineBlockIncrementEvent event) {
		if (event.getNextAll() >= this.unlock_num)
			this.unlockAchievement(event.getGiganticPlayer());
	}

}
