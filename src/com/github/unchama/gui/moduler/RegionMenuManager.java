package com.github.unchama.gui.moduler;

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
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.region.RegionManager;
import com.sk89q.worldedit.bukkit.selections.Selection;

/**
 *
 *@author yuki_256
 *
 */

public class RegionMenuManager extends GuiMenuManager {

	@Override
	public String getInventoryName(Player player) {
		return "保護メニュー";
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
		return Sound.valueOf("BLOCK_ENCHANTMENT_TABLE_USE");
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
		id_map.put(0,"wand");
		id_map.put(1,"claim");
		id_map.put(2,"list");
		id_map.put(4,"gui");
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack itemstack = null;
		switch (slot) {
		case 0:
			itemstack = new ItemStack(Material.WOOD_AXE);
			break;
		case 1:
			itemstack = new ItemStack(Material.GOLD_AXE);
			break;
		case 2:
			itemstack = new ItemStack(Material.STONE_AXE);
			break;
		case 4:
			itemstack = new ItemStack(Material.DIAMOND_AXE);
			break;
		}
		return itemstack;
	}
	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		switch (slot) {
		case 0:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "保護設定用の木の斧を召喚");
			itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで召喚"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "※インベントリを空けておこう"
					, ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE + "保護のかけ方"
					, ChatColor.RESET + "" +  ChatColor.GREEN + "①召喚された斧を手に持ちます"
					, ChatColor.RESET + "" +  ChatColor.GREEN + "②保護したい領域の一方の角を" + ChatColor.YELLOW + "左" + ChatColor.GREEN + "クリック"
					, ChatColor.RESET + "" +  ChatColor.GREEN + "③もう一方の対角線上の角を" + ChatColor.RED + "右" + ChatColor.GREEN + "クリック"
					, ChatColor.RESET + "" +  ChatColor.GREEN + "④メニューの" + ChatColor.RESET + "" +  ChatColor.YELLOW + "金の斧" + ChatColor.RESET + "" +  ChatColor.GREEN + "をクリック"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "command->[//wand]"
					);
			itemmeta.setLore(lore);
			break;
		case 1:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "保護の申請");
			itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			lore.clear();

			Selection selection = com.github.unchama.util.Util.getWorldEdit().getSelection(player);

			if(!player.hasPermission("worldguard.region.claim")){
				lore.addAll(Arrays.asList(ChatColor.RED + "このワールドでは"
						, ChatColor.RED + "保護を申請出来ません"
						));
			}else if (selection == null) {
				lore.addAll(Arrays.asList(ChatColor.RED + "範囲指定されてません"
						, ChatColor.RED + "先に木の斧で2か所クリックしてネ"
						));
			}else if(selection.getLength() < 10||selection.getWidth() < 10){
				lore.addAll(Arrays.asList(ChatColor.RED + "選択された範囲が狭すぎます"
						, ChatColor.RED + "1辺当たり最低10ブロック以上にしてネ"
						));
			}else{
				itemmeta.addEnchant(Enchantment.DIG_SPEED, 100, false);
				lore.addAll(Arrays.asList(ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE + "範囲指定されています"
						, ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE + "クリックすると保護を申請します"
						));
			}

			if(player.hasPermission("worldguard.region.claim")){
				lore.addAll(Arrays.asList(ChatColor.DARK_GRAY + "Y座標は自動で全範囲保護されます"
						, ChatColor.RESET + "" +  ChatColor.YELLOW + "" + "A new region has been claimed"
						, ChatColor.RESET + "" +  ChatColor.YELLOW + "" + "named '" + player.getName() + "_" + gp.getManager(RegionManager.class).getRgnum() + "'."
						, ChatColor.RESET + "" +  ChatColor.GRAY + "と出れば保護設定完了です"
						, ChatColor.RESET + "" +  ChatColor.RED + "赤色で別の英文が出た場合"
						, ChatColor.RESET + "" +  ChatColor.GRAY + "保護の設定に失敗しています"
						, ChatColor.RESET + "" +  ChatColor.GRAY + "・別の保護と被っていないか"
						, ChatColor.RESET + "" +  ChatColor.GRAY + "・保護数上限に達していないか"
						, ChatColor.RESET + "" +  ChatColor.GRAY + "確認してください"
						));
			}

			itemmeta.setLore(lore);
			break;
		case 2:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "保護一覧を表示");
			itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで表示"
					, ChatColor.RESET + "" +  ChatColor.GRAY + "今いるワールドで"
					, ChatColor.RESET + "" +  ChatColor.GRAY + "あなたが保護している"
					, ChatColor.RESET + "" +  ChatColor.GRAY + "土地の一覧を表示します"
					, ChatColor.RESET + "" +  ChatColor.RED + "" + ChatColor.UNDERLINE + "/rg info 保護名"
					, ChatColor.RESET + "" +  ChatColor.GRAY + "該当保護の詳細情報を表示"
					, ChatColor.RESET + "" +  ChatColor.RED + "" + ChatColor.UNDERLINE + "/rg rem 保護名"
					, ChatColor.RESET + "" +  ChatColor.GRAY + "該当保護を削除する"
					, ChatColor.RESET + "" +  ChatColor.RED + "" + ChatColor.UNDERLINE + "/rg addmem 保護名 プレイヤー名"
					, ChatColor.RESET + "" +  ChatColor.GRAY + "該当保護に指定メンバーを追加"
					, ChatColor.RESET + "" +  ChatColor.RED + "" + ChatColor.UNDERLINE + "/rg removemember 保護名 プレイヤー名"
					, ChatColor.RESET + "" +  ChatColor.GRAY + "該当保護の指定メンバーを削除"
					, ChatColor.RESET + "" +  ChatColor.DARK_GRAY + "その他のコマンドはWikiを参照"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "command->[/rg list]"
					);
			itemmeta.setLore(lore);
			break;
		case 4:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "RegionGUI機能");
			itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで開く"
					, ChatColor.RESET + "" +  ChatColor.RED + "保護の作成と管理が超簡単に！"
					, ChatColor.RESET + "" +  ChatColor.RED + "クリックした場所によって挙動が変わります"
					, ChatColor.RESET + "" +  ChatColor.YELLOW + "自分の所有する保護内なら…"
					, ChatColor.RESET + "" +  ChatColor.GRAY + "保護の各種設定や削除が行えます"
					, ChatColor.RESET + "" +  ChatColor.YELLOW + "それ以外なら…"
					, ChatColor.RESET + "" +  ChatColor.GRAY + "新規保護の作成画面が表示されます"
					, ChatColor.RESET + "" + ChatColor.DARK_GRAY + "command->[/land]"
					);
			itemmeta.setLore(lore);
			break;
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		switch (identifier) {
		case "wand":
			player.closeInventory();
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			player.chat("//wand");
			player.sendMessage(ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE + "保護のかけ方\n"
					+ ChatColor.RESET + "" +  ChatColor.GREEN + "①召喚された斧を手に持ちます\n"
					+ ChatColor.RESET + "" +  ChatColor.GREEN + "②保護したい領域の一方の角を" + ChatColor.YELLOW + "左" + ChatColor.GREEN + "クリック\n"
					+ ChatColor.RESET + "" +  ChatColor.GREEN + "③もう一方の対角線上の角を" + ChatColor.RED + "右" + ChatColor.GREEN + "クリック\n"
					+ ChatColor.RESET + "" +  ChatColor.GREEN + "④メニューの" + ChatColor.RESET + "" +  ChatColor.YELLOW + "金の斧" + ChatColor.RESET + "" +  ChatColor.GREEN + "をクリック\n"
					+ ChatColor.DARK_GREEN + "解説ページ→" + ChatColor.UNDERLINE + "http://seichi.click/d/WorldGuard"
					);
			return true;
		case "claim":
			player.closeInventory();
			Selection selection = com.github.unchama.util.Util.getWorldEdit().getSelection(player);
			if(!player.hasPermission("worldguard.region.claim")){
				player.sendMessage(ChatColor.RED + "このワールドでは保護を申請できません");
				return false;
			}else if (selection == null) {
				player.sendMessage(ChatColor.RED + "先に木の斧で範囲を指定してからこのボタンを押してください");
				player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, (float)0.5);
				return false;
			}else if(selection.getLength() < 10||selection.getWidth() < 10){
				player.sendMessage(ChatColor.RED + "指定された範囲が狭すぎます。1辺当たり最低10ブロック以上にしてください");
				player.sendMessage(ChatColor.DARK_GRAY + "[TIPS]どうしても小さい保護が必要な人は直接コマンド入力で作ろう！");
				player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, (float)0.5);
				return false;
			}

			player.chat("//expand vert");
			player.chat("/rg claim " + player.getName() + "_" + gp.getManager(RegionManager.class).getRgnum());
			gp.getManager(RegionManager.class).increase(1);
			player.chat("//sel");
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			return true;
		case "list":
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			player.closeInventory();
			player.sendMessage(ChatColor.GRAY + "--------------------\n"
					+ ChatColor.GRAY + "複数ページの場合… " + ChatColor.RESET + "" +  ChatColor.RED + "" + ChatColor.BOLD + "/rg list ページNo\n"
					+ ChatColor.RESET + "" +  ChatColor.GRAY + "先頭に[+]のついた保護はOwner権限\n[-]のついた保護はMember権限を保有しています\n"
					+ ChatColor.DARK_GREEN + "解説ページ→" + ChatColor.UNDERLINE + "http://seichi.click/d/WorldGuard");
			player.chat("/rg list");
			return true;
		case "gui":
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			player.closeInventory();
			player.chat("/land");
			return true;
		}
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
