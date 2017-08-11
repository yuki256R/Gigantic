package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import com.github.unchama.util.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Mon_chi
 */
public class KotueiEnchantment implements GiganticEnchantment {
    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof BlockBreakEvent) {
            for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
                if (entity.getType() == EntityType.SKELETON && !entity.isDead()) {
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 50, 4));
                }
            }
        }
        return false;
    }
}
