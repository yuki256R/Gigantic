package com.github.unchama.event;

import com.github.unchama.event.moduler.LevelUpEvent;
import com.github.unchama.player.GiganticPlayer;
/**
 * @author tar0ss
 *
 */
public class BuildLevelUpEvent extends LevelUpEvent{

	public BuildLevelUpEvent(GiganticPlayer gp, int level) {
		super(gp, level);
	}

}
