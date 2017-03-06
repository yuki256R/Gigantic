package com.github.unchama.player.minestack;

import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.MineStackTableManager;

public class MineStackManager extends DataManager implements UsingSql{
	MineStackTableManager tm ;
	public HashMap<StackType,MineStack> datamap;

	public MineStackManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(MineStackTableManager.class);
		this.datamap = new HashMap<StackType,MineStack>();
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}


}
