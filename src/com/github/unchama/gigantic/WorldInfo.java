package com.github.unchama.gigantic;

public class WorldInfo {
	//ワールド名
	private String name;
	//スキル破壊のログ保存
	private Boolean logflag;
	//保護
	private Boolean protectflag;

	WorldInfo(String _name,Boolean _log,Boolean _protect){
		name = _name;
		logflag = _log;
		protectflag = _protect;
	}

	public String getName(){
		return name;
	}
	public Boolean getLogFlag(){
		return logflag;
	}
	public Boolean getProtectFlag(){
		return protectflag;
	}
}
