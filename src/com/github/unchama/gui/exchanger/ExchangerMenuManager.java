package com.github.unchama.gui.exchanger;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.exchange.ExchangeType;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;

/**
 * Created by Mon_chi on 2017/06/18.
 */
public class ExchangerMenuManager extends GuiMenuManager {


    @Override
    public Inventory getInventory(Player player, int slot) {
        Inventory inventory = this.getEmptyInventory(player);
        int count = 0;
        for (ExchangeType type : ExchangeType.values()) {
            inventory.setItem(count, type.getIcon());
            count += 1;
        }
        return inventory;
    }

    @Override
    protected void setIDMap(HashMap<Integer, String> idmap) {
        int count = 0;
        for (ExchangeType type : ExchangeType.values()) {
            idmap.put(count, type.name());
            count += 1;
        }
    }

    @Override
    public boolean invoke(Player player, String identifier) {
        ExchangeType type = ExchangeType.valueOf(identifier);
        player.playSound(player.getLocation(), this.getSoundName(), this.getVolume(), this.getPitch());
        player.openInventory(type.getInventory());
        return true;
    }

    @Override
    protected void setOpenMenuMap(HashMap<Integer, GuiMenu.ManagerType> openmap) {

    }

    @Override
    protected void setKeyItem() {

    }

    @Override
    public String getClickType() {
        return null;
    }

    @Override
    public int getInventorySize() {
        return 5;
    }

    @Override
    public String getInventoryName(Player player) {
        return "" + ChatColor.GOLD + ChatColor.BOLD + "アイテム交換システム";
    }

    @Override
    protected InventoryType getInventoryType() {
        return InventoryType.HOPPER;
    }

    @Override
    protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
        return null;
    }

    @Override
    protected ItemStack getItemStack(Player player, int slot) {
        return null;
    }

    @Override
    public Sound getSoundName() {
        return Sound.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public float getVolume() {
        return 1.0F;
    }

    @Override
    public float getPitch() {
        return 1.0F;
    }
}
