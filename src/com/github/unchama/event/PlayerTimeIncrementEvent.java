package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;

/**
*
* @author ten_niti
*
*/
public class PlayerTimeIncrementEvent extends CustomEvent{
	private GiganticPlayer gp;
	private int increase;
	private int pre_tick,next_tick;

	public PlayerTimeIncrementEvent(GiganticPlayer gp_, int increase_, int tick){
		gp = gp_;
		increase = increase_;
		pre_tick = tick;
		next_tick = tick + increase_;
	}

	/**増加量を取得
	 * @return increase
	 */
	public int getIncrease() {
		return increase;
	}

	/**
	 * 増加前のtick数
	 * @return
	 */
	public int getPreTick(){
		return pre_tick;
	}

	/**
	 * 増加後のtick数
	 * @return
	 */
	public int getNextTick(){
		return next_tick;
	}

	/**
	 * @return gp
	 */
	public GiganticPlayer getGiganticPlayer() {
		return gp;
	}
}
