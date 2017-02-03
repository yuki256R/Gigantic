package com.github.unchama.gigantic;

public class Debugmode {

	private Boolean flag;

	public Debugmode(Config config,Gigantic plugin){
		flag = config.getDebugMode(plugin);
	}

	public Boolean state(){
		return flag;
	}


}
