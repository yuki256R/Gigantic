package com.github.unchama.player.seichiskill;

import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichiskill.moduler.EffectRunner;
import com.github.unchama.sql.player.SkillEffectTableManager;

public final class SkillEffectManager extends DataManager implements UsingSql{
	SkillEffectTableManager tm;

	//現在選択されているエフェクトの識別番号
	private int id;

	//全てのエフェクトの獲得状況マップ
	private HashMap<Integer, Boolean> map;
	//エフェクトの実行部分を扱うクラス
	private EffectRunner runner;

	public SkillEffectManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(SkillEffectTableManager.class);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	/**エフェクトを実行します．
	 *
	 * @param breaklist
	 * @param liquidlist
	 */
	public void run(List<Block> breaklist, List<Block> liquidlist){
		Class<? extends EffectRunner> ec = this.getRunnerClass();
		runner = null;
		try {
			runner = ec.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			plugin.getLogger().warning("Failed to create  new Effect Instance of player:" + gp.name);
			e.printStackTrace();
			return;
		}
		runner.call(breaklist, liquidlist);
	}


	private Class<? extends EffectRunner> getRunnerClass() {
		return EffectCategory.getRunnerClass(id);
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
	public HashMap<Integer, Boolean> getEffectFlagMap() {
		return map;
	}

	/**
	 * @param map セットする map
	 */
	public void setEffectFlagMap(HashMap<Integer, Boolean> map) {
		this.map = map;
	}

}
