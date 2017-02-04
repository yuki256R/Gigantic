package com.github.unchama.gigantic;

public class Debugmode {

	private Boolean flag;

	public Debugmode(Config config){
		flag = config.getDebugMode();
	}

	public Boolean state(){
		return flag;
	}


}
