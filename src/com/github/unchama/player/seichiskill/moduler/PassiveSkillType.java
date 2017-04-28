package com.github.unchama.player.seichiskill.moduler;

import com.github.unchama.player.seichiskill.passive.manarecovery.ManaRecoveryManager;
import com.github.unchama.player.seichiskill.passive.mineboost.MineBoostManager;

public enum PassiveSkillType {
	MINEBOOST(MineBoostManager.class),MANARECOVERY(ManaRecoveryManager.class),
	;
	private Class<? extends PassiveSkillManager> skillClass;

	PassiveSkillType(Class<? extends PassiveSkillManager> skillClass) {
		this.skillClass = skillClass;
	}

	/**
	 * スキルを管理するクラスを取得します．
	 *
	 * @return
	 */
	public Class<? extends PassiveSkillManager> getSkillClass() {
		return skillClass;
	}
}
