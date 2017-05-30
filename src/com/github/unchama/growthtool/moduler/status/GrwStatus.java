package com.github.unchama.growthtool.moduler.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.moduler.tool.GrwDefine;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * Growth Toolの全アイテムレベル分の情報を保持するためのオブジェクト。<br />
 * ランダム付与となるエンチャントを除き、アイテムレベル毎の設定値はこのオブジェクトに全て集約させる。<br />
 */
public final class GrwStatus extends ArrayList<GrwStateData> {
	// debug Instance
	private static final DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	/**
	 * コンストラクタ。設定値別のレベル毎値リストから、レベル毎のGrwStateData型リストに分類する。<br />
	 *
	 * @param baseitem ベースアイテムが変化するアイテムレベルと変化先MaterialのMap
	 * @param nextExp アイテムレベル毎レベルアップまでの必要経験値リスト
	 * @param custom1 アイテムレベル毎に変動するLOREのカスタムメッセージ1
	 * @param custom2 アイテムレベル毎に変動するLOREのカスタムメッセージ2
	 * @param unbreakableLv 耐久無限を付与するアイテムレベル <0: 未設定>
	 */
	public GrwStatus(Map<Integer, Material> baseitem, List<Integer> nextExp, List<List<String>> custom1, List<List<String>> custom2, int unbreakableItemLv) {
		super();
		Material material = Material.GOLD_HELMET;

		// 初期化及び引数チェック
		if (baseitem == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStatus] baseitemがnullのためemptyとして扱います。");
			baseitem = new LinkedHashMap<Integer, Material>();
		}
		if (!baseitem.containsKey(1)) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStatus] baseitemにLv1のMaterialが未登録のためGOLD_HELMETとして扱います。");
		}
		if (nextExp == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStatus] nextExpがnullのためemptyとして扱います。");
			nextExp = new ArrayList<Integer>();
		}
		if (nextExp.isEmpty()) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStatus] nextExpがemptyのため500として扱います。");
			nextExp.add(500);
		}
		if (custom1 == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStatus] custom1がnullのためemptyとして扱います。");
			custom1 = new ArrayList<List<String>>();
		}
		if (custom2 == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwStatus] custom2がnullのためemptyとして扱います。");
			custom2 = new ArrayList<List<String>>();
		}

		// レベル毎の格納
		for (int lvkey = 0; lvkey < nextExp.size(); lvkey++) {
			// 引数チェック
			int exp = nextExp.get(lvkey);
			if (exp <= 0) {
				debug.warning(DebugEnum.GROWTHTOOL, "[GrwStatus] nextExpが" + String.valueOf(exp) + "のため500として扱います。");
				exp = 500;
			}

			// ベースアイテムが変化するアイテムレベルなら、保持Materialを更新する
			if (baseitem.containsKey(lvkey + 1)) {
				material = baseitem.get(lvkey + 1);
			}
			// カスタムメッセージが未設定の場合はemptyを、設定されている場合メッセージを取得する
			List<String> ptrCustom1 = custom1.size() > lvkey ? GrwDefine.EMPTYSTRINGLIST : Collections.unmodifiableList(custom1.get(lvkey));
			List<String> ptrCustom2 = custom2.size() > lvkey ? GrwDefine.EMPTYSTRINGLIST : Collections.unmodifiableList(custom2.get(lvkey));
			// 耐久無限を判定する
			boolean unbreakable = (unbreakableItemLv != 0) && (unbreakableItemLv <= lvkey + 1);
			// GrwStateDataオブジェクト生成
			add(new GrwStateData(material, exp, ptrCustom1, ptrCustom2, unbreakable));
		}
	}
}
