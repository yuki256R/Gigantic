package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Mon_chi
 */
public class ItazuraEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if (e.getDamager() == player && e.getEntity() instanceof LivingEntity && e.getEntityType() != EntityType.CHICKEN) {
                e.getEntity().remove();
                e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CHICKEN);
                return true;
            }
        }
        return false;
    }
}
