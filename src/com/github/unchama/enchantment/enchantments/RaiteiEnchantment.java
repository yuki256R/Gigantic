package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import com.github.unchama.event.PlayerDamageWithArmorEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mon_chi
 */
public class RaiteiEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof PlayerDamageWithArmorEvent) {
            PlayerDamageWithArmorEvent e = (PlayerDamageWithArmorEvent) event;
            if (e.getOriginEvent().getDamager() instanceof LivingEntity) {
                LivingEntity damager = (LivingEntity) e.getOriginEvent().getDamager();
                damager.damage(12, player);
                List<Entity> entitiesAroundDamager = damager.getNearbyEntities(10, 10, 10);
                List<Integer> damagedEntities = new ArrayList<>();
                for (Entity entity : entitiesAroundDamager) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.damage(8, player);
                        damagedEntities.add(livingEntity.getEntityId());
                        for (Entity nestedEntity: livingEntity.getNearbyEntities(10, 10, 10)) {
                            if (!entitiesAroundDamager.contains(nestedEntity) && !damagedEntities.contains(nestedEntity.getEntityId())) {
                                livingEntity.damage(4, player);
                                damagedEntities.add(nestedEntity.getEntityId());
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
