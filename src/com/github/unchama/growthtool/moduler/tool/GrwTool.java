package com.github.unchama.growthtool.moduler.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.moduler.status.GrwEnchants;
import com.github.unchama.growthtool.moduler.status.GrwStateData;
import com.github.unchama.growthtool.moduler.status.GrwStatus;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

import de.tr7zw.itemnbtapi.NBTItem;

/**
 * GrowthTool用ItemStack拡張クラス。個々のGrowthTool用に利用する。<br />
 * Named Binary Tagの入出力や、名前 / Lore、エンチャントの編集が可能。<br />
 */
public final class GrwTool extends ItemStack {
	// debug Instance
	private static final DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	// 表示名
	private String name;
	// Lore内の固有識別詞
	private List<String> identify;
	// アイテムレベル
	private int itemlv;
	// エンチャント情報
	private Map<Enchantment, Integer> enchantments;
	// 所有者のMCID
	private String owner;
	// ステータス
	private GrwStateData state;
	// プレイヤーに対する呼び名
	private String playerName;
	// 所有者のUUID
	private UUID playerUuid;
	// 現在経験値
	private int currentExp;
	// レベルアップまでの必要経験値
	private int nextExp;

	/**
	 * 新規Growth Tool生成用コンストラクタ。<br />
	 */
	public GrwTool(UUID playerUuid, String name, List<String> identify, String owner, GrwStatus status, GrwEnchants enchants) {
		super();
		// パラメータの読み込み
		this.itemlv = 1;
		if (status == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] statusがnullのためemptyとして扱います。");
			status = new GrwStatus(null, null, null, null, 0);
		} else {
			state = status.get(itemlv - 1);
		}
		if (name == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] nameが未設定のためemptyとして扱います。");
			name = "";
		}
		if (identify == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] identifyが未設定のため[未設定]として扱います。");
			identify = Arrays.asList("[未設定]");
		}
		this.identify = identify;
		if (owner == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] ownerがnullのためemptyとして扱います。");
			owner = "";
		}
		this.owner = owner;
		if (enchants == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] enchantsがnullのため無しとして扱います。");
			enchantments = new LinkedHashMap<Enchantment, Integer>();
		} else {
			enchantments = enchants.addDefaultEnchant();
		}
		playerName = "";
		this.playerUuid = playerUuid;
		currentExp = 0;
		nextExp = state.getNextExp();
		grwBuild();
	}

	/**
	 * 読み込み用コンストラクタ。ItemStackを引数とする。ItemStackの内容を解析し、各データを保持する。<br />
	 * Growth ToolではないItemStackを渡された場合は保証しない。<br />
	 *
	 * @param Growth ToolのItemStack
	 */
	public GrwTool(ItemStack tool) {
		super(tool);
		ItemMeta itemmeta;
		List<String> itemlore;
		if (hasItemMeta()) {
			itemmeta = getItemMeta();
		} else {
			itemmeta = Bukkit.getItemFactory().getItemMeta(tool.getType());
		}
		if (itemmeta.hasLore()) {
			itemlore = itemmeta.getLore();
		} else {
			itemlore = new ArrayList<String>();
		}
		if (itemmeta.hasDisplayName()) {
			name = itemmeta.getDisplayName();
		}
		identify = new ArrayList<String>();
		List<String> custom1 = new ArrayList<String>();
		List<String> custom2 = new ArrayList<String>();
		for (String s : itemlore) {
			if (s.startsWith(GrwDefine.IDENTHEAD)) {
				identify.add(s.replace(GrwDefine.IDENTHEAD, ""));
			} else if (s.startsWith(GrwDefine.ILHEAD)) {
				try {
					int itemlv = Integer.parseUnsignedInt(s.replace(GrwDefine.ILHEAD, ""));
					if (itemlv > 1) {
						this.itemlv = itemlv;
					}
				} catch (NumberFormatException e) {
					// 変換できないアイテムレベルが格納されている場合は初期値のままとする。
				}
			} else if (s.startsWith(GrwDefine.CUSTOM1HEAD)) {
				custom1.add(s.replace(GrwDefine.CUSTOM1HEAD, ""));
			} else if (s.startsWith(GrwDefine.CUSTOM2HEAD)) {
				custom2.add(s.replace(GrwDefine.CUSTOM2HEAD, ""));
			} else if (s.startsWith(GrwDefine.OWNERHEAD)) {
				owner = s.replace(GrwDefine.OWNERHEAD, "");
			}
		}
		if (identify.isEmpty()) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] identifyが未設定のため[未設定]として扱います。");
			identify = Arrays.asList("[未設定]");
		}
		enchantments = getEnchantments();
		state = new GrwStateData(getType(), getNBT(GrwNbti.NextExp, Integer.class), custom1, custom2, itemmeta.spigot().isUnbreakable());
	}

	private void grwBuild() {
		setType(state.getMaterial());
		ItemMeta itemmeta;
		if (hasItemMeta()) {
			itemmeta = getItemMeta();
		} else {
			itemmeta = Bukkit.getItemFactory().getItemMeta(state.getMaterial());
		}
		if (!name.isEmpty()) {
			itemmeta.setDisplayName(name);
		}

		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		for (String s : identify) {
			itemlore.add(GrwDefine.IDENTHEAD + s);
		}
		itemlore.add("");
		if (itemlv != 0) {
			itemlore.add(GrwDefine.ILHEAD + String.valueOf(itemlv));
			int nextExp = getNBT(GrwNbti.NextExp, Integer.class);
			int currentExp = getNBT(GrwNbti.CurrentExp, Integer.class);
			if (nextExp != 0) {
				itemlore.add(GrwDefine.EXPHEAD + String.valueOf(currentExp) + " / " + String.valueOf(nextExp));
			}
		}
		List<String> custom1 = state.getCustom1();
		if (custom1 == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] custom1がnullのためemptyとして扱います。");
			custom1 = GrwDefine.EMPTYSTRINGLIST;
		}
		for (String s : custom1) {
			itemlore.add(GrwDefine.CUSTOM1HEAD + s);
		}
		List<String> custom2 = state.getCustom2();
		if (custom2 == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] custom2がnullのためemptyとして扱います。");
			custom2 = GrwDefine.EMPTYSTRINGLIST;
		}
		for (String s : custom2) {
			itemlore.add(GrwDefine.CUSTOM2HEAD + s);
		}
		if (owner != "") {
			itemlore.add("");
			itemlore.add(GrwDefine.OWNERHEAD + owner);
		}
		itemmeta.setLore(itemlore);
		setItemMeta(itemmeta);
		addUnsafeEnchantments(enchantments);
		setNBT(GrwNbti.PlayerName, playerName);
		setNBT(GrwNbti.PlayerUuid, playerUuid.toString());
		setNBT(GrwNbti.CurrentExp, currentExp);
		setNBT(GrwNbti.NextExp, nextExp);
	}

	/**
	 * NBT設定メソッド。tagに対応するNBTのデータを書き込む。<br />
	 *
	 * @param tag NBTに対応するenum
	 * @param obj 設定値
	 */
	public void setNBT(GrwNbti tag, Object obj) {
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
	public <T> T getNBT(GrwNbti tag, Class<T> type) {
		NBTItem nbti = new NBTItem(this);
		return nbti.getObject(tag.name(), type);
	}

	/**
	 * 名称設定メソッド。<br />
	 *
	 * @param name 新しい名称
	public void setName(String name) {
		itemmeta.setDisplayName(GrwDefine.NAMEHEAD + name);
	}

	/**
	 * 名称取得メソッド。名前が設定されていない場合はemptyを返却する。<br />
	 *
	 * @return 設定されている名称
	public String getName() {
		if (!itemmeta.hasDisplayName()) {
			return "";
		}
		return itemmeta.getDisplayName().replace(GrwDefine.NAMEHEAD, "");
	}

	/**
	 * 耐久無限フラグ取得メソッド。<br />
	 *
	 * @return 耐久無限フラグ <true: 耐久無限 / false: 未設定>
	public boolean getUnbreakable() {
		return itemmeta.spigot().isUnbreakable();
	}

	/**
	 * 耐久無限フラグ設定メソッド<br />
	 *
	 * @param unbreakable 耐久無限フラグ <true: 耐久無限 / false: 未設定>
	public void setUnbreakable(boolean unbreakable) {
		itemmeta.spigot().setUnbreakable(unbreakable);
	}

	/**
	 * 固有識別詞設定メソッド。<br />
	 *
	 * @param identify このツールに対応する固有識別詞
	public void setIdentify(List<String> identify) {
		this.identify = identify;
	}

	public int getItemLv() {
		return itemlv;
	}

	public void setItemLv(int itemlv) {
		this.itemlv = itemlv;
	}

	public List<String> getCustom1() {
		return custom1;
	}

	public void setCustom1(List<String> custom1) {
		this.custom1 = custom1;
	}

	public List<String> getCustom2() {
		return custom2;
	}

	public void setCustom2(List<String> custom2) {
		this.custom2 = custom2;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void addUnsafeEnchantments(Map<Enchantment, Integer> enchantments) {
		addUnsafeEnchantments(enchantments);
	}

	public void addUnsafeEnchantment(Enchantment ench) {
		itemstack.addUnsafeEnchantment(ench, itemstack.getEnchantmentLevel(ench) + 1);
	}
*/
	public boolean addExp() {
		if()
		return false;
	}
/*
	public boolean addExp(int exp) {
		int newexp = getNBT(GrwNbti.CurrentExp, Integer.class) + exp;
		setNBT(GrwNbti.CurrentExp, newexp);
		int nextexp = getNBT(GrwNbti.NextExp, Integer.class);
		return nextexp != 0 && newexp >= nextexp;
	}

	public void update(Material baseitem, int nextExp, List<String> custom1, List<String> custom2, boolean unbreakable) {
		if (!itemstack.getType().equals(baseitem)) {
			itemstack.setType(baseitem);
		}
		setNBT(GrwNbti.NextExp, nextExp);
		setCustom1(custom1);
		setCustom2(custom2);
		setUnbreakable(unbreakable);
	}

	public boolean isWarn() {
		return itemstack.getType().getMaxDurability() - itemstack.getDurability() < 10;
	}
*/
}
