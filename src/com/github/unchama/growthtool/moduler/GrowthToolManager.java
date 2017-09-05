package com.github.unchama.growthtool.moduler;

import java.util.List;
import java.util.Map;

import com.github.unchama.event.GiganticInteractEvent;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.event.SecondEvent;
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
import org.bukkit.projectiles.ProjectileSource;

/**
 * Growth Tool内の基本挙動を示す抽象クラス。<br />
 * このクラスのインスタンスは生成されず、継承された各Growth Toolごとのインスタンスを利用する。<br />
 * 共通処理部分を定義する。継承クラスは必要に応じ、それぞれの処理をOverrideして独自実装して良い。<br />
 * 装備部位ごとに継承クラスを生成することを想定しており、abstractで定義されているメソッドを実装する必要がある。<br />
 *
 * @author CrossHearts
 */
public abstract class GrowthToolManager {
	/** Growth Toolの名称 */
	protected String name;
	/** ドロップバランス */
	protected int dropBalanceRate;
	/** 識別用固有メッセージ */
	protected List<String> identLore;
	/** ステータス一覧 */
	protected GrwStatus status;
	/** エンチャント一覧 */
	protected GrwEnchants enchant;
	/** カスタムメッセージ1 */
	protected GrwCustomMessage customMsg;
	/** 整地時のメッセージリスト */
	protected GrwMessage onBlockBreakMsg;
	/** 命名時のメッセージリスト */
	protected GrwMessage onRenameItemMsg;
	/** 討伐時のメッセージリスト */
	protected GrwMessage onMonsterKillMsg;
	/** 被ダメージ時のメッセージリスト */
	protected GrwMessage onGetDamageMsg;
	/** 破損警告時のメッセージリスト */
	protected GrwMessage onWarnItemMsg;
	/** 破損時のメッセージリスト */
	protected GrwMessage onBreakItemMsg;
	/** プレイヤーログアウト時のメッセージリスト */
	protected GrwMessage onPlayerQuitMsg;

	/**
	 * 装備中の該当Growth Tool取得処理。<br />
	 * 指定プレイヤーに対し、該当Growth Toolが装備されていると想定される箇所から装備を取得し、対応するGrowth Toolの場合は返却する。<br />
	 * 対応するGrowth Toolを装備していない場合はnullを返却する。<br />
	 *
	 * @param player 装備を取得するプレイヤー
	 * @return 装備中のGrowth Tool (null: 対応tool未装備)
	 */
	protected abstract GrwTool getTool(Player player);

	/**
	 * ヘルメット装備処理。<br />
	 * 指定プレイヤーに対し、該当Growth Toolが装備されると想定される箇所に指定のGrowth Toolを装備する。<br />
	 *
	 * @param player ヘルメットを装備するプレイヤー
	 * @param grwtool 装備するヘルメット
	 */
	protected abstract void setTool(Player player, GrwTool grwtool);

	/**
	 * コンストラクタ。全装備共通となる処理を行う。<br />
	 * 主にgrowthtool.ymlからの読み込みと各プロパティの初期化を行う。<br />
	 *
	 * @param type 対応するGrowthToolTypeのenum Key
	 */
	public GrowthToolManager(GrowthToolType type) {
		name = type.toString();
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
	 * 渡されたItemStackがこのクラスで定義されているGrowth Toolかどうかを判定する。<br />
	 * 継承クラスにより取得した、装備中のItemStackを引数として呼び出されることを想定している。<br />
	 * 判定にはgrowthtool.yml / identの一致、及びToolの所有者名またはUUIDと一致する必要がある。<br />
	 * 一致した場合は該当するItemStackをGrwToolインスタンスに変換して返却、一致しない場合はnullを返却する。<br />
	 *
	 * @param player itemを所有しているプレイヤー
	 * @param item Growth Toolかどうかを判定するItemStack
	 * @return GrwToolインスタンス (null: 対応toolまたは所有者の不一致)
	 */
	protected GrwTool getTool(Player player, ItemStack item) {
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
		// 所有者の判定
		if (!GrwTool.isOwner(item, player)) {
			return null;
		}
		return new GrwTool(item, identLore, status, enchant);
	}

	/**
	 * このクラスのGrowth Toolをアイテムレベル1の状態で生成し、対象プレイヤーに配布する。<br />
	 * 整地により入手条件を満たす、またはdebug用getコマンドにより呼び出される。<br />
	 *
	 * @param player Growth Toolを配布するプレイヤー
	 * @param canRepair 金床使用可能か否か(true: 可能 / false: 不可能)
	 * @return (true: インベントリ収納成功 / false: インベントリフルによるアイテムドロップ)
	 */
	public boolean giveDefault(Player player, Boolean canRepair) {
		NBTItem growthTool = new NBTItem(new GrwTool(player, name, identLore, status, enchant));
		if (!canRepair) {
			//NBTを編集することで金床使用を阻止する。
			//RepairCostを適当に大きくしてあげればよい。
			growthTool.setInteger("RepairCost", 10000);
		}
		return !Util.giveItem(player, growthTool.getItem(), false);
	}

	/**
	 * このクラスのGrowth Toolをプレイヤーに配布。
	 * 基本的にはgiveDefaultと同じだが、装備として装着させた状態で配布する。
	 * 初参加時に呼び出されることを想定。
	 * @param player 与えるプレイヤー
	 * @param type 装備の位置
	 * @param canRepair 金床使用可能か否か(true: 可能 / false: 不可能)
	 */
	public void giveDefaultEquipment(Player player, EquipmentType type, Boolean canRepair) {
		NBTItem item =  new NBTItem(new GrwTool(player, name, identLore, status, enchant));
		if (!canRepair) {
			//NBTを編集することで金床使用を阻止する。
			//RepairCostを適当に大きくしてあげればよい。
			item.setInteger("RepairCost", 10000);
		}
		//初参加時、アイテムはないはずだが念のためチェックしてから配置
		switch (type) {
			case HELMET:
				if (player.getInventory().getHelmet() == null) {
					player.getInventory().setHelmet(item.getItem());
					break;
				} else sendWarn(player, canRepair);
				break;
			case CHESTPLATE:
				if (player.getInventory().getChestplate() == null) {
					player.getInventory().setChestplate(item.getItem());
					break;
				} else sendWarn(player, canRepair);
				break;
			case LEGGIGS:
				if (player.getInventory().getLeggings() == null) {
					player.getInventory().setLeggings(item.getItem());
					break;
				} else sendWarn(player, canRepair);
				break;
			case BOOTS:
				if (player.getInventory().getBoots() == null) {
					player.getInventory().setLeggings(item.getItem());
					break;
				} else sendWarn(player, canRepair);
				break;
		}
	}

	/**
	 * プレイヤーにアイテム欄が埋まっていることを警告し、直接インベントリにアイテムを追加します。
	 * 初期配布処理で使用されることを想定。
	 * @param player 警告を表示するプレイヤー
	 * @param canRepair アイテムが金床で修繕可能か否か(true: 可能 / false: 不可能)
	 */
	private void sendWarn(Player player, Boolean canRepair) {
		player.sendMessage(ChatColor.RED + "何らかの原因でスロットが埋まっているため、");
		player.sendMessage(ChatColor.RED + "Growth Toolをインベントリに直接追加しました。");
		this.giveDefault(player, canRepair);
	}

	/**
	 * 装備の場所を指定するためのenum
	 */
	public enum EquipmentType {
		HELMET,
		CHESTPLATE,
		LEGGIGS,
		BOOTS,
		;
	}

	/**
	 * Growth Toolの表示名を変更する。コマンドによる命名で呼び出される。<br />
	 * 名前は正規化の上で命名される。emptyを命名した場合は初期名称となる。<br />
	 *
	 * @param player Growth Toolの所有者
	 * @param name 新しい名前
	 * @return (true: 命名成功 / false: 命名失敗)
	 */
	public boolean rename(Player player, String name) {
		GrwTool tool = getTool(player);
		if (tool == null) {
			player.sendMessage(this.name + "を装備していません。");
			return false;
		}
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

	/**
	 * 愛称設定処理。Growth Toolからプレイヤーに対しての呼び名を設定する。<br />
	 * 設定された愛称は、yml内の<name>部分に置き換えられる。<br />
	 * 愛称は正規化の上で設定される。emptyを設定した場合はMCIDとなる。<br />
	 *
	 * @param player Growth Toolの所有者
	 * @param called プレイヤーに対する愛称
	 * @return (true: 設定成功 / false: 設定失敗)
	 */
	public boolean setPlayerCalled(Player player, String called) {
		GrwTool tool = getTool(player);
		if (tool == null) {
			player.sendMessage(this.name + "を装備していません。");
			return false;
		}
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

	/**
	 * 文字列正規化処理。命名及び愛称設定で使用される。<br />
	 * 入力文字列から、Color Code及びタグシーケンスの除去、文字数制限の上で返却する。<br />
	 *
	 * @param text 正規化対象文字列
	 * @return 正規化済み文字列
	 */
	private String trimInputText(String text) {
		ChatColor.stripColor(text);
		text = text.replace("<", "").replace(">", "");
		if (text.length() > 10) {
			text = text.substring(0, 10);
		}
		return text;
	}

	/**
	 * ドロップバランス取得処理。100を基準として、このクラスのGrowth Toolのドロップレートを調整する為の値。<br />
	 * ドロップ判定時に各Growth Toolからドロップバランスを取得し、ドロップ対象を決定するために利用される。<br />
	 *
	 * @return このクラスのGrowth Toolに設定されているドロップバランス
	 */
	public int getDropBalance() {
		return dropBalanceRate;
	}

	/**
	 * イベントメッセージ取得処理。メッセージを出力すべき各イベントの際に呼び出され、対応するメッセージを返却する。<br />
	 * イベントによるが、多くの場合は統括のGrowthToolクラスで全てのGrowth Toolから1文ずつ取得の上で選択される。<br />
	 * 但し一部のイベントには本メソッド内から強制出力するメッセージが存在する。<br />
	 *
	 * @param event メッセージ出力の要因となるBukkitイベント（Eventクラス）の継承クラス
	 * @param player イベントの出力対象となるプレイヤー
	 * @return 出力候補メッセージ (null: 候補なし)
	 */
	public String getMessage(Event event, Player player) {
		// forceTalkはこのメソッド内で呼び出しの上で、nullを返却すること
		String ret = null;
		GrwTool tool;

		// PlayerItemBreakEventはアイテムを装備していない為先に処理する
		if (event instanceof PlayerItemBreakEvent) {
			// 破損ツールをGrwToolに変換出来たら、自身が壊れたと判定できる
			tool = getTool(((PlayerItemBreakEvent) event).getPlayer(), ((PlayerItemBreakEvent) event).getBrokenItem());
			if (tool != null) {
				// 破損時メッセージ（強制）
				GrowthTool.talk(player, onBreakItemMsg.talk(tool, player, null), true);
				player.sendMessage(tool.getName() + "が旅立ちました。");
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 1f, 0.1f);
			}
		} else if ((tool = getTool(player)) == null) {
			// 装備不一致
		} else if (event instanceof SecondEvent) {
			ret = customMsg.talk(tool, player, null);
		} else if (event instanceof EntityDeathEvent) {
			ret = onMonsterKillMsg.talk(tool, player, ((EntityDeathEvent) event).getEntity());
		} else if (event instanceof PlayerQuitEvent) {
			ret = onPlayerQuitMsg.talk(getTool(player), player, null);
		} else if (event instanceof BlockBreakEvent) {
			if (tool.addExp(player)) {
				setTool(player, tool);
			}
			ret = onBlockBreakMsg.talk(tool, player, null);
		} else if (event instanceof GiganticInteractEvent) {
			if (tool.addExp(player)) {
				setTool(player, tool);
			}
			ret = onBlockBreakMsg.talk(tool, player, null);
		} else if (event instanceof EntityDamageByEntityEvent) {
			if (tool.isWarn()) {
				// 破損警告時メッセージ（強制）
				GrowthTool.talk(player, onWarnItemMsg.talk(tool, player, ((EntityDamageByEntityEvent) event).getDamager()), true);
			} else {
				/*
				 * EntityDamageByEntityEvent#getDamager()だとスケルトンなどで攻撃された際、Arrow(矢)が出力されてしまうので
				 * 以下の手順を踏む必要がある。
				 */
				//1.EntityDamageByEntitiyEventにキャストする(instaceofで安全性は確保されている。)
				EntityDamageByEntityEvent cause = (EntityDamageByEntityEvent) event;
				//2.ダメージを与えたエンティティを取得
				Entity entity = cause.getDamager();
				//3.矢・ポーションについてはモンスターを取得。
				if (entity instanceof Arrow) {
					Arrow arrow = (Arrow) entity;
					LivingEntity shooter = (LivingEntity) arrow.getShooter();
					ret = onGetDamageMsg.talk(tool, player, shooter);
				} else if (entity instanceof Projectile) {
					ProjectileSource shooter = ((Projectile) entity).getShooter();
					ret = onGetDamageMsg.talk(tool, player, (Entity) shooter);
				} else {
					ret = onGetDamageMsg.talk(tool, player, entity);
				}
			}
		}
		return ret;
	}
}
