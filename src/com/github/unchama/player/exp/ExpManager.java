package com.github.unchama.player.exp;

import org.bukkit.entity.Player;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.ExpTableManager;

public final class ExpManager extends DataManager implements UsingSql,Initializable{

	ExpTableManager tm;

	private float exp = 0;

	public ExpManager(GiganticPlayer gp) {
		super(gp);
		this.tm = sql.getManager(ExpTableManager.class);
	}

	@Override
	public void save(Boolean loginflag){
		tm.save(gp,loginflag);
	}

	/**
	 * @return exp
	 */
	public float getExp() {
		return exp;
	}

	/**
	 * @param exp セットする exp
	 */
	public void setExp(float exp) {
		this.exp = exp;
	}

	@Override
	public void init() {
		Player player = PlayerManager.getPlayer(gp);

		if(player == null)return;

		player.setExp((float)exp);
	}

}
