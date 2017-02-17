package com.github.unchama.player.mineboost;

import java.util.List;

import com.github.unchama.player.DataManager;
import com.github.unchama.player.GiganticPlayer;

public class MineBoostManager extends DataManager{

	private Boolean flag;
	private Boolean messageflag;
	private List<MineBoost> boostlist;

	public MineBoostManager(GiganticPlayer gp){
		super(gp);
		this.flag = true;
		this.messageflag = false;
	}

	public void forwardOneMinute() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void save() {
	}

	@Override
	public void load() {
	}





}
