package com.github.unchama.gui.moduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.GuiMenu.ManagerType;

public class BuildMenuManager extends GuiMenuManager{

	public BuildMenuManager(){
		Bukkit.getServer().getLogger().info("BuildMenuManagerコンストラクタ");
		setKeyItem();
	}
	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
	}

	@Override
	protected void setKeyItem() {
		this.keyitem = new KeyItem(Material.STICK);
		Bukkit.getServer().getLogger().info("keyitem:" + this.keyitem.getMaterial().toString());
	}

	@Override
	public String getClickType() {
		Bukkit.getServer().getLogger().info("clicktype");
		return "left";
	}

	@Override
	public int getInventorySize() {
		return 36;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.DARK_PURPLE + "Buildメニュー";
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.CHEST;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore;
		
		switch(slot){
		case 1:
			itemmeta.setDisplayName("テストぉぉぉ");
			lore = new ArrayList<String>();
			lore.add("ふにゃぺけ");
			itemmeta.setLore(lore);
			break;
		}
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack itemstack = null;
		switch(slot){
		case 1:
			itemstack = new ItemStack(Material.STONE,1,(short)0);
		}
		return itemstack;
	}

	@Override
	public Sound getSoundName() {
		return null;
	}

	@Override
	public float getVolume() {
		return 0;
	}

	@Override
	public float getPitch() {
		return 0;
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {	
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		return false;
	}

}
