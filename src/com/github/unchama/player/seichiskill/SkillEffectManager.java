package com.github.unchama.player.seichiskill;

import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.EffectRunner;
import com.github.unchama.sql.player.SkillEffectTableManager;

public final class SkillEffectManager extends DataManager implements UsingSql {
	SkillEffectTableManager tm;

	//現在選択されているエフェクトの識別番号
	private HashMap<ActiveSkillType, Integer> eMap;

	//全てのエフェクトの獲得状況マップ
	private HashMap<Integer, Boolean> fmap;
	//エフェクトの実行部分を扱うクラス
	private EffectRunner runner;

	public SkillEffectManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(SkillEffectTableManager.class);
		eMap = new HashMap<ActiveSkillType, Integer>();
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	/**
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param t
	 */
	public void run(ActiveSkillType st, List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		Class<? extends EffectRunner> ec = this.getRunnerClass(st);
		runner = null;
		try {
			runner = ec.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			plugin.getLogger().warning("Failed to create  new Effect Instance of player:" + gp.name);
			e.printStackTrace();
			return;
		}
		runner.call(st, breaklist, liquidlist, alllist, range);
	}

	private Class<? extends EffectRunner> getRunnerClass(ActiveSkillType st) {
		return EffectCategory.getRunnerClass(eMap.get(st));
	}

	/**
	 *
	 * @param t
	 * @return
	 */
	public int getId(ActiveSkillType t) {
		return eMap.get(t);
	}

	/**
	 *
	 * @param id
	 * @param t
	 */
	public void setId(ActiveSkillType t, int id) {
		eMap.put(t, id);
	}

	/**
	 *
	 * @return
	 */
	public HashMap<Integer, Boolean> getEffectFlagMap() {
		return fmap;
	}

	/**
	 * @param map セットする map
	 */
	public void setEffectFlagMap(HashMap<Integer, Boolean> map) {
		this.fmap = map;
	}

	/**エフェクトの名前
	 *
	 * @param st
	 * @return
	 */
	public String getName(ActiveSkillType st) {
		return EffectCategory.getName(eMap.get(st));
	}

}
