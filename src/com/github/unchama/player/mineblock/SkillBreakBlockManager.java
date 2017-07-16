package com.github.unchama.player.mineblock;

import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.sql.player.SkillBreakBlockTableManager;

/**
 * スキル別破壊量プレイヤーデータ。
 * 新規スキルを作成の際には,こちらにも破壊量を追加すること。
 *
 * @author karayuu
 *
 */
public class SkillBreakBlockManager extends DataManager implements UsingSql{

	public SkillBreakBlockManager(GiganticPlayer gp) {
		super(gp);
	}

	//プレイヤーごとの使用スキルと破壊量のマップ
	private HashMap<ActiveSkillType, Double> skillbreakmap;

	SkillBreakBlockTableManager tm = sql.getManager(SkillBreakBlockTableManager.class);

	public void startup() {
		skillbreakmap = new HashMap<ActiveSkillType, Double>();

		for (ActiveSkillType type : ActiveSkillType.values()) {
			skillbreakmap.put(type, 0.0);
		}
	}

	public void setup(HashMap<ActiveSkillType, Double> set) {
		skillbreakmap = new HashMap<>();

		for (ActiveSkillType type : ActiveSkillType.values()) {
			if (set.containsKey(type)) {
				Double amount = set.get(type);

				skillbreakmap.put(type, amount);
			}
		}
	}

	public void increase(ActiveSkillType type, Double amount) {
		skillbreakmap.put(type, skillbreakmap.get(type) + amount);
	}

	public double getNum(ActiveSkillType type) {
		return skillbreakmap.get(type);
	}

	public HashMap<ActiveSkillType, Double> getNumMap() {
		return skillbreakmap;
	}


	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

}
