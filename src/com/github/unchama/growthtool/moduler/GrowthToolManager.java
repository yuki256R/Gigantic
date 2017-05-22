package com.github.unchama.growthtool.moduler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.github.unchama.yml.GrowthToolDataManager;

public abstract class GrowthToolManager {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	// 配色設定
	private static final String NAMEHEAD = ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "";
	private static final String IDENTHEAD = ChatColor.RESET + "" + ChatColor.AQUA + "";
	private static final String ILHEAD = ChatColor.RESET + "" + ChatColor.RED + "" + ChatColor.BOLD + "アイテムLv. ";
	private static final String CUSTOM1HEAD = ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "";
	private static final String CUSTOM2HEAD = ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.ITALIC + "";
	private static final String OWNERHEAD = ChatColor.RESET + "" + ChatColor.DARK_GREEN + "所有者：";

	// ドロップバランス
	private int dropBalanceRate;
	// 識別用固有メッセージ
	private List<String> identLore;
	// wikiTips一覧
	private List<String> wikiTipsMsg;
	// ベースアイテム一覧
	private Map<Integer, Material> bases;
	// ステータス一覧
	private List<State> status;
	// デフォルトエンチャント
	private Map<Enchantment, Integer> defench;
	// エンチャント一覧
	private List<Enchant> enchant;
	// 耐久無限レベル
	private int unbreakable;
	// 固有Tipsメッセージリスト
	private List<String> originalTipsMsg;
	// 整地時のメッセージリスト
	private List<String> onBlockBreakMsg;
	// 命名時のメッセージリスト
	private List<String> onRenameItemMsg;
	// 討伐時のメッセージリスト
	private List<String> onMonsterKillMsg;
	// 被ダメージ時のメッセージリスト
	private List<String> onGetDamageMsg;
	// 破損警告時のメッセージリスト
	private List<String> onWarnItemMsg;
	// 破損時のメッセージリスト
	private List<String> onBreakItemMsg;
	// プレイヤーログアウト時のメッセージリスト
	private List<String> onPlayerQuitMsg;

	protected abstract boolean isEquip(Player player);

	public abstract boolean rename(Player player, String name);

	public GrowthToolManager(GrowthToolType type) {
		// ymlからの読み込み
		GrowthToolDataManager configmanager = Gigantic.yml.getManager(GrowthToolDataManager.class);
		dropBalanceRate = configmanager.getDropBalance(type);
		identLore = configmanager.getIdent(type);
		// wiki
		loadTips(configmanager.getWikiUrl(type));
		// bases
		bases = configmanager.getBaseItem(type);
		// status
		List<Integer> exp = configmanager.getExp(type);
		List<List<String>> custom1 = configmanager.getStringListList(type, "custom1");
		List<List<String>> custom2 = configmanager.getStringListList(type, "custom2");
		status = new ArrayList<State>();
		for (int lv = 0; lv < exp.size(); lv++) {
			status.add(new State(exp.get(lv), custom1.get(lv), custom2.get(lv)));
		}
		defench = configmanager.getDefaultEnchantment(type);
		// enchant
		enchant = new ArrayList<Enchant>();
		Map<Enchantment, List<Integer>> ench = configmanager.getEnchantments(type);
		for (Map.Entry<Enchantment, List<Integer>> e : ench.entrySet()) {
			enchant.add(new Enchant(e.getKey(), e.getValue().get(0), e.getValue().get(1)));
		}
		unbreakable = configmanager.getUnbreakableLv(type);
		// メッセージリスト
		originalTipsMsg = configmanager.getStringList(type, "tipsmsg");
		onBlockBreakMsg = configmanager.getStringList(type, "breakmsg");
		onRenameItemMsg = configmanager.getStringList(type, "renamemsg");
		onMonsterKillMsg = configmanager.getStringList(type, "killmsg");
		onGetDamageMsg = configmanager.getStringList(type, "damagemsg");
		onWarnItemMsg = configmanager.getStringList(type, "warnmsg");
		onBreakItemMsg = configmanager.getStringList(type, "destroymsg");
		onPlayerQuitMsg = configmanager.getStringList(type, "quitmsg");

		debug.info(DebugEnum.GROWTHTOOL, this.getClass().getSimpleName() + " Loaded.");
	}

	/**
	 * アイテムLv1で生成し配布する。
	 *
	 * @param player
	 * @return
	 */
	public boolean giveDefault(Player player) {
		State currentstate = status.get(0);
		Material material = getCurrentBase(1);
		ItemStack tool = new ItemStack(material);
		ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);

		// LOREの生成
		List<String> lore = new ArrayList<String>();
		lore.add("");
		// 固有部分
		for (String s : identLore) {
			lore.add(IDENTHEAD + s);
		}
		lore.add("");
		// アイテムレベル
		lore.add(ILHEAD + "1");
		// カスタム1
		for (String s : currentstate.custom1) {
			lore.add(CUSTOM1HEAD + s);
		}
		// カスタム2
		for (String s : currentstate.custom2) {
			lore.add(CUSTOM2HEAD + s);
		}
		lore.add("");
		// 所有者
		lore.add(OWNERHEAD + player.getDisplayName().toLowerCase());
		meta.setLore(lore);

		// 名前を設定
		String name = NAMEHEAD + this.getClass().getSimpleName().toUpperCase();
		meta.setDisplayName(name);
		// エンチャントを設定
		for (Map.Entry<Enchantment, Integer> ench : defench.entrySet()) {
			meta.addEnchant(ench.getKey(), ench.getValue().intValue(), true);
		}
		// 耐久無限を設定
		meta.spigot().setUnbreakable((unbreakable == 1));
		// フラグ設定
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		// meta反映
		tool.setItemMeta(meta);
		// 配布
		Util.giveItem(player, tool, false);
		return true;
	}

	/**
	 * プレイヤーログアウト時の出力候補メッセージ選定。 装備中かつメッセージが設定されている場合は1つ選択して返却。該当しない場合はnullを返却する。
	 *
	 * @param player
	 * @return
	 */
	public String getPlayerQuitMsg(Player player) {
		// 装備中かつメッセージが登録されている場合
		if (isEquip(player) && onPlayerQuitMsg.size() > 0) {
			// ランダムに1つ選択して返却する
			return onPlayerQuitMsg.get(new Random().nextInt(onPlayerQuitMsg.size()));
		}
		return null;
	}

	/**
	 * webからのTipsリスト読み込み処理
	 */
	private void loadTips(String wikiurl) {
		try {
			wikiTipsMsg = new ArrayList<String>();
			// HTTP通信でJSONデータを取得
			URL url = new URL(wikiurl);
			URLConnection urlCon = url.openConnection();
			// 403回避のためユーザーエージェントを登録
			urlCon.setRequestProperty("User-Agent", "GrowthTool");
			InputStream in = urlCon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "EUC-JP"));
			String line;
			// Tips先頭まで読み込み
			while ((line = reader.readLine()) != null) {
				if (line.contains("<ul id=\"content_block_2\" class=\"list-1\">")) {
					break;
				}
			}
			// Tipsを読み込み
			while ((line = reader.readLine()) != null) {
				if (line.contains("</ul>")) {
					break;
				} else {
					wikiTipsMsg.add(line.replace("<li> ", "").replace("</li>", ""));
				}
			}
			reader.close();
			in.close();
		} catch (Exception e) {
			debug.warning(DebugEnum.GROWTHTOOL, "tips読み込み失敗: " + wikiurl);
			wikiTipsMsg.clear();
		}
	}

	/**
	 * 現在のアイテムレベルに適したMaterialを返却する。存在しない場合はGOLD_HELMETを返却する。
	 *
	 * @param itemLv
	 * @return
	 */
	private Material getCurrentBase(int itemLv) {
		List<Integer> threshLv = new ArrayList<Integer>(bases.keySet());
		Collections.sort(threshLv);
		Material ret = Material.GOLD_HELMET;
		for (Integer i : threshLv) {
			if (i <= itemLv) {
				ret = bases.get(i);
			}
		}
		return ret;
	}

	// Lv毎の設定情報クラス
	private class State {
		private int exp;
		private List<String> custom1;
		private List<String> custom2;

		private State(Integer exp, List<String> custom1, List<String> custom2) {
			this.exp = exp;
			this.custom1 = custom1;
			this.custom2 = custom2;
		}
	}

	// エンチャント毎の設定情報クラス
	private class Enchant {
		private Enchantment type;
		private int maxLv;
		private int premise;

		private Enchant(Enchantment type, int maxLv, int premise) {
			this.type = type;
			this.maxLv = maxLv;
			this.premise = premise;
		}
	}
}
