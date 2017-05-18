package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;

/**毎月一度だけ実行されます
 *
 * @author tar0ss
 *
 */
public class MonthlyEvent extends CustomEvent {
	private int month;

	public MonthlyEvent(int month){
		this.month = month;
	}
	/**月を取得
	 *
	 * @return int
	 */
	public int getMonth(){
		return month;
	}
}
