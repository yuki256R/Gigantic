package com.github.unchama.gui.ranking;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.util.Util;

/**
 * @author tar0ss
 *
 */
public class RankingSelectMenuManager extends GuiMenuManager {

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
		openmap.put(0, ManagerType.TOTALMINEBLOCKRANKINGMENU);
		openmap.put(9, ManagerType.DAYMINEBLOCKRANKINGMENU);
		openmap.put(18, ManagerType.WEEKMINEBLOCKRANKINGMENU);
		openmap.put(27, ManagerType.MONTHMINEBLOCKRANKINGMENU);
		openmap.put(36, ManagerType.YEARMINEBLOCKRANKINGMENU);
	}

	@Override
	protected void setKeyItem() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getClickType() {
		return null;
	}

	@Override
	public int getInventorySize() {
		return 54;
	}

	@Override
	public String getInventoryName(Player player) {
		return "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "各種ランキング一覧";
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack itemstack = null;
		switch (slot) {
		case 0:
			itemstack = head.getMobHead("blue_chalice");
			Util.setDisplayName(itemstack, "" + ChatColor.YELLOW + "整地量ランキング（総合）");
			break;
		case 9:
			itemstack = head.getMobHead("blue_chalice");
			Util.setDisplayName(itemstack, "" + ChatColor.YELLOW + "整地量ランキング（日間）");
			break;
		case 18:
			itemstack = head.getMobHead("blue_chalice");
			Util.setDisplayName(itemstack, "" + ChatColor.YELLOW + "整地量ランキング（週間）");
			break;
		case 27:
			itemstack = head.getMobHead("blue_chalice");
			Util.setDisplayName(itemstack, "" + ChatColor.YELLOW + "整地量ランキング（月間）");
			break;
		case 36:
			itemstack = head.getMobHead("blue_chalice");
			Util.setDisplayName(itemstack, "" + ChatColor.YELLOW + "整地量ランキング（年間）");
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		default:
			break;
		}
		return itemstack;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_WOODEN_TRAPDOOR_OPEN;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return 0.6F;
	}

}
