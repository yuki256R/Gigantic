package com.github.unchama.enumdata;

public enum DebugEnum {
	ALL(false),
	MINEBOOST(false),
	;
	private Boolean flag;
	
	private DebugEnum(Boolean flag){
		this.flag = flag;
	}
	
	public Boolean getDefaultFlag(){
		return flag;
	}
	
}
