package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mon_chi on 2017/06/04.
 */
public class TestEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, ItemStack item, int level) {
        if (event instanceof PlayerItemHeldEvent) {
            PlayerItemHeldEvent e = (PlayerItemHeldEvent) event;
            e.getPlayer().sendMessage("Held : " + item.getType().name() + " : " + level);
            return false;
        }
        else if (event instanceof BlockBreakEvent) {
            BlockBreakEvent e = (BlockBreakEvent) event;
            e.getPlayer().sendMessage("Break : " + item.getType().name() + " : " + level);
        }
        return false;
    }
}
