package com.github.unchama.event;

public class MinuteEvent extends CustomEvent{
	private int minute;

	public MinuteEvent(int minute){
		this.minute = minute;
	}

	public int getMinute(){
		return minute;
	}
}
