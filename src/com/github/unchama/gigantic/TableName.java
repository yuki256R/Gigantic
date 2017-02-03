package com.github.unchama.gigantic;

public enum TableName {
	PLAYERDATA("playerdata"),
	GACHADATA("gachadata"),
	DONATEDATA("donatedata"),
	MSGACHADATA("msgachadata"),
	;

	private String tablename;

	TableName(String tablename){
		this.tablename = tablename;
	}


	public String getTableName(){
		return this.tablename;
	}
}
