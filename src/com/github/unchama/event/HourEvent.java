package com.github.unchama.event;

public class HourEvent extends CustomEvent{
	private int hour;

	public HourEvent(int hour){
		this.hour = hour;
	}

	public int getHour(){
		return hour;
	}
}