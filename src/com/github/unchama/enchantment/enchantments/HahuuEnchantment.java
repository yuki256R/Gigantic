package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * @author Mon_chi
 */
public class HahuuEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof BlockBreakEvent) {
            BlockBreakEvent e = (BlockBreakEvent)event;
            for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = ((LivingEntity) entity);
                    livingEntity.damage(6);
                    if (!livingEntity.isDead()) {
                        Vector direction = entity.getLocation().getDirection().normalize().multiply(3);
                        direction.setX(-direction.getX()).setY(-direction.getY()).setZ(-direction.getZ());
                        entity.setVelocity(direction);
                    }
                }
            }
        }
        return false;
    }
}
