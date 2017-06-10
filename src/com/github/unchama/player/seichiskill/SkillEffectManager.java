package com.github.unchama.player.seichiskill;

import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.SkillEffectTableManager;

public final class SkillEffectManager extends DataManager implements UsingSql{
	SkillEffectTableManager tm;

	//現在選択されているエフェクトの識別番号
	private int id;

	//全てのエフェクトの獲得状況マップ
	private HashMap<Integer,Boolean> map;

	public SkillEffectManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(SkillEffectTableManager.class);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id セットする id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return map
	 */
	public HashMap<Integer,Boolean> getEffectFlagMap() {
		return map;
	}

	/**
	 * @param map セットする map
	 */
	public void setEffectFlagMap(HashMap<Integer,Boolean> map) {
		this.map = map;
	}

}
