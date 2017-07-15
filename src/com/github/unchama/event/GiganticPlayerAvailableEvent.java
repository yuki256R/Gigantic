package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;

public final class GiganticPlayerAvailableEvent extends CustomEvent {
	private final GiganticPlayer gp;

	/**
	 * @param gp
	 */
	public GiganticPlayerAvailableEvent(GiganticPlayer gp) {
		super();
		this.gp = gp;
	}

	/**
	 * @return gp
	 */
	public GiganticPlayer getGiganticPlayer() {
		return gp;
	}

}
