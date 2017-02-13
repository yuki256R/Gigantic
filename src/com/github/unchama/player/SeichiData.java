package com.github.unchama.player;


public class SeichiData {
	private int level;
	private PassiveData passivedata;
	private GachaData gachadata;
//	private MineBoostData mineboostdata;
	private PocketData pocketdata;

	public SeichiData(){
		setLevel(1);
		gachadata = new GachaData();
//		mineboostdata = new MineBoostData();
		pocketdata = new PocketData();
	}

//	public MineBoostData getMineboostdata() {
//		return mineboostdata;
//	}

	public PocketData getPocketData(){
		return pocketdata;
	}

	public PassiveData getPassivedata() {
		return passivedata;
	}

	public GachaData getGachadata() {
		return gachadata;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
