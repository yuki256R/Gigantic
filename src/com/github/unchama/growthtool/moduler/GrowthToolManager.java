package com.github.unchama.growthtool.moduler;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.growthtool.moduler.message.GrwCustomMessage;
import com.github.unchama.growthtool.moduler.message.GrwMessage;
import com.github.unchama.growthtool.moduler.status.GrwEnchants;
import com.github.unchama.growthtool.moduler.status.GrwStatus;
import com.github.unchama.growthtool.moduler.tool.GrwDefine;
import com.github.unchama.growthtool.moduler.tool.GrwTool;
import com.github.unchama.util.Util;
import com.github.unchama.yml.GrowthToolDataManager;

public abstract class GrowthToolManager {
	// 成長ツール名称
	protected String name;
	// ドロップバランス
	protected int dropBalanceRate;
	// 識別用固有メッセージ
	protected List<String> identLore;
	// ステータス一覧
	protected GrwStatus status;
	// エンチャント一覧
	protected GrwEnchants enchant;
	// カスタムメッセージ1
	protected GrwCustomMessage customMsg;
	// 整地時のメッセージリスト
	protected GrwMessage onBlockBreakMsg;
	// 命名時のメッセージリスト
	protected GrwMessage onRenameItemMsg;
	// 討伐時のメッセージリスト
	protected GrwMessage onMonsterKillMsg;
	// 被ダメージ時のメッセージリスト
	protected GrwMessage onGetDamageMsg;
	// 破損警告時のメッセージリスト
	protected GrwMessage onWarnItemMsg;
	// 破損時のメッセージリスト
	protected GrwMessage onBreakItemMsg;
	// プレイヤーログアウト時のメッセージリスト
	protected GrwMessage onPlayerQuitMsg;

	/**
	 * 装備中の一致する成長ツールを取得する。装備中ではない場合はnullが返却される。
	 *
	 * @param player
	 * @return
	 */
	protected abstract GrwTool getTool(Player player);

	// getToolでitemを取得したらこれを呼び出す
	// 所有者一致確認は行わない
	protected GrwTool getTool(ItemStack item) {
		if (!item.hasItemMeta()) {
			return null;
		}
		ItemMeta itemmeta = item.getItemMeta();
		if (!itemmeta.hasLore()) {
			return null;
		}
		List<String> itemlore = itemmeta.getLore();
		// identを含むか判定
		for (String identLine : identLore) {
			if (!itemlore.contains(GrwDefine.IDENTHEAD + identLine)) {
				return null;
			}
		}
		return new GrwTool(item, identLore, status, enchant);
	}

	/**
	 * 装備中の一致する成長ツールを置換する。
	 *
	 * @param player
	 * @return
	 */
	protected abstract void setTool(Player player, GrwTool newtool);

	public GrowthToolManager(GrowthToolType type) {
		name = type.name();
		// ymlからの読み込み
		GrowthToolDataManager configmanager = Gigantic.yml.getManager(GrowthToolDataManager.class);
		dropBalanceRate = configmanager.getDropBalance(type);
		identLore = configmanager.getIdent(type);
		// Status
		final Map<Integer, Material> base = configmanager.getBaseItem(type);
		final List<Integer> nextExp = configmanager.getExp(type);
		final List<List<String>> custom1 = configmanager.getStringListList(type, "custom1");
		final List<List<String>> custom2 = configmanager.getStringListList(type, "custom2");
		final Integer unbreakable = configmanager.getUnbreakableLv(type);
		status = new GrwStatus(base, nextExp, custom1, custom2, unbreakable);
		// Enchants
		enchant = configmanager.getEnchantments(type);
		// メッセージリスト
		customMsg = new GrwCustomMessage(name, configmanager.getStringList(type, "tipsmsg"), custom1);
		onBlockBreakMsg = new GrwMessage(name, configmanager.getStringList(type, "breakmsg"));
		onRenameItemMsg = new GrwMessage(name, configmanager.getStringList(type, "renamemsg"));
		onMonsterKillMsg = new GrwMessage(name, configmanager.getStringList(type, "killmsg"));
		onGetDamageMsg = new GrwMessage(name, configmanager.getStringList(type, "damagemsg"));
		onWarnItemMsg = new GrwMessage(name, configmanager.getStringList(type, "warnmsg"));
		onBreakItemMsg = new GrwMessage(name, configmanager.getStringList(type, "destroymsg"));
		onPlayerQuitMsg = new GrwMessage(name, configmanager.getStringList(type, "quitmsg"));
	}

	/**
	 * アイテムLv1で生成し配布する。
	 *
	 * @param player
	 * @return
	 */
	public boolean giveDefault(Player player) {
		Util.giveItem(player, (ItemStack) new GrwTool(player, name, identLore, status, enchant), false);
		return true;
	}

	/**
	 * アイテムの表示名を変更する。
	 *
	 * @param player
	 * @param name
	 * @return
	 */
	public boolean rename(Player player, String name) {
		GrwTool tool = getTool(player);
		if (tool != null) {
			String trim = trimInputText(name);
			if (trim.isEmpty()) {
				tool.setName(this.name);
				player.sendMessage(this.name + "の名前を初期化しました。");
			} else {
				tool.setName(trim);
				player.sendMessage(this.name + "の名前を" + trim + "に変更しました。");
			}
			setTool(player, tool);
			GrowthTool.talk(player, onRenameItemMsg.talk(tool, player, null), true);
			return true;
		}
		player.sendMessage(this.name + "を装備していません。");
		return false;
	}

	/**
	 * ドロップバランスを取得する。
	 *
	 * @return
	 */
	public int getDropBalance() {
		return dropBalanceRate;
	}

	/**
	 * msg内の<name>に対応する呼び名を設定する。
	 *
	 * @param player
	 * @param called
	 * @return
	 */
	public boolean setPlayerCalled(Player player, String called) {
		GrwTool tool = getTool(player);
		if (tool != null) {
			String trim = trimInputText(called);
			if (trim.isEmpty()) {
				tool.setCall("");
				player.sendMessage(name + "からの呼び名を初期化しました。");
			} else {
				tool.setCall(trim);
				player.sendMessage(name + "からの呼び名を" + trim + "に変更しました。");
			}
			setTool(player, tool);
			return true;
		}
		player.sendMessage(name + "からの呼び名を変更出来ませんでした。");
		return false;
	}

	/**
	 * 入力文字列から除外文字を除去する。Color Codeを除去し、10文字を上限として返却する。
	 *
	 * @param text
	 * @return
	 */
	private String trimInputText(String text) {
		ChatColor.stripColor(text);
		text = text.replace("<", "").replace(">", "");
		if (text.length() > 10) {
			text = text.substring(0, 10);
		}
		return text;
	}

	// forceTalkはこの中で行った上でnullを返却する
	public String getMessage(Event event, Player player) {
		String ret = null;
		GrwTool tool;

		// PlayerItemBreakEventはアイテムを装備していない為先に処理する
		if (event instanceof PlayerItemBreakEvent) {
			// 破損ツールをGrwToolに変換出来たら、自身が壊れたと判定できる
			tool = getTool(((PlayerItemBreakEvent) event).getBrokenItem());
			if (tool != null) {
				// 破損時メッセージ（強制）
				GrowthTool.talk(player, onBreakItemMsg.talk(tool, player, null), true);
				player.sendMessage(tool.getName() + "が旅立ちました。");
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 1f, 0.1f);
			}
		} else if ((tool = getTool(player)) == null) {
		} else if (event == null) {
			ret = customMsg.talk(tool, player, null);
		} else if (event instanceof EntityDeathEvent) {
			ret = onMonsterKillMsg.talk(tool, player, ((EntityDeathEvent) event).getEntity().getKiller());
		} else if (event instanceof PlayerQuitEvent) {
			ret = onPlayerQuitMsg.talk(getTool(player), player, null);
		} else if (event instanceof BlockBreakEvent) {
			if (tool.addExp(player)) {
				setTool(player, tool);
			}
			ret = onBlockBreakMsg.talk(tool, player, null);
		} else if (event instanceof EntityDamageByEntityEvent) {
			if (tool.isWarn()) {
				// 破損警告時メッセージ（強制）
				GrowthTool.talk(player, onWarnItemMsg.talk(tool, player, ((EntityDamageByEntityEvent) event).getDamager()), true);
			} else {
				ret = onGetDamageMsg.talk(tool, player, ((EntityDamageByEntityEvent) event).getDamager());
			}
		}
		return ret;
	}
}
