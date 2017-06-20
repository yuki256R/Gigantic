package com.github.unchama.gui.subhome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;

/**
 *
 * @author yuki_256
 *
 */
public class HomeProtectMenuManager extends GuiMenuManager {

	public int getNum(){
		return HomeMenuManager.getHomeNum();
	}

	@Override
	public String getInventoryName(Player player) {
		return "ホームポイント" + getNum() + "を変更しますか";
	}

	protected void setKeyItem() {
	}

	@Override
	public String getClickType() {
		return "";
	}

	@Override
	public int getInventorySize() {
		return 5;
	}


	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_WOOD_BUTTON_CLICK_ON;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return (float) 0.8;
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		id_map.put(0, "yes");
		id_map.put(4, "no");
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack itemstack = null;
		switch(slot){
		case 0:
			itemstack = new ItemStack(Material.WOOL,1,(short) 5);
			break;
		case 4:
			itemstack = new ItemStack(Material.WOOL,1,(short) 14);
			break;
		}
		return itemstack;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		switch(slot){
		case 0:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "ホームポイント" + getNum() + "を設定");
			lore = Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "現在位置をホームポイント" + getNum()
					, ChatColor.RESET + "" + ChatColor.GRAY + "として設定します"
					, ChatColor.RESET + "" + ChatColor.	DARK_GRAY + "※上書きされます"
					, ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで設定"
					);
			itemmeta.setLore(lore);
			break;
		case 4:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "ホームポイントを設定しない");
			lore = Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "現在位置をホームポイント"
					, ChatColor.RESET + "" + ChatColor.GRAY + "として設定しません"
					, ChatColor.RESET + "" + ChatColor.	DARK_GRAY + "※ホームメニューに戻るには右クリックで！"
					, ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで閉じる"
					);
			itemmeta.setLore(lore);
			break;
		}
		return itemmeta;
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		switch(identifier){
		case "yes":
			player.closeInventory();
			player.chat("/home set " + getNum());
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			return true;
		case "no":
			player.closeInventory();
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			return true;
		}
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
	}

}