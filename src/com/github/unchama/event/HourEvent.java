package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;

public class HourEvent extends CustomEvent{
	private int hour;

	public HourEvent(int hour){
		this.hour = hour;
	}
	/**何時か取得します．（24時間表記）
	 *
	 * @return int
	 */
	public int getHour(){
		return hour;
	}
}