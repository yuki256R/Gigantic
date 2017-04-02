package com.github.unchama.gui.moduler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.minestack.build.CategoryBuildMenuManager;
import com.github.unchama.gui.minestack.item.CategoryItemMenuManager;
import com.github.unchama.gui.minestack.material.CategoryMaterialMenuManager;
import com.github.unchama.gui.minestack.otherwise.CategoryOtherwiseMenuManager;
import com.github.unchama.gui.minestack.redstone.CategoryRedstoneMenuManager;

public enum StackCategory {
	BUILD("建築・装飾", CategoryBuildMenuManager.class, Material.BRICK),
	ITEM("道具・農業", CategoryItemMenuManager.class, Material.CAKE),
	REDSTONE("赤石・移動", CategoryRedstoneMenuManager.class, Material.REDSTONE_COMPARATOR),
	MATERIAL("醸造・材料", CategoryMaterialMenuManager.class, Material.BREWING_STAND_ITEM),
	OTHERWISE("その他", CategoryOtherwiseMenuManager.class, Material.WATER_BUCKET),
	;

	String name;
	Class<? extends MineStackMenuManager> managerClass;
	ItemStack menuIcon;

	StackCategory(String name, Class<? extends MineStackMenuManager> managerClass, Material material){
		this.name = ChatColor.RESET+name;
		this.managerClass = managerClass;
		this.menuIcon = new ItemStack(material);
		ItemMeta meta = menuIcon.getItemMeta();
		meta.setDisplayName(this.name);
		menuIcon.setItemMeta(meta);
	}

	public String getName(){
		return name;
	}

	public Class<? extends MineStackMenuManager> getManagerClass(){
		return managerClass;
	}

	public ItemStack getMenuIcon(){
		return menuIcon;
	}
}
