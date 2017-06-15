package com.github.unchama.growthtool.moduler.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
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
 * GrowthTool用ItemStack拡張クラス。個々のGrowthTool用に利用する。<br />
 * Named Binary Tagの入出力や、名前 / Lore、エンチャントの編集が可能。<br />
 */
public final class GrwTool extends ItemStack {
	// 表示名
	private String name;
	// Lore内の固有識別詞
	private List<String> identify;
	// アイテムレベル
	private int itemlv = 1;
	// 現在のエンチャント情報
	private Map<Enchantment, Integer> enchantments;
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
	 */
	public GrwTool(Player player, String itemname, List<String> identLore, GrwStatus status, GrwEnchants enchants) {
		super();
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
		enchantments = enchants.addDefaultEnchant();
		enchantTable = enchants;
		owner = player.getDisplayName();
		this.status = status;
		playerName = "";
		playerUuid = player.getUniqueId();
		currentExp = 0;
		grwBuild();
	}

	/**
	 * 読み込み用コンストラクタ。ItemStackを引数とする。ItemStackの内容を解析し、各データを保持する。<br />
	 * Growth ToolではないItemStackを渡された場合は保証しない。<br />
	 *
	 * @param Growth ToolのItemStack
	 */
	public GrwTool(ItemStack tool, List<String> identLore, GrwStatus status, GrwEnchants enchants) {
		super(tool);
		try {
			ItemMeta itemmeta = getItemMeta();
			List<String> itemlore = itemmeta.getLore();
			name = itemmeta.hasDisplayName() ? itemmeta.getDisplayName() : "";
			identify = Collections.unmodifiableList(identLore);
			itemlv = getNBT(GrwNbti.ItemLv, Integer.class);
			enchantments = getEnchantments();
			enchantTable = enchants;
			owner = getNBT(GrwNbti.PlayerMcid, String.class);
			this.status = status;
			playerName = getNBT(GrwNbti.PlayerName, String.class);
			playerUuid = getNBT(GrwNbti.PlayerUuid, UUID.class);
			currentExp = getNBT(GrwNbti.CurrentExp, Integer.class);
			// アイテムレベルがNBTに登録されていない場合は取得する
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
			// 所有者がNBTに登録されていない場合は取得する
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

	private void grwBuild() {
		GrwStateData state = status.get(itemlv - 1);
		setType(state.getMaterial());
		ItemMeta itemmeta = hasItemMeta() ? getItemMeta() : Bukkit.getItemFactory().getItemMeta(state.getMaterial());
		if (!name.isEmpty()) {
			itemmeta.setDisplayName(name);
		}

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
		addUnsafeEnchantments(enchantments);
		setNBT(GrwNbti.PlayerName, playerName);
		setNBT(GrwNbti.PlayerUuid, playerUuid);
		setNBT(GrwNbti.PlayerMcid, owner);
		setNBT(GrwNbti.CurrentExp, currentExp);
		setNBT(GrwNbti.ItemLv, itemlv);
	}

	/**
	 * NBT設定メソッド。tagに対応するNBTのデータを書き込む。<br />
	 *
	 * @param tag NBTに対応するenum
	 * @param obj 設定値
	 */
	private void setNBT(GrwNbti tag, Object obj) {
		NBTItem nbti = new NBTItem(this);
		nbti.setObject(tag.name(), obj);
		this.setItemMeta(nbti.getItem().getItemMeta());
	}

	/**
	 * NBT取得メソッド。tagに対応するNBTのデータを読み込む。<br />
	 *
	 * @param tag NBTに対応するenum
	 * @param type 戻り値の型のクラス
	 * @return 取得値
	 */
	private <T> T getNBT(GrwNbti tag, Class<T> type) {
		NBTItem nbti = new NBTItem(this);
		return nbti.getObject(tag.name(), type);
	}

	// アイテムが変更されたらtrue
	public boolean addExp(Player player) {
		if (itemlv < status.size()) {
			currentExp++;
			if (currentExp >= status.get(itemlv - 1).getNextExp()) {
				// levelup
				itemlv++;
				Enchantment uped = enchantTable.addEnchant(enchantments, itemlv);
				currentExp = 0;
				player.sendMessage(name + "のアイテムレベルが" + Integer.toString(itemlv) + "に上がりました。");
				if (uped != null) {
					player.sendMessage(uped.toString() + "のエンチャントレベルが" + Integer.toString(enchantments.get(uped)) + "に上がりました。");
				} else {
					player.sendMessage("エンチャントレベルは既に最大値です。");
				}
			}
			grwBuild();
			return true;
		}
		return false;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCall(String name) {
		this.playerName = name;
	}

	public String getCall() {
		return playerName;
	}

	public int getItemLv() {
		return itemlv;
	}

	public boolean isWarn() {
		return getType().getMaxDurability() - getDurability() < 10;
	}
}
