package com.github.unchama.player.seichiskill;

import com.github.unchama.player.seichiskill.effect.EffectType;
import com.github.unchama.player.seichiskill.moduler.EffectRunner;
import com.github.unchama.player.seichiskill.premiumeffect.PremiumEffectType;

public enum EffectCategory {
	NORMAL(0),PREMIUM(1);

	private static final int interval = 1000;

	private static final EffectCategory[] eclist = values();


	private final int id;

	EffectCategory(int id){
		this.id = id;
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}


	public static EffectCategory getCategory(int effect_id) {
		int tmp_id = (int)effect_id / getInterval();
		for(EffectCategory ec : eclist){
			if(ec.getId() == tmp_id)return ec;
		}
		return EffectCategory.NORMAL;
	}

	/**
	 * @return interval
	 */
	public static int getInterval() {
		return interval;
	}

	public int getEffectID(int effect_id) {
		return id * getInterval() + effect_id;
	}

	public static Class<? extends EffectRunner> getRunnerClass(int effect_id) {
		EffectCategory ec = getCategory(effect_id);
		Class<? extends EffectRunner> eClass;
		switch(ec){
		case NORMAL:
			eClass = EffectType.getRunnerClass(effect_id);
			break;
		case PREMIUM:
			eClass = PremiumEffectType.getRunnerClass(effect_id);
			break;
		default:
			eClass = null;
			break;

		}
		return eClass;
	}
}
