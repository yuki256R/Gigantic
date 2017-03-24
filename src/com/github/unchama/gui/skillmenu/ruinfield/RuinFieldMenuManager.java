package com.github.unchama.gui.skillmenu.ruinfield;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.gui.moduler.SkillMenuManager;
import com.github.unchama.player.skill.RuinField;

public class RuinFieldMenuManager extends SkillMenuManager{

	@Override
	public String getInventoryName(Player player) {
		return RuinField.getJPName();
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		MenuType mt = MenuType.getMenuTypebySlot(slot);
		if(mt == null)return null;
		ItemMeta itemmeta = itemstack.getItemMeta();
		switch(mt){
		case INFO:
			itemmeta.setDisplayName(ChatColor.GREEN + "基本情報");
			break;
		case RANGE:
			itemmeta.setDisplayName(ChatColor.BLUE + "範囲設定");
			break;
		case ORIGIN:
			itemmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "起点設定");
			break;
		case BOOK:
			itemmeta.setDisplayName(ChatColor.RED + "専用スキルブックを受け取る");
			break;
		case EXTENSION:
			itemmeta.setDisplayName(ChatColor.DARK_AQUA + "スキル強化");
			break;
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}


	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		MenuType mt = MenuType.getMenuTypebySlot(slot);
		if(mt == null)return null;
		ItemStack itemstack = null;
		switch(mt){
		case INFO:
			itemstack = new ItemStack(RuinField.getMenuMaterial());
			break;
		case RANGE:
			itemstack = new ItemStack(Material.GLASS);
			break;
		case ORIGIN:
			itemstack = new ItemStack(Material.SKULL_ITEM);
			break;
		case BOOK:
			itemstack = new ItemStack(Material.BOOK);
			break;
		case EXTENSION:
			itemstack = new ItemStack(Material.ENCHANTMENT_TABLE);
			break;
		}

		if(itemstack != null){
			ItemMeta itemmeta = this.getItemMeta(player, slot, itemstack);
			if(itemmeta != null){
				itemstack.setItemMeta(itemmeta);
			}
		}
		return itemstack;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, Class<? extends GuiMenuManager>> openmap) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
