package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

/**毎日0時に実行される
 *
 * @author tar0ss
 *
 */
public class DailyEvent extends CustomEvent {
	private int day;
	private int wday;
	private TimeType tt;

	public DailyEvent(int day,int wday){
		this.day = day;
		this.wday = wday;
		this.tt = TimeType.DAY;
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

	/**時間定数を取得
	 * 
	 * @return
	 */
	public TimeType getTimeType(){
		return tt;
	}
}
