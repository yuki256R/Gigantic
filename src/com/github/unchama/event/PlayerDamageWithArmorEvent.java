package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Mon_chi
 */
public class PlayerDamageWithArmorEvent extends CustomEvent {

    private EntityDamageEvent originEvent;
    private Player player;
    private ItemStack item;
    private ArmorType armorType;

    public PlayerDamageWithArmorEvent(EntityDamageEvent originEvent, Player player, ItemStack item, ArmorType armorType) {
        this.originEvent = originEvent;
        this.player = player;
        this.item = item;
        this.armorType = armorType;
    }

    public EntityDamageEvent getOriginEvent() {
        return originEvent;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItem() {
        return item;
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public enum ArmorType {

        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS
    }
}
