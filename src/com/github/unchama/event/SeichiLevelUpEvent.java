package com.github.unchama.event;

import com.github.unchama.player.GiganticPlayer;

public class SeichiLevelUpEvent extends LevelUpEvent{

	public SeichiLevelUpEvent(GiganticPlayer gp, int level) {
		super(gp, level);
	}

}
