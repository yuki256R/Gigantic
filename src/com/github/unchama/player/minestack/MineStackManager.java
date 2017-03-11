package com.github.unchama.player.minestack;

import java.util.LinkedHashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.MineStackTableManager;

public class MineStackManager extends DataManager implements UsingSql{
	MineStackTableManager tm ;
	public LinkedHashMap<StackType,MineStack> datamap;

	public MineStackManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(MineStackTableManager.class);
		this.datamap = new LinkedHashMap<StackType,MineStack>();
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}
}
