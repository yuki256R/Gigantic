package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

/*毎年一度だけ実行されます
 *
 */
public class YearEvent extends CustomEvent {
	private int y;
	private TimeType tt;

	public YearEvent(int y) {
		this.y = y;
		this.tt = TimeType.YEAR;
	}

	/**年を取得
	 *
	 * @return int
	 */
	public int getYear() {
		return y;
	}

	/**時間定数を取得
	 * 
	 * @return
	 */
	public TimeType getTimeType(){
		return tt;
	}
}
