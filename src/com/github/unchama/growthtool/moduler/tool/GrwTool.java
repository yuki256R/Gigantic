package com.github.unchama.growthtool.moduler.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

import de.tr7zw.itemnbtapi.NBTItem;

/**
 * GrowthTool用ItemStackラッパークラス。個々のGrowthTool用に利用する。<br />
 * Named Binary Tagの入出力や、名前 / Lore、エンチャントの編集が可能。<br />
 */
public final class GrwTool {
	// debug Instance
	private static final DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	// アイテム実体
	private ItemStack itemstack;
	// ItemMeta
	private ItemMeta itemmeta;
	// Lore
	private List<String> itemlore;
	// Lore内の固有識別詞
	private List<String> identify = new ArrayList<String>();
	// アイテムレベル
	private int itemlv = 1;
	// カスタムメッセージ1
	private List<String> custom1 = new ArrayList<String>();
	// カスタムメッセージ2
	private List<String> custom2 = new ArrayList<String>();
	// 所有者のMCID
	private String owner = "";

	public GrwTool(Material material, String name, List<String> identify, int itemlv, List<String> custom1, List<String> custom2, String owner, boolean unbreakable, Map<Enchantment, Integer> enchant) {
		if (material == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] materialがnullのためGOLD_HELMETとして扱います。");
			material = Material.GOLD_HELMET;
		}
		if (name == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] nameが未設定のためemptyとして扱います。");
			name = "";
		}
		if (identify == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTool] identifyが未設定のためemptyとして扱います。");
			identify = GrwDefine.EMPTYSTRINGLIST;
		}
		// TODO ここから
	}

	/**
	 * 読み込み用コンストラクタ。ItemStackを引数とする。ItemStackの内容を解析し、各データを保持する。<br />
	 * Growth ToolではないItemStackを渡された場合は保証しない。<br />
	 *
	 * @param Growth ToolのItemStack
	 */
	public GrwTool(ItemStack tool) {
		itemstack = new ItemStack(tool);
		if (itemstack.hasItemMeta()) {
			itemmeta = itemstack.getItemMeta();
		} else {
			itemmeta = Bukkit.getItemFactory().getItemMeta(tool.getType());
		}
		if (itemmeta.hasLore()) {
			itemlore = itemmeta.getLore();
		} else {
			itemlore = new ArrayList<String>();
		}
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
	}

	/**
	 * ItemStack取得メソッド。<br />
	 * getの一元化によりMeta/Loreの書き換えを限定するために、ItemStackの拡張ではなくラッパーにしてある。<br />
	 *
	 * @return オブジェクトに対応するアイテム
	 */
	public ItemStack get() {
		List<String> lore = new ArrayList<String>();
		lore.add("");
		for (String s : identify) {
			lore.add(GrwDefine.IDENTHEAD + s);
		}
		lore.add("");
		if (itemlv != 0) {
			lore.add(GrwDefine.ILHEAD + String.valueOf(itemlv));
			int nextExp = getNBT(GrwNbti.NextExp, Integer.class);
			int currentExp = getNBT(GrwNbti.CurrentExp, Integer.class);
			if (nextExp != 0) {
				lore.add(GrwDefine.EXPHEAD + String.valueOf(currentExp) + " / " + String.valueOf(nextExp));
			}
		}
		for (String s : custom1) {
			lore.add(GrwDefine.CUSTOM1HEAD + s);
		}
		for (String s : custom2) {
			lore.add(GrwDefine.CUSTOM2HEAD + s);
		}
		if (owner != "") {
			lore.add("");
			lore.add(GrwDefine.OWNERHEAD + owner);
		}
		itemlore = lore;
		itemmeta.setLore(itemlore);
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}

	/**
	 * NBT設定メソッド。tagに対応するNBTのデータを書き込む。<br />
	 *
	 * @param tag NBTに対応するenum
	 * @param obj 設定値
	 */
	public void setNBT(GrwNbti tag, Object obj) {
		NBTItem nbti = new NBTItem(itemstack);
		nbti.setObject(tag.name(), obj);
		itemmeta = nbti.getItem().getItemMeta();
	}

	/**
	 * NBT取得メソッド。tagに対応するNBTのデータを読み込む。<br />
	 *
	 * @param tag NBTに対応するenum
	 * @param type 戻り値の型のクラス
	 * @return 取得値
	 */
	public <T> T getNBT(GrwNbti tag, Class<T> type) {
		NBTItem nbti = new NBTItem(itemstack);
		return nbti.getObject(tag.name(), type);
	}

	/**
	 * 名称設定メソッド。<br />
	 *
	 * @param name 新しい名称
	 */
	public void setName(String name) {
		itemmeta.setDisplayName(GrwDefine.NAMEHEAD + name);
	}

	/**
	 * 名称取得メソッド。名前が設定されていない場合はemptyを返却する。<br />
	 *
	 * @return 設定されている名称
	 */
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
	 */
	public boolean getUnbreakable() {
		return itemmeta.spigot().isUnbreakable();
	}

	/**
	 * 耐久無限フラグ設定メソッド<br />
	 *
	 * @param unbreakable 耐久無限フラグ <true: 耐久無限 / false: 未設定>
	 */
	public void setUnbreakable(boolean unbreakable) {
		itemmeta.spigot().setUnbreakable(unbreakable);
	}

	/**
	 * 固有識別詞設定メソッド。<br />
	 *
	 * @param identify このツールに対応する固有識別詞
	 */
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

	public boolean addExp() {
		return addExp(1);
	}

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
}
