package com.github.unchama.gui.seichiskill.active.explosion;

import com.github.unchama.gui.moduler.EffectSellectManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;

public final class E_EffectSellectMenu extends EffectSellectManager {

	@Override
	protected ActiveSkillType getActiveSkillType() {
		return ActiveSkillType.EXPLOSION;
	}

}
