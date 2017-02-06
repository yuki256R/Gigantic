package com.github.unchama.player;

public class PassiveData {
	private MineBoostData mineboostdata;
	private PocketData pocketdata;

	public PassiveData(){
		mineboostdata = new MineBoostData();
		pocketdata = new PocketData();
	}

	public MineBoostData getMineboostdata() {
		return mineboostdata;
	}

	public PocketData getPocketData(){
		return pocketdata;
	}


}
