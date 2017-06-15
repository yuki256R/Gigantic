package com.github.unchama.growthtool.moduler.status;

import com.github.unchama.growthtool.GrowthTool;

/**
 * Growth Toolにおけるエンチャント1種類分の情報を保持するためのオブジェクト。<br />
 * 設定対象として定義されたエンチャントの種類分のオブジェクトが生成される。<br />
 * オブジェクト生成により設定対象として扱うため、設定対象ではないエンチャントのオブジェクトを生成してはいけない。<br />
 */
public final class GrwEnchantData {
	// 対象エンチャントの最大エンチャントレベル
	private final int maxEnchantLv;
	// 対象エンチャントが付与可能となる解放アイテムレベル
	private final int premiseItemLv;

	/**
	 * コンストラクタ。エンチャント1種類分の情報を引数として保持する。<br />
	 *
	 * @param maxEnchantLv 対象エンチャントの最大エンチャントレベル
	 * @param premiseItemLv 対象エンチャントが付与可能となる解放アイテムレベル
	 */
	public GrwEnchantData(int maxEnchantLv, int premiseItemLv) {
		if (maxEnchantLv <= 0) {
			GrowthTool.GrwDebugWarning("maxEnchantLvが" + String.valueOf(maxEnchantLv) + "のため1として扱います。");
			maxEnchantLv = 1;
		}
		if (premiseItemLv <= 0) {
			GrowthTool.GrwDebugWarning("premiseItemLvが" + String.valueOf(premiseItemLv) + "のため1として扱います。");
			premiseItemLv = 1;
		}
		this.maxEnchantLv = maxEnchantLv;
		this.premiseItemLv = premiseItemLv;
	}

	/**
	 * 対象エンチャントが付与可能かを判定する。<br />
	 *
	 * @param currentItemLv 現在のアイテムレベル
	 * @param currentEnchantLv 現在のエンチャントレベル
	 * @return 付与判定 <true:付与可能 / false:付与不可能>
	 */
	public final boolean canAddEnchantment(int currentItemLv, int currentEnchantLv) {
		return (currentEnchantLv >= maxEnchantLv) && (currentItemLv >= premiseItemLv);
	}
}
