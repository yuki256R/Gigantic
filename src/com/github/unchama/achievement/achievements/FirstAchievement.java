package com.github.unchama.achievement.achievements;

import com.github.unchama.achievement.GiganticAchievement;

/**
 * 
 * @author tar0ss
 *
 */
public final class FirstAchievement extends GiganticAchievement {

	public FirstAchievement(int id) {
		super(id);
	}

	@Override
	public String getUnlockInfo() {
		return "初期解禁";
	}


	@Override
	public int getPoint() {
		return 0;
	}

	@Override
	public int getUsePoint() {
		return 0;
	}

	@Override
	public boolean isPurchasable() {
		return false;
	}

	@Override
	public String getLockInfo() {
		return "初期解禁";
	}

}
