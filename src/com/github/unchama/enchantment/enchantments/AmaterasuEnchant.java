package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import com.github.unchama.event.SecondEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

/**
 * @author Mon_chi
 */
public class AmaterasuEnchant implements GiganticEnchantment {
    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof SecondEvent) {
            for (Entity entity : player.getNearbyEntities(30, 30, 30)) {
                if (entity instanceof LivingEntity) {
                    entity.setFireTicks(20);
                }
            }
        }
        return false;
    }
}
