package com.github.unchama.growthtool.moduler.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.moduler.tool.GrwTool;

/**
 * Growth Toolのエンチャント設定を保持するためのオブジェクト。<br />
 * 1種類のGrowth Toolにつき1つ生成される。<br />
 * Growth Toolのエンチャントにはこのオブジェクトを介してアクセスできる。<br />
 *
 * @author CrossHearts
 */
public final class GrwEnchants {
	// Growth ToolがLv1の時点におけるデフォルトエンチャント
	private final Map<Enchantment, Integer> defaultEnchant;
	// エンチャント/最大レベル/解放レベル
	private List<Enchantment> enchantment = new ArrayList<Enchantment>();
	private Map<Enchantment, Integer> maxEnchantLv = new LinkedHashMap<Enchantment, Integer>();
	private Map<Enchantment, Integer> premiseItemLv = new LinkedHashMap<Enchantment, Integer>();

	/**
	 * コンストラクタ。LinkedHashMap&lt;Enchantment, GrwEnchantData&gt;を初期化する。<br />
	 *
	 * @param defaultEnchant 初期エンチャントとして設定するエンチャントとレベルの一覧
	 */
	public GrwEnchants(Map<Enchantment, Integer> defaultEnchant) {
		super();
		this.defaultEnchant = Collections.unmodifiableMap(new LinkedHashMap<Enchantment, Integer>(defaultEnchant));
	}

	/**
	 * エンチャント情報を登録する。<br />
	 * 特定のエンチャントに対して、最大エンチャントレベルと解放アイテムレベルを設定する。<br />
	 *
	 * @param enchant 対象エンチャント
	 * @param maxEnchantLv 対象エンチャントの最大エンチャントレベル
	 * @param premiseItemLv 対象エンチャントが付与可能となる解放アイテムレベル
	 */
	public void add(Enchantment enchantment, Integer maxEnchantLv, Integer premiseItemLv) {
		if (enchantment == null) {
			GrowthTool.GrwDebugWarning("enchantmentがnullのためDURABILITYとして扱います。");
			enchantment = Enchantment.DURABILITY;
		}
		if (maxEnchantLv <= 0) {
			GrowthTool.GrwDebugWarning("maxEnchantLvが" + String.valueOf(maxEnchantLv) + "のため1として扱います。");
			maxEnchantLv = 1;
		}
		if (premiseItemLv <= 0) {
			GrowthTool.GrwDebugWarning("premiseItemLvが" + String.valueOf(premiseItemLv) + "のため1として扱います。");
			premiseItemLv = 1;
		}
		this.enchantment.add(enchantment);
		this.maxEnchantLv.put(enchantment, maxEnchantLv);
		this.premiseItemLv.put(enchantment, premiseItemLv);
	}

	/**
	 * デフォルトのエンチャントを返却する。<br />
	 * 返却情報はそのままItemStack.addUnsafeEnchantmentsでの使用を想定している。<br />
	 *
	 * @return 初期エンチャントとして設定するエンチャントとレベルの一覧
	 */
	public final Map<Enchantment, Integer> getDefaultEnchant() {
		return defaultEnchant;
	}

	/**
	 * レベルアップ時に現在付与可能なエンチャントから1つを選択し、ランダムなエンチャントに1Lv加算する。<br />
	 * 無効なGrowth Toolを引数とした場合、何も行わずに返却する。<br />
	 *
	 * @param grwtool 加算したエンチャント (null: 付与可能エンチャント無し)
	 */
	public final Enchantment addEnchant(GrwTool grwtool) {
		Collections.shuffle(enchantment);
		for (Enchantment enchant : enchantment) {
			if (grwtool.getEnchantmentLevel(enchant) < maxEnchantLv.get(enchant) && grwtool.getItemLv() >= premiseItemLv.get(enchant)) {
				grwtool.addUnsafeEnchantment(enchant, grwtool.getEnchantmentLevel(enchant) + 1);
				return enchant;
			}
		}
		return null;
	}
}
