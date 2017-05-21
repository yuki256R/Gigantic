package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;

/**週の初めに1度だけ実行される
 *
 * @author tar0ss
 *
 */
public class WeeklyEvent extends CustomEvent {
	private int day;

	public WeeklyEvent(int day){
		this.day = day;
	}
	/**日にちを取得
	 *
	 * @return int
	 */
	public int getDay(){
		return day;
	}
}
