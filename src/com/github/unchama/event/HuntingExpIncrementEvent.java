package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;

/**
*
* @author ten_niti
*
*/
public class HuntingExpIncrementEvent extends CustomEvent{
	private GiganticPlayer gp;
	private double increase;
	private double preExp;
	private double nextExp;

	public HuntingExpIncrementEvent(GiganticPlayer gp_, double increase_, double exp){
		gp = gp_;
		increase = increase_;
		preExp = exp;
		nextExp = exp + increase_;
	}

	/**増加量を取得
	 * @return increase
	 */
	public double getIncrease() {
		return increase;
	}

	/**
	 * 増加前の経験値を取得
	 * @return
	 */
	public double getPreExp(){
		return preExp;
	}

	/**
	 * 増加後の経験値を取得
	 * @return
	 */
	public double getNextExp(){
		return nextExp;
	}

	/**
	 * @return gp
	 */
	public GiganticPlayer getGiganticPlayer() {
		return gp;
	}
}
