package com.github.unchama.gui.seichiskill.active.condensation;

import com.github.unchama.gui.moduler.EffectSellectMenuManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;

public final class C_EffectSellectMenuManager extends EffectSellectMenuManager {

	@Override
	protected ActiveSkillType getActiveSkillType() {
		return ActiveSkillType.CONDENSATION;
	}

}
