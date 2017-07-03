package com.github.unchama.growthtool.moduler.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.moduler.status.GrwEnchants;
import com.github.unchama.growthtool.moduler.status.GrwStateData;
import com.github.unchama.growthtool.moduler.status.GrwStatus;

import de.tr7zw.itemnbtapi.NBTItem;

/**
 * Growth Tool格納用ItemStack拡張クラス。個々のGrowth Toolインスタンスで利用する。<br />
 * Named Binary Tagの入出力や、名前 / Lore、エンチャント等のGrowth Tool固有設定を個別に所有する。<br />
 * このクラスのメソッドを介して設定した情報は、即座にItemStackに反映される。<br />
 * Read目的でItemStackの継承メソッドを公開しているが、Write目的による直接ItemStackの操作は保証しない。<br />
 *
 * @author CrossHearts
 */
public final class GrwTool extends ItemStack {
	// 表示名
	private String name;
	// Lore内の固有識別詞
	private List<String> identify;
	// アイテムレベル（1スタート）
	private int itemlv = 1;
	// レベルアップ時のエンチャントテーブル
	private GrwEnchants enchantTable;
	// 所有者のMCID
	private String owner;
	// ステータス
	private GrwStatus status;
	// プレイヤーに対する呼び名
	private String playerName;
	// 所有者のUUID
	private UUID playerUuid;
	// 現在経験値
	private int currentExp;

	/**
	 * 新規Growth Tool生成用コンストラクタ。<br />
	 * ItemStackを生成し、Growth Toolそれぞれの初期値を設定し保存する。<br />
	 * 引数の情報を持つGrowthToolManager継承クラスから、デフォルトの初期アイテム生成用としての呼び出しを想定している。<br />
	 *
	 * @param player 所有者プレイヤー
	 * @param itemname Growth Toolの初期名称
	 * @param identLore Growth Toolのident
	 * @param status Growth Toolのステータス情報
	 * @param enchants Growth Toolのエンチャント情報
	 */
	public GrwTool(Player player, String itemname, List<String> identLore, GrwStatus status, GrwEnchants enchants) {
		super();
		setAmount(1);
		// パラメータの読み込み
		if (player == null) {
			GrowthTool.GrwDebugWarning("playerがnullです。");
		}
		if (itemname.isEmpty()) {
			GrowthTool.GrwDebugWarning("itemnameがnullのためemptyとして扱います。");
			itemname = "";
		}
		if (status == null) {
			GrowthTool.GrwDebugWarning("statusがnullのためemptyとして扱います。");
			status = new GrwStatus(null, null, null, null, 0);
		}
		if (identLore == null) {
			GrowthTool.GrwDebugWarning("identLoreがnullのため[未設定]として扱います。");
			identLore = Arrays.asList("[未設定]");
		}
		if (enchants == null) {
			GrowthTool.GrwDebugWarning("enchantsがnullのため無しとして扱います。");
			enchants = new GrwEnchants(new LinkedHashMap<Enchantment, Integer>());
		}

		// パラメータの設定
		name = itemname;
		identify = identLore;
		itemlv = 1;
		enchantTable = enchants;
		owner = player.getDisplayName();
		this.status = status;
		playerName = "";
		playerUuid = player.getUniqueId();
		currentExp = 0;
		grwBuild();
	}

	/**
	 * 既存Growth Tool読み込み用コンストラクタ。<br />
	 * 引数のItemStackから内容を解析し、各データを保持する。<br />
	 * ItemStackはGrowth Toolであることを前提とし、その他のItemStackを渡した場合の動作は保証しない。<br />
	 * 引数の情報を持つGrowthToolManager継承クラスから、既存のアイテム読み込み用としての呼び出しを想定している。<br />
	 *
	 * @param tool 読み込み対象のItemStack型Growth Tool
	 * @param identLore Growth Toolのident
	 * @param status Growth Toolのステータス情報
	 * @param enchants Growth Toolのエンチャント情報
	 */
	public GrwTool(ItemStack tool, List<String> identLore, GrwStatus status, GrwEnchants enchants) {
		super(tool);
		try {
			ItemMeta itemmeta = getItemMeta();
			List<String> itemlore = itemmeta.getLore();
			if (itemmeta.hasDisplayName()) {
				name = itemmeta.getDisplayName();
			} else {
				GrowthTool.GrwDebugWarning("Growth ToolにDisplayNameが存在しないためMaterial名を設定します。");
				name = tool.getType().toString();
			}
			name = itemmeta.hasDisplayName() ? itemmeta.getDisplayName() : "";
			identify = Collections.unmodifiableList(identLore);
			itemlv = getNBT(GrwNbti.ItemLv, Integer.class);
			enchantTable = enchants;
			owner = getNBT(GrwNbti.PlayerMcid, String.class);
			this.status = status;
			playerName = getNBT(GrwNbti.PlayerName, String.class);
			playerUuid = getNBT(GrwNbti.PlayerUuid, UUID.class);
			currentExp = getNBT(GrwNbti.CurrentExp, Integer.class);
			// [移行用] アイテムレベルがNBTに登録されていない場合は取得する
			if (itemlv < 1) {
				itemlv = 1;
				for (String s : itemlore) {
					if (s.startsWith(GrwDefine.ILHEAD)) {
						try {
							itemlv = Integer.parseUnsignedInt(s.replace(GrwDefine.ILHEAD, ""));
							if (itemlv < 1) {
								throw new NumberFormatException("アイテムレベルが正の実数ではありません。");
							}
						} catch (NumberFormatException e) {
							GrowthTool.GrwDebugWarning("itemlvが不正な値のため1として扱います。");
							itemlv = 1;
						}
						break;
					}
				}
			}
			// [移行用] 所有者がNBTに登録されていない場合は取得する
			if (owner.isEmpty()) {
				for (String s : itemlore) {
					if (s.startsWith(GrwDefine.OWNERHEAD)) {
						owner = s.replace(GrwDefine.OWNERHEAD, "");
						break;
					}
				}
				if (owner.isEmpty()) {
					GrowthTool.GrwDebugWarning("ownerがemptyのためunchamaとして扱います。");
					owner = "unchama";
				}
			}
		} catch (NullPointerException e) {
			GrowthTool.GrwDebugWarning("ItemStackコンストラクタにGrowth Tool以外が渡されました。");
		}
	}

	/**
	 * GrwTool用ItemStack生成処理。<br />
	 * このクラスで管理している情報を、継承したItemStackに対し上書きする。<br />
	 * プロパティに対しWriteを行うメソッドでは最後に必ず呼び出すこと。<br />
	 */
	private void grwBuild() {
		GrwStateData state = status.get(itemlv - 1);
		setType(state.getMaterial());
		ItemMeta itemmeta = hasItemMeta() ? getItemMeta() : Bukkit.getItemFactory().getItemMeta(state.getMaterial());
		if (!name.isEmpty()) {
			itemmeta.setDisplayName(name);
		}
		itemmeta.spigot().setUnbreakable(state.getUnbreakable());

		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		for (String s : identify) {
			itemlore.add(GrwDefine.IDENTHEAD + s);
		}
		itemlore.add("");
		itemlore.add(GrwDefine.ILHEAD + String.valueOf(itemlv));
		int nextExp = state.getNextExp();
		if (nextExp != 0) {
			itemlore.add(GrwDefine.EXPHEAD + String.valueOf(currentExp) + " / " + String.valueOf(nextExp));
		}
		for (String s : state.getCustom1()) {
			itemlore.add(GrwDefine.CUSTOM1HEAD + s);
		}
		for (String s : state.getCustom2()) {
			itemlore.add(GrwDefine.CUSTOM2HEAD + s);
		}
		if (!owner.isEmpty()) {
			itemlore.add("");
			itemlore.add(GrwDefine.OWNERHEAD + owner);
		}
		itemmeta.setLore(itemlore);
		setItemMeta(itemmeta);
		if (itemlv == 1) {
			addUnsafeEnchantments(enchantTable.getDefaultEnchant());
		}
		setNBT(GrwNbti.PlayerName, playerName);
		setNBT(GrwNbti.PlayerUuid, playerUuid);
		setNBT(GrwNbti.PlayerMcid, owner);
		setNBT(GrwNbti.CurrentExp, currentExp);
		setNBT(GrwNbti.ItemLv, itemlv);
	}

	/**
	 * NBT設定メソッド。tagに対応するNBTのデータを書き込む。<br />
	 * grwBuild内での呼び出しのみを想定しているため、他メソッドから書き換えを行う場合一度プロパティ変更の経由を推奨する。<br />
	 *
	 * @param tag NBTに対応するenum
	 * @param obj 設定する値
	 */
	private void setNBT(GrwNbti tag, Object obj) {
		NBTItem nbti = new NBTItem(this);
		nbti.setObject(tag.toString(), obj);
		setItemMeta(nbti.getItem().getItemMeta());
	}

	/**
	 * NBT取得メソッド。tagに対応するNBTのデータを読み込む。<br />
	 * コンストラクタでの呼び出しを想定しており、その他の読み込みはプロパティのreadによる実現を推奨する。<br />
	 *
	 * @param tag NBTに対応するenum
	 * @param type 戻り値の型のクラス
	 * @return 取得値
	 */
	private <T> T getNBT(GrwNbti tag, Class<T> type) {
		NBTItem nbti = new NBTItem(this);
		return nbti.getObject(tag.toString(), type);
	}

	/**
	 * 経験値加算処理。経験値の加算、及びレベルアップの判定を行う。<br />
	 * 経験値情報はNBTとLoreに書き込まれるため、アイテムレベル最大時を除きアイテムは更新される。<br />
	 * 戻り値がtrueの場合は装備の上書きとしてsetToolを実施すること。<br />
	 *
	 * @param player 経験値を取得したプレイヤー
	 * @return (true: Growth Toolに変更があり、setToolが必要な場合 / false: 変更無し)
	 */
	public boolean addExp(Player player) {
		if (itemlv >= status.size()) {
			return false;
		}
		currentExp++;
		if (currentExp >= status.get(itemlv - 1).getNextExp()) {
			// levelup
			itemlv++;
			Enchantment uped = enchantTable.addEnchant(this);
			currentExp = 0;
			player.sendMessage(name + "のアイテムレベルが" + Integer.toString(itemlv) + "に上がりました。");
			player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1f, 0.1f);
			if (uped != null) {
				player.sendMessage(uped.toString() + "のエンチャントレベルが" + Integer.toString(getEnchantmentLevel(uped)) + "に上がりました。");
			} else {
				player.sendMessage("エンチャントレベルは既に最大値です。");
			}
		}
		grwBuild();
		return true;
	}

	/**
	 * 名前用セッター。Growth Toolの命名処理で呼び出される。<br />
	 *
	 * @param name 設定する名前
	 */
	public void setName(String name) {
		this.name = name;
		grwBuild();
	}

	/**
	 * 名前用ゲッター。Growth Toolの名前を返却する。<br />
	 * Growth Toolはデフォルト名または命名によりDisplayNameを持っている前提とする。<br />
	 *
	 * @return 命名されている名前 (empty: 想定外)
	 */
	public String getName() {
		return name;
	}

	/**
	 * 愛称用セッター。愛称設定処理で呼び出される。Growth Toolからプレイヤーへの呼び名を設定する。<br />
	 *
	 * @param name 設定する愛称。
	 */
	public void setCall(String name) {
		this.playerName = name;
		grwBuild();
	}

	/**
	 * 愛称用ゲッター。設定されている愛称を返却する。<br />
	 * 愛称が設定されていない場合はemptyを返却する。<br />
	 *
	 * @return 設定されている愛称 (empty: 未設定)
	 */
	public String getCall() {
		return playerName;
	}

	/**
	 * アイテムレベル用ゲッター。このツールのアイテムレベルを返却する。<br />
	 * アイテムレベルは1スタートで、1以上最大レベル以下であることが保証される。<br />
	 *
	 * @return アイテムレベル (1～最大レベル)
	 */
	public int getItemLv() {
		return itemlv;
	}

	/**
	 * 耐久値警告判定。現在の耐久値が閾値を切っているかどうかを判定する。<br />
	 * 返却値がtrueの場合は警告メッセージの出力を想定している。<br />
	 *
	 * @return 警告判定 (true: 警告状態 / false: 非警告状態)
	 */
	public boolean isWarn() {
		return getType().getMaxDurability() - getDurability() < 10;
	}

	/**
	 * 所有者判定用staticメソッド。引数のGrowth Toolが引数のplayerの持ち物かどうかを判定する。<br />
	 * 外部からNBTを利用した判定を行うための処理として、本クラスにstaticで実装している。<br />
	 *
	 * @param item 判定対象のGrowth Tool
	 * @param player 持ち主のプレイヤー
	 * @return 所有者判定 (true: 所有者 / false: 非所有者)
	 */
	public static boolean isOwner(ItemStack item, Player player) {
		List<String> itemlore;
		try {
			itemlore = item.getItemMeta().getLore();
		} catch (NullPointerException e) {
			return false;
		}
		UUID uuid = new NBTItem(item).getObject(GrwNbti.PlayerUuid.toString(), UUID.class);

		// UUIDの一致
		if (uuid != null && uuid.equals(player.getUniqueId())) {
			return true;
		}
		// [移行用] ItemStackの所有者欄にtoLowerCaseが登録されている場合
		if (itemlore.contains(GrwDefine.OWNERHEAD + player.getDisplayName().toLowerCase())) {
			return true;
		}
		return false;
	}
}
