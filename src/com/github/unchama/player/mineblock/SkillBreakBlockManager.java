package com.github.unchama.player.mineblock;

import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;

public class SkillBreakBlockManager extends DataManager implements UsingSql{

	public SkillBreakBlockManager(GiganticPlayer gp) {
		super(gp);
		startup();
	}

	//プレイヤーごとの使用スキルと破壊量のマップ
	private HashMap<ActiveSkillType, Double> skillbreakmap;

	public void startup() {
		skillbreakmap = new HashMap<ActiveSkillType, Double>();

		for (ActiveSkillType type : ActiveSkillType.values()) {
			skillbreakmap.put(type, 0.0);
		}
	}

	public void setup(HashMap<ActiveSkillType, Double> set) {
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

	}

}
