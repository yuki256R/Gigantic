package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Mon_chi
 */
public class WaikyokuEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if (e.getEntity() instanceof LivingEntity) {
                LivingEntity victim = (LivingEntity) e.getEntity();
                if (!victim.isDead()) {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 1));
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 255));
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 5, 1));
                    for (Entity entity : victim.getNearbyEntities(10, 10, 10)) {
                        if (entity != player && entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 1));
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 255));
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 5, 1));
                        }
                    }
                }
            }
        }
        return false;
    }
}
