package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

/**週の初めに1度だけ実行される
 *
 * @author tar0ss
 *
 */
public class WeeklyEvent extends CustomEvent {
	private int day;
	private TimeType tt;

	public WeeklyEvent(int day){
		this.day = day;
		this.tt = TimeType.WEEK;
	}
	/**日にちを取得
	 *
	 * @return int
	 */
	public int getDay(){
		return day;
	}

	/**時間定数を取得
	 * 
	 * @return
	 */
	public TimeType getTimeType(){
		return tt;
	}
}
