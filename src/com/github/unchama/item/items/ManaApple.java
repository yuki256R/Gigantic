package com.github.unchama.item.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.item.moduler.ManaEffect;
import com.github.unchama.item.moduler.NBTTag;
import com.github.unchama.util.Util;

public class ManaApple extends ItemStack implements NBTTag{
	/**書き換え禁止******************************/
	public static final String MANAAPPLENBT = "ManaApple";
	/******************************************/

	public ItemStack apple;

	public ManaApple(String name, ManaEffect effect, short id) {
		super(Material.GOLDEN_APPLE, 1, id);
		Util.setDisplayName(this,name);
		Util.setLore(this,effect.getName());
		apple = this.addNBTTag(this, MANAAPPLENBT, effect);
	}

	public ItemStack getTaggedApple(){
		return apple;
	}

}
