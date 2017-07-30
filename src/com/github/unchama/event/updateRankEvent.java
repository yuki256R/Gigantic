package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.gui.ranking.RankingMenuManager;

/**
 *
 * @author tar0ss
 *
 */
public final class updateRankEvent extends CustomEvent {
	private final String name;
	private final Class<? extends RankingMenuManager> rClass;
	private final int rank;

	public updateRankEvent(Class<? extends RankingMenuManager> rClass, String name, int rank){
		this.rClass = rClass;
		this.name = name;
		this.rank = rank;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return rClass
	 */
	public Class<? extends RankingMenuManager> getRankClass() {
		return rClass;
	}

	/**
	 * @return rank
	 */
	public int getRank() {
		return rank;
	}
}
