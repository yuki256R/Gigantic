package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Mon_chi
 */
public class YuwakuEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof EntityTargetLivingEntityEvent) {
            EntityTargetLivingEntityEvent e = (EntityTargetLivingEntityEvent) event;
            if (e.getReason() == EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY) {
                return true;
            }
        }
        return false;
    }
}
