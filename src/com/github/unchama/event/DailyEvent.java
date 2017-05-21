package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;

/**毎日0時に実行される
 *
 * @author tar0ss
 *
 */
public class DailyEvent extends CustomEvent {
	private int day;
	private int wday;

	public DailyEvent(int day,int wday){
		this.day = day;
		this.wday = wday;
	}
	/**日にちを取得
	 *
	 * @return int
	 */
	public int getDay(){
		return day;
	}
	/**曜日を取得
	 *
	 * @return int
	 */
	public int getDayofWeek(){
		return wday;
	}
}
