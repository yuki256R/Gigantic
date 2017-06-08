package com.github.unchama.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;

/**
 *
 *@author yuki_256
 *
 */

public class HelpMenuManager extends GuiMenuManager {

	@Override
	public String getInventoryName(Player player) {
		return "ヘルプメニュー";
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
		id_map.put(0,"wiki");
		id_map.put(1,"rule");
		id_map.put(2,"map");
		id_map.put(3,"jms");
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack itemstack = null;
		switch (slot) {
		case 0:
			itemstack = new ItemStack(Material.BOOK);
			break;
		case 1:
			itemstack = new ItemStack(Material.PAPER);
			break;
		case 2:
			itemstack = new ItemStack(Material.MAP);
			break;
		case 3:
			itemstack = new ItemStack(Material.SIGN);
			break;
		}
		return itemstack;
	}
	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		switch (slot) {
		case 0:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "公式Wikiにアクセス");
			lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.GREEN + "鯖内の「困った」は公式Wikiで解決！"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "クリックするとチャット欄に"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "URLが表示されますので"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Tキーを押してから"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "そのURLをクリックしてください"
					);
			itemmeta.setLore(lore);
			break;
		case 1:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "運営方針とルールを確認");
			lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.GREEN + "当鯖で遊ぶ前に確認してネ！"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "クリックするとチャット欄に"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "URLが表示されますので"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Tキーを押してから"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "そのURLをクリックしてください"
					);
			itemmeta.setLore(lore);
			break;
		case 2:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "鯖Mapを見る");
			lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.GREEN + "webブラウザから鯖Mapを閲覧出来ます"
					, ChatColor.RESET + "" +  ChatColor.GREEN + "他人の居場所や保護の場所を確認出来ます"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "クリックするとチャット欄に"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "URLが表示されますので"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Tキーを押してから"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "そのURLをクリックしてください"
					);
			itemmeta.setLore(lore);
			break;
		case 3:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "JapanMinecraftServerリンク");
			lore = Arrays.asList(ChatColor.RESET + "" + ChatColor.DARK_GRAY + "クリックするとチャット欄に"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "URLが表示されますので"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Tキーを押してから"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "そのURLをクリックしてください"
					);
			itemmeta.setLore(lore);
			break;
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		switch (identifier) {
		case "wiki":
			player.sendMessage(ChatColor.RED + "" + ChatColor.UNDERLINE + "http://seichi.click");
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			player.closeInventory();
			return true;
		case "rule":
			player.sendMessage(ChatColor.RED + "" + ChatColor.UNDERLINE + "http://seichi.click/d/%b1%bf%b1%c4%ca%fd%bf%cb%a4%c8%a5%eb%a1%bc%a5%eb");
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			player.closeInventory();
			return true;
		case "map":
			player.sendMessage(ChatColor.RED + "" + ChatColor.UNDERLINE + "http://seichi.click/d/DynmapLinks");
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			player.closeInventory();
			return true;
		case "jms":
			player.sendMessage(ChatColor.RED + "" + ChatColor.UNDERLINE + "https://minecraft.jp/servers/54d3529e4ddda180780041a7");
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			player.closeInventory();
			return true;
		}
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
