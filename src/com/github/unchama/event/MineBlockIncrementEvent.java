package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;
/**
 * @author tar0ss
 *
 */
public class MineBlockIncrementEvent extends CustomEvent{

	private GiganticPlayer gp;
	private int increase;
	private double pre_all,next_all;

	public MineBlockIncrementEvent(GiganticPlayer gp, int increase, double all) {
		this.gp = gp;
		this.pre_all = all;
		this.next_all = all + increase;
	}

	/**増加量を取得
	 * @return increase
	 */
	public int getIncrease() {
		return increase;
	}

	/**増加前の値を取得
	 * @return all
	 */
	public double getPreAll() {
		return pre_all;
	}
	/**増加後の値を取得
	 * @return all
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
