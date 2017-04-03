package com.github.unchama.enumdata;

import com.github.unchama.gui.minestack.build.CategoryBuildMenuManager;
import com.github.unchama.gui.minestack.drop.CategoryDropMenuManager;
import com.github.unchama.gui.minestack.farm.CategoryFarmMenuManager;
import com.github.unchama.gui.minestack.mine.CategoryMineMenuManager;
import com.github.unchama.gui.minestack.redstone.CategoryRedstoneMenuManager;
import com.github.unchama.gui.moduler.MineStackMenuManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum StackCategory {
	MINE("採掘系アイテム", CategoryMineMenuManager.class, Material.DIAMOND),
	DROP("ドロップ系アイテム", CategoryDropMenuManager.class, Material.BONE),
	FARM("農業系アイテム", CategoryFarmMenuManager.class, Material.WHEAT),
	BUILD("建築系アイテム", CategoryBuildMenuManager.class, Material.BRICK),
	REDSTONE("レッドストーン系アイテム", CategoryRedstoneMenuManager.class, Material.REDSTONE_COMPARATOR),
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
