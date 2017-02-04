package com.github.unchama.enumdata;


public enum WorldEnum {
	world_SW("world_SW",false,true),
	world_SW_2("world_SW_2",false,true),
	world_SW_nether("world_SW_nether",false,true),
	world_SW_the_end("world_SW_the_end",false,true),
	;

	private String name;
	//coreprotectのログを取得するかどうか
	private Boolean logflag;
	//ワールドガードを使用できるワールドかどうか
	private Boolean guardflag;

	WorldEnum(String name,Boolean logflag,Boolean guardflag){
		this.name = name;
		this.logflag = logflag;
		this.guardflag = guardflag;
	}


	public String getName(){
		return this.name;
	}

	public Boolean getLogFlag(){
		return this.logflag;
	}

	public Boolean getGuardFlag(){
		return this.guardflag;
	}

	//nameからflagを取得する
	public static Boolean getLogFlagbyName(String s){

		for(WorldEnum wn : values()){
			if(wn.getName().equals(s)){
				return wn.getLogFlag();
			}
		}

		return true;
	}

	//nameからflagを取得する
	public static Boolean getGuardFlagbyName(String s){

		for(WorldEnum wn : values()){
			if(wn.getName().equals(s)){
				return wn.getGuardFlag();
			}
		}

		return false;
	}


}

