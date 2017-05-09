package com.github.unchama.player.region;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.RegionTableManager;

public class RegionManager extends DataManager implements UsingSql {

	private int rgnum;
	RegionTableManager tm;

	public RegionManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(RegionTableManager.class);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	public int getRgnum() {
		return this.rgnum;
	}

	public void setRgnum(int rgnum) {
		this.rgnum = rgnum;
	}

	public void increase(int i){
		this.rgnum += i;
	}

}