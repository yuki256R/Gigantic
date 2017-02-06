package com.github.unchama.player;

public class SqlData {
	//読み込み済みフラグ
	public boolean loaded;

	public SqlData(){
		this.loaded = false;
	}

	public Boolean isLoaded(){
		return loaded;
	}
}
