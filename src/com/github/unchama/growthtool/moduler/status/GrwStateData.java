package com.github.unchama.growthtool.moduler.status;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.moduler.tool.GrwDefine;

/**
 * Growth Toolの1レベル分の情報を保持するためのオブジェクト。<br />
 * ランダム付与となるエンチャントを除き、レベル毎の設定値はこのオブジェクトに全て集約させる。<br />
 *
 * @author CrossHearts
 */
public final class GrwStateData {
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
	 * @param unbreakable 耐久無限の設定有無 (false: 耐久有限 / true: 耐久無限)
	 */
	public GrwStateData(Material baseitem, int nextExp, List<String> custom1, List<String> custom2, boolean unbreakable) {
		if (baseitem == null) {
			GrowthTool.GrwDebugWarning("baseがnullのためGOLD_HELMETとして扱います。");
			baseitem = Material.GOLD_HELMET;
		}
		if (nextExp < 0) {
			GrowthTool.GrwDebugWarning("nextExpが" + String.valueOf(nextExp) + "のため0として扱います。");
			nextExp = 0;
		}
		if (custom1 == null) {
			GrowthTool.GrwDebugWarning("custom1がnullのためemptyとして扱います。");
			custom1 = GrwDefine.EMPTYSTRINGLIST;
		}
		if (custom2 == null) {
			GrowthTool.GrwDebugWarning("custom2がnullのためemptyとして扱います。");
			custom2 = GrwDefine.EMPTYSTRINGLIST;
		}
		this.baseitem = baseitem;
		this.nextExp = nextExp;
		this.custom1 = Collections.unmodifiableList(custom1);
		this.custom2 = Collections.unmodifiableList(custom2);
		this.unbreakable = unbreakable;
	}

	/**
	 * Material要ゲッター。このレベルのベースとなっているアイテムを返却する。<br />
	 *
	 * @return ベースアイテムのMaterial
	 */
	public Material getMaterial() {
		return baseitem;
	}

	/**
	 * レベルアップまでに必要な経験値のゲッター。次のレベルアップまでに必要な経験値を返却する。<br />
	 *
	 * @return 次のレベルアップまでに必要な経験値
	 */
	public int getNextExp() {
		return nextExp;
	}

	/**
	 * カスタムメッセージ1用のゲッター。レベル毎に解放されていくメッセージ。<br />
	 *
	 * @return カスタムメッセージ1
	 */
	public List<String> getCustom1() {
		return custom1;
	}

	/**
	 * カスタムメッセージ2用のゲッター。レベル毎に解放されていくメッセージ。<br />
	 *
	 * @return カスタムメッセージ2
	 */
	public List<String> getCustom2() {
		return custom2;
	}

	/**
	 * 耐久無限フラグ用のゲッター。このレベルにおいて耐久無限かどうかを返却する。<br />
	 *
	 * @return 耐久無限フラグ
	 */
	public boolean getUnbreakable() {
		return unbreakable;
	}
}
