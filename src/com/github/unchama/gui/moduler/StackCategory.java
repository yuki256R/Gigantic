package com.github.unchama.gui.moduler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.GuiMenu.ManagerType;

public enum StackCategory {
	BUILD("建築・装飾", ManagerType.BUILDCATEGORYMENU, Material.BRICK),
	ITEM("道具・農業", ManagerType.ITEMCATEGORYMENU, Material.CAKE),
	REDSTONE("赤石・移動", ManagerType.REDSTONECATEGORYMENU, Material.REDSTONE_COMPARATOR),
	MATERIAL("醸造・材料", ManagerType.MATERIALCATEGORYMENU, Material.BREWING_STAND_ITEM),
	OTHERWISE("その他", ManagerType.OTHERWISECATEGORYMENU, Material.WATER_BUCKET),
	;

	String name;
	ManagerType mt;
	ItemStack menuIcon;

	StackCategory(String name, ManagerType mt, Material material){
		this.name = ChatColor.RESET+name;
		this.mt = mt;
		this.menuIcon = new ItemStack(material);
		ItemMeta meta = menuIcon.getItemMeta();
		meta.setDisplayName(this.name);
		menuIcon.setItemMeta(meta);
	}

	public String getName(){
		return name;
	}

	public ManagerType getManagerType(){
		return mt;
	}

	public ItemStack getMenuIcon(){
		return menuIcon;
	}
}
