package com.github.unchama.enchantment;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mon_chi on 2017/06/03.
 */
public interface GiganticEnchantment {

    boolean onEvent(Event event, ItemStack item, int level);

}