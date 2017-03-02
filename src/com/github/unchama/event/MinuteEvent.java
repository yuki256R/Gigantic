package com.github.unchama.event;

public class MinuteEvent extends CustomEvent{
	private int minute;

	public MinuteEvent(int minute){
		this.minute = minute;
	}
	/**初期時間から経過した分数を取得します．(１時間毎にリセットします）
	 * 
	 * @return int
	 */
	public int getMinute(){
		return minute;
	}
}
