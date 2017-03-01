package com.github.unchama.sql.moduler;

import com.github.unchama.player.GiganticPlayer;

public interface GiganticLoadable {
	public abstract Boolean load(GiganticPlayer gp);
	public abstract Boolean save(GiganticPlayer gp,boolean loginflag);
}
