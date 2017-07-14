package com.github.unchama.achievement.achievements;

import com.github.unchama.achievement.GiganticAchievement;

public final class ChainJoinAchievement extends GiganticAchievement {
	private final int id;
	/**連続ログイン日数がこの値以上の時に実績を解除します
	 *
	 */
	private final long unlock_chain;

	public ChainJoinAchievement(int id,long unlock_chain) {
		super();
		this.id = id;
		this.unlock_chain = unlock_chain;
	}

	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * @return unlock_chain
	 */
	public long getUnlockChain() {
		return unlock_chain;
	}

}
