package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * @author Mon_chi
 */
public class HangekiEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if (e.getEntity() == player && e.getDamager() != player) {
                Vector direction = e.getDamager().getLocation().getDirection().normalize().multiply(10);
                direction.setX(-direction.getX()).setY(-direction.getY()).setZ(-direction.getZ());
                e.getDamager().setVelocity(direction.add(e.getDamager().getVelocity()));
            }
        }
        return false;
    }
}
