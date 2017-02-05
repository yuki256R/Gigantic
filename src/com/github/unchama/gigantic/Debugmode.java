package com.github.unchama.gigantic;

public class Debugmode {

	private Boolean flag;

	public Debugmode(){
		this.flag = Gigantic.config.getDebugMode();
	}

	public Boolean state(){
		return flag;
	}


}
