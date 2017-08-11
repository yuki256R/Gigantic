package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Mon_chi
 */
public class SyunpoEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof PlayerInteractEvent) {
        }
        return false;
    }
}
