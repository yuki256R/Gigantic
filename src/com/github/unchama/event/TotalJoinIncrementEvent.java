package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;
/**
 * @author tar0ss
 *
 */
public class TotalJoinIncrementEvent extends CustomEvent {

	private final GiganticPlayer gp;
	private final double increase, pre_all, next_all;

	public TotalJoinIncrementEvent(GiganticPlayer gp, double increase, double pre_all) {
		this.gp = gp;
		this.pre_all = pre_all;
		this.next_all = pre_all + increase;
		this.increase = increase;
	}

	/**増加量を取得
	 * @return increase
	 */
	public double getIncrease() {
		return increase;
	}

	/**増加前の値を取得
	 * @return pre_all
	 */
	public double getPreAll() {
		return pre_all;
	}

	/**増加後の値を取得
	 * @return next_all
	 */
	public double getNextAll() {
		return next_all;
	}

	/**
	 * @return gp
	 */
	public GiganticPlayer getGiganticPlayer() {
		return gp;
	}

}
