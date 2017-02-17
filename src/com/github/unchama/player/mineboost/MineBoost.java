package com.github.unchama.player.mineboost;


public class MineBoost {
	enum BoostType{
		NUMBER_OF_PEOPLE("接続人数"),
		MINED("採掘量"),
		DRAGON_NIGHT_TIME("ドラゲナイタイム"),
		VOTED_BONUS("投票ボーナス"),
		OTHERWISE("その他"),
		;
		private String reason;

		BoostType(String reason){
			this.reason = reason;
		}

		String getReason(){
			return this.reason;
		}

	}

	private int duration;
	private double amplifier;
	private BoostType type;
	private int boostlv;
	private int lastboostlv;


	MineBoost(){

		this.setBoostlv(0);
		this.setLastboostlv(0);

	}


	public int getBoostlv() {
		return boostlv;
	}

	public void setBoostlv(int boostlv) {
		this.boostlv = boostlv;
	}

	public int getLastboostlv() {
		return lastboostlv;
	}

	public void setLastboostlv(int lastboostlv) {
		this.lastboostlv = lastboostlv;
	}

}
