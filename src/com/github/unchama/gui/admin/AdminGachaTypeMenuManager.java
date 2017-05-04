package com.github.unchama.gui.admin;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.AdminMenuManager;

public class AdminGachaTypeMenuManager extends AdminMenuManager {

	@Override
	public Inventory getInventory(Player player, int slot) {
		Inventory inv = this.getEmptyInventory(player);
		GachaType[] gt = GachaType.values();
		for (int i = 0; i < gt.length; i++) {
			ItemStack itemstack = gacha.getManager(gt[i].getManagerClass())
					.getGachaTypeInfo();
			if (itemstack == null)
				continue;
			inv.setItem(i, itemstack);
		}
		return inv;
	}

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
		GachaType[] gt = GachaType.values();
		for (int i = 0; i < gt.length; i++) {
			openmap.put(i, ManagerType.getTypebyClass(gt[i].getMenuManagerClass()));
		}
	}

	@Override
	public int getInventorySize() {
		return 5;
	}

	@Override
	public String getInventoryName(Player player) {
		return "" + ChatColor.RED + ChatColor.BOLD + "ガチャを選択";
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
