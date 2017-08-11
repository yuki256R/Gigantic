package com.github.unchama.player.seichiskill;

import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.point.GiganticPointManager;
import com.github.unchama.player.point.UnchamaPointManager;
import com.github.unchama.player.seichiskill.effect.EffectType;
import com.github.unchama.player.seichiskill.giganticeffect.GiganticEffectType;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.effect.EffectRunner;
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

	/**エフェクトを回すランナーを取得します．
	 *
	 * @return EffectRunner
	 */
	public EffectRunner createRunner(ActiveSkillType st) {
		Class<? extends EffectRunner> ec = this.getRunnerClass(st);
		runner = null;
		try {
			runner = ec.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			plugin.getLogger().warning("Failed to create  new Effect Instance of player:" + gp.name);
			e.printStackTrace();
		}
		return runner;
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

	/**指定されたエフェクトを解除する
	 *
	 * @param effect_id
	 */
	public void unlock(int effect_id) {
		fmap.put(effect_id, true);
	}

	public boolean canBuyEffect(int effect_id) {
		EffectCategory ec = EffectCategory.getCategory(effect_id);
		return canBuyEffect(effect_id, ec);
	}

	public boolean canBuyEffect(int effect_id, EffectCategory ec) {
		switch (ec) {
		case GIGANTIC:
			GiganticEffectType gt = GiganticEffectType.getbyID(effect_id);
			int useDonatePoint = gt.getUseDonatePoint();
			GiganticPointManager gM = gp.getManager(GiganticPointManager.class);
			int gpoint = gM.getPoint();
			if (useDonatePoint <= gpoint) {
				return true;
			} else {
				return false;
			}
		case NORMAL:
			EffectType t = EffectType.getbyID(effect_id);
			int useVotePoint = t.getUseVotePoint();
			UnchamaPointManager uM = gp.getManager(UnchamaPointManager.class);
			int upoint = uM.getPoint();
			if (useVotePoint <= upoint) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void unlockonShop(int effect_id, EffectCategory ec) {
		switch (ec) {
		case GIGANTIC:
			GiganticEffectType gt = GiganticEffectType.getbyID(effect_id);
			int useDonatePoint = gt.getUseDonatePoint();
			GiganticPointManager gM = gp.getManager(GiganticPointManager.class);
			int gpoint = gM.getPoint();
			if (useDonatePoint <= gpoint) {
				gM.addPoint(-gt.getUseDonatePoint());
				this.unlock(effect_id);
			}
			return;
		case NORMAL:
			EffectType t = EffectType.getbyID(effect_id);
			int useVotePoint = t.getUseVotePoint();
			UnchamaPointManager uM = gp.getManager(UnchamaPointManager.class);
			int upoint = uM.getPoint();
			if (useVotePoint <= upoint) {
				uM.addPoint(-t.getUseVotePoint());
				this.unlock(effect_id);
			}
			return;
		}
	}

	public String getCurrentPointString(EffectCategory ec) {
		switch(ec){
		case GIGANTIC:
			GiganticPointManager gM = gp.getManager(GiganticPointManager.class);
			return "保有GP:" + gM.getPoint();
		case NORMAL:
			UnchamaPointManager uM = gp.getManager(UnchamaPointManager.class);
			return "保有GP:" + uM.getPoint();
		}
		return "";
	}

}
