package com.github.unchama.gigantic;


public enum WorldName {
	world_SW("world_SW",true,true),
	world_SW_2("world_SW_2",true,true),
	world_SW_nether("world_SW_nether",true,true),
	world_SW_the_end("world_SW_the_end",true,true),
	;

	private String name;
	private Boolean logflag;
	private Boolean protectflag;

	WorldName(String name,Boolean logflag,Boolean protectflag){
		this.name = name;
		this.logflag = logflag;
		this.protectflag = protectflag;
	}


	public String getName(){
		return this.name;
	}

	public Boolean getLogFlag(){
		return this.logflag;
	}

	public Boolean getProtectFlag(){
		return this.protectflag;
	}

	public static Boolean getLogFlagbyName(String s){

		for(WorldName wn : values()){
			if(wn.getName().equals(s)){
				return wn.getLogFlag();
			}
		}

		return false;
	}

	public static Boolean getProtectFlagbyName(String s){

		for(WorldName wn : values()){
			if(wn.getName().equals(s)){
				return wn.getProtectFlag();
			}
		}

		return false;
	}


}

