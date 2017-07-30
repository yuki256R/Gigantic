package com.github.unchama.item;

import org.bukkit.inventory.ItemStack;

import com.github.unchama.item.items.ManaApple;
import com.github.unchama.item.items.OreTicket;
import com.github.unchama.item.moduler.ManaEffect;

public enum GiganticItem {
	ORE_TICKET(new OreTicket()),
	MANA_FULL(new ManaApple("流星林檎", ManaEffect.MANA_FULL, (short)1).getTaggedApple()),
	MANA_SMALL(new ManaApple("林檎(小)", ManaEffect.MANA_SMALL, (short)0).getTaggedApple()),
	MANA_MEDIUM(new ManaApple("林檎(中)", ManaEffect.MANA_MEDIUM, (short)0).getTaggedApple()),
	MANA_LARGE(new ManaApple("林檎(大)", ManaEffect.MANA_LARGE, (short)0).getTaggedApple()),
	MANA_HUGE(new ManaApple("林檎(極)", ManaEffect.MANA_HUGE, (short)0).getTaggedApple()),
	;

	private final ItemStack is;

	GiganticItem(ItemStack is){
		this.is = is;
	}

	/**
	 * @return is
	 */
	public ItemStack getItemStack() {
		return is.clone();
	}

}
