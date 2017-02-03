package com.github.unchama.gigantic;

public class Maintenance {

	private Boolean flag;


	public Maintenance(){
		flag = false;
	}

	public void on(){
		flag = true;
	}

	public void off(){
		flag = false;
	}

	public Boolean state(){
		return flag;
	}
}
