package com.github.unchama.player.seichiskill.passive.mineboost;

public enum BoostType{
	NUMBER_OF_PEOPLE("接続人数"),
	MINUTE_MINE("1分間の採掘量"),
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

	public String getColumnName() {
		return this.name();
	}
}
