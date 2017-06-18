package com.github.unchama.event;

import com.github.unchama.event.moduler.LevelUpEvent;
import com.github.unchama.player.GiganticPlayer;

/**
 *
 * @author ten_niti
 *
 */
public class HuntingLevelUpEvent extends LevelUpEvent {
	public HuntingLevelUpEvent(GiganticPlayer gp, int level) {
		super(gp, level);
	}
}
