package com.github.unchama.gacha.moduler;

import org.bukkit.inventory.ItemStack;

public class GachaItem {
	private ItemStack item;
	private int amount;
	private Rarity rarity;

	public GachaItem(ItemStack item,int amount,Rarity rarity){
		this.item = item;
		this.amount = amount;
		this.rarity = rarity;
	}

}
