package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;

public class HourEvent extends CustomEvent{
	private int hour;

	public HourEvent(int hour){
		this.hour = hour;
	}
	/**初期時間から経過した時間を取得します．(１時間毎にリセットします）
	 *
	 * @return int
	 */
	public int getHour(){
		return hour;
	}
}