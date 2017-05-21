package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;

/*毎年一度だけ実行されます
 *
 */
public class YearEvent extends CustomEvent {
	private int y;

	public YearEvent(int y) {
		this.y = y;
	}

	/**年を取得
	 *
	 * @return int
	 */
	public int getYear() {
		return y;
	}
}
