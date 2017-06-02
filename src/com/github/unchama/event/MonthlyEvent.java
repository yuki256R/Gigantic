package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

/**毎月一度だけ実行されます
 *
 * @author tar0ss
 *
 */
public class MonthlyEvent extends CustomEvent {
	private int month;
	private TimeType tt;

	public MonthlyEvent(int month){
		this.month = month;
		this.tt = TimeType.MONTH;
	}
	/**月を取得
	 *
	 * @return int
	 */
	public int getMonth(){
		return month;
	}

	/**時間定数を取得
	 * 
	 * @return
	 */
	public TimeType getTimeType(){
		return tt;
	}
}
