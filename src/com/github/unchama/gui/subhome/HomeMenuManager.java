package com.github.unchama.gui.subhome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.home.HomeProtectManager;

/**
 *
 * @author yuki_256
 *
 */

public class HomeMenuManager extends GuiMenuManager {

	private static int homenum;

	public static int getHomeNum() {
		return homenum;
	}

	public static int setHomeNum(int homenum) {
		return HomeMenuManager.homenum = homenum;
	}

	public int getMax() {
		return config.getSubHomeMax();
	}

	public String getInventoryName(Player player) {
		return "ホームメニュー";
	}

	protected void setKeyItem() {
	}

	@Override
	public String getClickType() {
		return "";
	}

	@Override
	public int getInventorySize() {
		return 3*9;
	}


	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.CHEST;
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
		id_map.put(0, "protect");
		id_map.put(9, "testinfo");
		id_map.put(18, "changeicon");
		for(int i = 2 ; i < getMax() + 2 ; i++){
			id_map.put(i, "subset");
			id_map.put(i + 9, "info" + Integer.toString(i-1));
			id_map.put(i + 18, "home");
		}
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack itemstack = null;
		switch(slot){
		case 0:
			itemstack = new ItemStack(Material.BARRIER);
			break;
		case 9:
			itemstack = new ItemStack(Material.PAPER);
			break;
		case 18:
			itemstack = new ItemStack(Material.TRIPWIRE_HOOK);
			break;
		}
		for(int i = 2 ; i < getMax() + 2 ; i++){
			if(slot == i) {
				itemstack = new ItemStack(Material.BED);
			}else if (slot == i + 9){
				itemstack = new ItemStack(Material.PAPER);
			}else if(slot == i + 18){
				itemstack = new ItemStack(Material.COMPASS);
			}
		}
		return itemstack;
	}
	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		switch(slot){
		case 0:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "ホームポイント保護機能切替");

			if(gp.getManager(HomeProtectManager.class).getHomeProtect() == false) {
				itemmeta.addEnchant(Enchantment.DIG_SPEED, 100, false);

				lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.DARK_GRAY + "※ログイン毎に自動で有効になります"
					,ChatColor.RESET + "" +  ChatColor.GRAY + "ホームポイントが"
					,ChatColor.RESET + "" +  ChatColor.GRAY + "直接上書きされません"
					,ChatColor.RED + "" + ChatColor.UNDERLINE + "クリックで保護を無効"
					);
			}else {
				itemmeta.removeEnchant(Enchantment.DIG_SPEED);
				lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.DARK_GRAY + "※ログイン毎に自動で有効になります"
					,ChatColor.RESET + "" +  ChatColor.GRAY + "ホームポイントが"
					,ChatColor.RESET + "" +  ChatColor.GRAY + "直接上書きされます"
					,ChatColor.GREEN + "" + ChatColor.UNDERLINE + "クリックで保護を有効"
					);
			}
			itemmeta.setLore(lore);
			break;
		case 9:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "テスト情報");
			lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.GRAY + "現在のsevernum==>" + config.getServerNum());
			itemmeta.setLore(lore);
			break;
		case 18:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "アイコン切替機能");
			lore = Arrays.asList(ChatColor.GRAY + "未実装");
			itemmeta.setLore(lore);
			break;
		}
		for(int i = 2 ; i < getMax() + 2 ; i++) {
			if(slot == i) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "ホームポイント" + (i-1) + "を設定");

				if(gp.getManager(HomeProtectManager.class).getHomeProtect() == false) {
					lore = Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "現在位置をホームポイント" + (i-1)
							, ChatColor.RESET + "" + ChatColor.GRAY + "として設定します"
							, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "※直接上書きされません"
							, ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで設定"
							, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "command->[/home set " + (i-1) + "]"
							);
				}else {
					lore = Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "現在位置をホームポイント" + (i-1)
							, ChatColor.RESET + "" + ChatColor.GRAY + "として設定します"
							, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "※直接上書きされます"
							, ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで設定"
							, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "command->[/home set " + (i-1) + "]"
							);
				}
				itemmeta.setLore(lore);
				break;
			}else if(slot == i+9){
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "ホームポイント" + (i-1) + "の情報");
				lore = Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "いんふぉ"
						, ChatColor.RESET + "" + ChatColor.DARK_GRAY + ""
						);
				itemmeta.setLore(lore);
			}else if(slot == i+18) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "ホームポイント" + (i-1) + "にワープ");
				lore = Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "あらかじめ設定した"
						, ChatColor.RESET + "" + ChatColor.GRAY + "ホームポイント" + (i-1) + "にワープします"
						, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "うまく機能しない時は"
						, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "再接続してみてください"
						, ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックでワープ"
						, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "command->[/home tp " + (i-1) + "]"
						);
				itemmeta.setLore(lore);
			}
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

		switch(identifier) {
		case "protect":
			if(gp.getManager(HomeProtectManager.class).getHomeProtect() == false) {
				gp.getManager(HomeProtectManager.class).setHomeProtect(true);
				player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
				player.openInventory(this.getInventory(player));
			}else {
				gp.getManager(HomeProtectManager.class).setHomeProtect(false);
				player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
				player.openInventory(this.getInventory(player));
			}
			return true;
		}

		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap){
			for(int i = 2 ; i < getMax() + 2 ; i++){
				openmap.put(i, GuiMenu.ManagerType.HOMEPROTECTMENU);
			}
			//openmap.put(18, GuiMenu.ManagerType.HOMEICONMENU);
	}

	@Override
	public boolean islocked(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

		for(int i = 2 ; i < getMax() + 2 ; i++){
			if(gp.getManager(HomeProtectManager.class).getHomeProtect() == true){
				if(slot == i){
					player.chat("/home set " + (i-1));
					player.closeInventory();
					player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
					return true;
				}
			}else {
				switch(slot){
				case 2:
					setHomeNum(1);
					break;
				case 3:
					setHomeNum(2);
					break;
				case 4:
					setHomeNum(3);
					break;
				case 5:
					setHomeNum(4);
					break;
				case 6:
					setHomeNum(5);
					break;
				case 7:
					setHomeNum(6);
					break;
				case 8:
					setHomeNum(7);
					break;
				}
			}

			if(slot == i+18){
				player.chat("/home tp " + (i-1));
				player.closeInventory();
				player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			}
		}
		return false;
	}

}