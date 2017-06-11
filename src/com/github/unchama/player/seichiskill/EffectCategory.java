package com.github.unchama.player.seichiskill;

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
}
