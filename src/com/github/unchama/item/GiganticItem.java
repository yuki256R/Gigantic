package com.github.unchama.item;

import org.bukkit.inventory.ItemStack;

import com.github.unchama.item.items.OreTicket;

public enum GiganticItem {
	ORE_TICKET(new OreTicket()),
	;

	private final ItemStack is;

	GiganticItem(ItemStack is){
		this.is = is;
	}

	/**
	 * @return is
	 */
	public ItemStack getItemStack() {
		return is;
	}

}
