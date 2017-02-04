package com.github.unchama.enumdata;

public enum TableEnum {
	PLAYERDATA("playerdata"),
	GACHADATA("gachadata"),
	DONATEDATA("donatedata"),
	MSGACHADATA("msgachadata"),
	;

	private String tablename;

	TableEnum(String tablename){
		this.tablename = tablename;
	}


	public String getTableName(){
		return this.tablename;
	}
}
