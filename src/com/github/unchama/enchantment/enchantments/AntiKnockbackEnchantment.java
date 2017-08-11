package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import com.github.unchama.event.PlayerDamageWithArmorEvent;
import com.github.unchama.gigantic.Gigantic;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * @author Mon_chi
 */
public class AntiKnockbackEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof PlayerDamageWithArmorEvent) {
            System.out.println(player.getName());
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 3);
            Bukkit.getScheduler().runTaskLater(Gigantic.plugin, () -> player.setVelocity(new Vector(0, 0, 0)), 1L);
        }
        return false;
    }
}
