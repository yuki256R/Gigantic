package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import com.github.unchama.event.PlayerDamageWithArmorEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Mon_chi
 */
public class DotonEnchantment implements GiganticEnchantment {
    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof PlayerDamageWithArmorEvent) {
            PlayerDamageWithArmorEvent e = (PlayerDamageWithArmorEvent) event;
            if (e.getOriginEvent().getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH || e.getOriginEvent().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                e.getOriginEvent().setCancelled(true);
            }
        }
        return false;
    }
}
