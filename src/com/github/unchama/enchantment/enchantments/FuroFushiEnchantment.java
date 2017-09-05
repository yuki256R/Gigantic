package com.github.unchama.enchantment.enchantments;

import com.github.unchama.enchantment.GiganticEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Mon_chi
 */
public class FuroFushiEnchantment implements GiganticEnchantment {

    @Override
    public boolean onEvent(Event event, Player player, ItemStack item, int level) {
        if (event instanceof FoodLevelChangeEvent) {
            if (((FoodLevelChangeEvent) event).getFoodLevel() < 7) {
                ((FoodLevelChangeEvent) event).setFoodLevel(7);
            }
        }
        return false;
    }
}
