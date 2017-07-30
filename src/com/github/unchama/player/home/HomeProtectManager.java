package com.github.unchama.player.home;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

/**
 *
 * @author yuki_256
 *
 */
public class HomeProtectManager extends DataManager {

	private boolean protect;

	public HomeProtectManager(GiganticPlayer gp) {
		super(gp);
	}

	public boolean getHomeProtect() {
		return this.protect;
	}

	public boolean setHomeProtect(boolean protect) {
		return this.protect = protect;
	}
}