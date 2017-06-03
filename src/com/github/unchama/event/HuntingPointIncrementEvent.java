package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.yml.HuntingPointDataManager.HuntingMobType;
/**
 * @author tar0ss
 *
 */
public class HuntingPointIncrementEvent extends CustomEvent{
	private GiganticPlayer gp;
	private HuntingMobType mobType;
	private int increase;
	private int current_pre_point, current_next_point;
	private int total_pre_point, total_next_point;

	public HuntingPointIncrementEvent(GiganticPlayer gp_, String name, int increase_, int current_point, int total_point){
		gp = gp_;
		mobType = HuntingMobType.getMobType(name);
		increase = increase_;
		current_pre_point = current_point;
		current_next_point = current_point + increase_;
		total_pre_point = total_point;
		total_next_point = total_point + increase_;
	}

	/**増加量を取得
	 * @return increase
	 */
	public int getIncrease() {
		return increase;
	}

	/**増加前の現在値を取得
	 * @return all
	 */
	public int getCurrentPrePoint() {
		return current_pre_point;
	}
	/**増加後の現在値を取得
	 * @return all
	 */
	public int getCurentNextPoint() {
		return current_next_point;
	}

	/**増加前の累計値を取得
	 * @return all
	 */
	public int getTotalPrePoint() {
		return total_pre_point;
	}
	/**増加後の累計値を取得
	 * @return all
	 */
	public int getTotalNextPoint() {
		return total_next_point;
	}

	/**
	 * @return gp
	 */
	public GiganticPlayer getGiganticPlayer() {
		return gp;
	}

	// Mobのタイプを取得
	public HuntingMobType getMobType(){
		return mobType;
	}
}
