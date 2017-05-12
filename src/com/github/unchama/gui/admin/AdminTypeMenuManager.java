package com.github.unchama.gui.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.AdminMenuManager;
import com.github.unchama.util.MobHead;
import com.github.unchama.yml.CustomHeadDataManager;

public class AdminTypeMenuManager extends AdminMenuManager {

	// 各ボタンのスロット番号
	private final int gachaSlot = 0;
	private final int customHeadSlot = 4;

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean invoke(Player player, String identifier) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(gachaSlot, ManagerType.ADMINGACHATYPEMENU);
		openmap.put(customHeadSlot, ManagerType.ADMINCUSTOMHEADGIBEMENU);
	}

	@Override
	public int getInventorySize() {
		return 5;
	}

	@Override
	public String getInventoryName(Player player) {
		return "" + ChatColor.RED + ChatColor.BOLD + "管理メニュー";
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		switch (slot) {
		case gachaSlot:
			itemmeta.setDisplayName(ChatColor.DARK_AQUA + "ガチャシステム");
			itemmeta.setLore(lore);
			break;
		case customHeadSlot:
			itemmeta.setDisplayName(ChatColor.DARK_AQUA + "カスタムヘッド");
			itemmeta.setLore(lore);
			break;
		default:
			break;
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack itemstack = null;
		CustomHeadDataManager headManager = Gigantic.yml.getManager(CustomHeadDataManager.class);
		switch (slot) {
		case gachaSlot:
			itemstack = MobHead.getMobHead("red_present");
			break;
		case customHeadSlot:
			itemstack = headManager.getMobHead("Zombie");
			break;
		default:
			break;
		}
		return itemstack;
	}

}
