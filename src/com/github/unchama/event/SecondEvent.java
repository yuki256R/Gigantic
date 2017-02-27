package com.github.unchama.event;

public class SecondEvent extends CustomEvent{
	private int second;

	public SecondEvent(int second){
		this.second = second;
	}

	public int getSecond(){
		return second;
	}
}
