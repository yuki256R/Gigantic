package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import com.github.unchama.event.PlayerDamageWithArmorEvent;
import com.github.unchama.event.SecondEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Mon_chi
 */
public class ChobaEnchantment implements GiganticEnchantment {

    private int countFlag;

    public ChobaEnchantment() {
        this.countFlag = 0;
    }

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof SecondEvent) {
            if (countFlag < 2) {
                this.countFlag += 1;
            }
            else {
                this.countFlag = 0;
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 5, 2));
            }
        }
        else if (event instanceof PlayerDamageWithArmorEvent) {
            EntityDamageEvent e = ((PlayerDamageWithArmorEvent) event).getOriginEvent();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setDamage(e.getDamage() * 0.5);
            }
        }
        return false;
    }
}
