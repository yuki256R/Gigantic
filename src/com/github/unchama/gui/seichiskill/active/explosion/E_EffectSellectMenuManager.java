package com.github.unchama.gui.seichiskill.active.explosion;

import com.github.unchama.gui.moduler.EffectSellectMenuManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;

public final class E_EffectSellectMenuManager extends EffectSellectMenuManager {

	@Override
	protected ActiveSkillType getActiveSkillType() {
		return ActiveSkillType.EXPLOSION;
	}

}
