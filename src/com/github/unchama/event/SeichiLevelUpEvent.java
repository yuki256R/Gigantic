package com.github.unchama.event;

import com.github.unchama.event.moduler.LevelUpEvent;
import com.github.unchama.player.GiganticPlayer;
/**
 * @author tar0ss
 *
 */
public class SeichiLevelUpEvent extends LevelUpEvent{

	public SeichiLevelUpEvent(GiganticPlayer gp, int level) {
		super(gp, level);
	}

}
