package com.github.unchama.player.exp;

import org.bukkit.entity.Player;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.ExpTableManager;
import com.github.unchama.util.ExperienceManager;

public final class ExpManager extends DataManager implements UsingSql,Initializable{

	ExpTableManager tm;

	private ExperienceManager eM;
	private double exp = 0.0;

	public ExpManager(GiganticPlayer gp) {
		super(gp);
		this.tm = sql.getManager(ExpTableManager.class);
	}

	@Override
	public void save(Boolean loginflag){
		tm.save(gp,loginflag);
	}

	@Override
	public void init() {
		Player player = PlayerManager.getPlayer(gp);
		if(player == null)return;
		eM = new ExperienceManager(player);
		eM.setExp(exp);
	}


	public void setExp(float exp) {
		this.exp = (double)exp;
	}

	public int getExp() {
		return eM.getCurrentExp();
	}


	public void changeExp(double exp){
		eM.changeExp(exp);
	}

	public ExperienceManager getExperienceManager() {
		return eM;
	}
}
