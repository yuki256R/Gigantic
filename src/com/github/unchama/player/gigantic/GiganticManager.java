package com.github.unchama.player.gigantic;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.GiganticTableManager;

public class GiganticManager extends DataManager implements UsingSql{
	//累計プレイ時間
	public long playtick;
	//seichiassist読み込み済みフラグ
	public boolean seichi_loaded;

	GiganticTableManager tm;

	public GiganticManager(GiganticPlayer gp){
		super(gp);
		this.tm = sql.getManager(GiganticTableManager.class);
	}

	@Override
	public void save(boolean loginflag){
		tm.save(gp,loginflag);
	}

	@Override
	public void load() {
		tm.load(gp);
	}
}
