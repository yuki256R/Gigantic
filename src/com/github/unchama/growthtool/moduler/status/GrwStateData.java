package com.github.unchama.growthtool.moduler.status;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.moduler.tool.GrwDefine;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * Growth Toolの1レベル分の情報を保持するためのオブジェクト。<br />
 * ランダム付与となるエンチャントを除き、レベル毎の設定値はこのオブジェクトに全て集約させる。<br />
 */
public final class GrwStateData {
	// debug Instance
	private static final DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	// ベースアイテムMaterial
	private final Material baseitem;
	// レベルアップまでの必要経験値
	private final int nextExp;
	// カスタムメッセージ1
	private final List<String> custom1;
	// カスタムメッセージ2
	private final List<String> custom2;
	// 耐久無限の設定有無
	private final boolean unbreakable;

	/**
	 * コンストラクタ。アイテムレベル1レベル分の情報を引数として、それらの情報をシャローコピーして保持する。<br />
	 *
	 * @param baseitem Growth ToolのベースアイテムMaterial
	 * @param nextExp レベルアップまでの必要経験値
	 * @param custom1 アイテムレベル毎に変動するLOREのカスタムメッセージ1
	 * @param custom2 アイテムレベル毎に変動するLOREのカスタムメッセージ2
	 * @param unbreakable 耐久無限の設定有無 <false: 耐久有限 / true: 耐久無限>
	 */
	public GrwStateData(Material baseitem, int nextExp, List<String> custom1, List<String> custom2, boolean unbreakable) {
		if (baseitem == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStateData] baseがnullのためGOLD_HELMETとして扱います。");
			baseitem = Material.GOLD_HELMET;
		}
		if (nextExp <= 0) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStateData] nextExpが" + String.valueOf(nextExp) + "のため500として扱います。");
			nextExp = 500;
		}
		if (custom1 == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStateData] custom1がnullのためemptyとして扱います。");
			custom1 = GrwDefine.EMPTYSTRINGLIST;
		}
		if (custom2 == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStateData] custom2がnullのためemptyとして扱います。");
			custom2 = GrwDefine.EMPTYSTRINGLIST;
		}
		this.baseitem = baseitem;
		this.nextExp = nextExp;
		this.custom1 = Collections.unmodifiableList(custom1);
		this.custom2 = Collections.unmodifiableList(custom2);
		this.unbreakable = unbreakable;
	}

	public Material getMaterial() {
		return baseitem;
	}

	public int getNextExp() {
		return nextExp;
	}

	public List<String> getCustom1() {
		return custom1;
	}

	public List<String> getCustom2() {
		return custom2;
	}

	public boolean getUnbreakable() {
		return unbreakable;
	}
}
