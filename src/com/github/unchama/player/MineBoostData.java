package com.github.unchama.player;

import java.util.ArrayList;
import java.util.List;

public class MineBoostData {
	private Boolean flag;
	private Boolean messageflag;
	private int boostlv;
	private int lastboostlv;
	private List<EffectData> effectdatalist;
	private MineBlock mineblock;

	public MineBoostData(){
		this.setFlag(true);
		this.setMessageflag(true);
		this.setBoostlv(0);
		this.setLastboostlv(0);
		this.setEffectdatalist(new ArrayList<EffectData>());
		this.setMineblock(new MineBlock());
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
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

	public List<EffectData> getEffectdatalist() {
		return effectdatalist;
	}

	public void setEffectdatalist(List<EffectData> effectdatalist) {
		this.effectdatalist = effectdatalist;
	}

	public Boolean getMessageflag() {
		return messageflag;
	}

	public void setMessageflag(Boolean messageflag) {
		this.messageflag = messageflag;
	}


	public MineBlock getMineblock() {
		return mineblock;
	}

	public void setMineblock(MineBlock mineblock) {
		this.mineblock = mineblock;
	}


}
