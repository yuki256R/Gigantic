package com.github.unchama.player.seichiskill;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.seichiskill.effect.EffectType;
import com.github.unchama.player.seichiskill.moduler.EffectRunner;
import com.github.unchama.player.seichiskill.premiumeffect.PremiumEffectType;
import com.github.unchama.util.Util;
import com.github.unchama.yml.CustomHeadManager;

public enum EffectCategory {
	NORMAL(0, ChatColor.BLUE + "ノーマル", "f_cube"),
	PREMIUM(1, ChatColor.AQUA + "プレミアム", "f_cube2");

	private static final int interval = 1000;

	private static final EffectCategory[] eclist = values();

	private final int id;
	private final String name;
	private final String menuHead;

	EffectCategory(int id, String name, String menuHead) {
		this.id = id;
		this.name = name;
		this.menuHead = menuHead;
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}


	/**セレクトメニューで使用するItemStackを取得します
	 *
	 * @return
	 */
	public ItemStack getMenuItem() {
		ItemStack ans = Gigantic.yml.getManager(CustomHeadManager.class).getMobHead(menuHead);
		Util.setDisplayName(ans,getName() + "エフェクト");
		return ans;
	}

	/**インターバル分を抜いた値を取得します．
	 *
	 * @param effect_id
	 * @return
	 */
	public int getSlot(int effect_id) {
		return effect_id % getInterval();
	}

	public ItemStack getSellectButton(int effect_id) {
		switch(this){
		case NORMAL:
			return EffectType.getSellectButton(effect_id);
		case PREMIUM:
			return PremiumEffectType.getSellectButton(effect_id);
		default:
			return EffectType.getSellectButton(effect_id);
		}
	}


	public static EffectCategory getCategory(int effect_id) {
		int tmp_id = (int) effect_id / getInterval();
		for (EffectCategory ec : eclist) {
			if (ec.getId() == tmp_id)
				return ec;
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
		switch (ec) {
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

	public static String getName(int effect_id) {
		EffectCategory ec = getCategory(effect_id);
		String name;
		switch (ec) {
		case NORMAL:
			name = EffectType.getNamebyID(effect_id);
			break;
		case PREMIUM:
			name = PremiumEffectType.getNamebyID(effect_id);
			break;
		default:
			name = EffectType.getNamebyID(effect_id);
			break;
		}
		return name;
	}

	public static EffectCategory getCategorybyID(int i) {
		for(EffectCategory ec : eclist){
			if(ec.getId() == i){
				return ec;
			}
		}
		return EffectCategory.NORMAL;
	}



}
