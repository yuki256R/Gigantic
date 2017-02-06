package com.github.unchama.player;

public class SeichiData {
	private int level;
	private PassiveData passivedata;
	private GachaData gachadata;

	public SeichiData(){
		setLevel(1);
		gachadata = new GachaData();
		passivedata = new PassiveData();
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
