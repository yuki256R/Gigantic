package com.github.unchama.event;

import com.github.unchama.event.moduler.LevelUpEvent;
import com.github.unchama.player.GiganticPlayer;

/**
 *
 * @author ten_niti
 *
 */
public class FishingLevelUpEvent extends LevelUpEvent {
	public FishingLevelUpEvent(GiganticPlayer gp, int level) {
		super(gp, level);
	}
}
