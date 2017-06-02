package com.github.unchama.gui.admin.build;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.util.ManaPotion;
import com.github.unchama.util.ManaPotion.ManaEffect;
import com.github.unchama.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Created by karayuu on 2017/05/31.
 */
public class AdminBuildMenuManager extends GuiMenuManager{
    @Override
    protected void setIDMap(HashMap<Integer, String> idmap) {
        idmap.put(0, "MAX");
        idmap.put(1, "300");
        idmap.put(2, "1500");
        idmap.put(3, "10000");
        idmap.put(4, "100000");
        idmap.put(8, "Mana");
    }

    @Override
    public boolean invoke(Player player, String identifier) {
        switch (identifier) {

            case "MAX":
                Util.addItem(player, ManaPotion.getDebugGachaApple(ManaEffect.MANA_FULL, 5));
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1,(float) 0.5);
                break;
            case "300":
                Util.addItem(player, ManaPotion.getDebugGachaApple(ManaEffect.MANA_SMALL, 5));
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1,(float) 0.5);
                break;
            case "1500":
                Util.addItem(player, ManaPotion.getDebugGachaApple(ManaEffect.MANA_MEDIUM, 5));
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1,(float) 0.5);
                break;
            case "10000":
                Util.addItem(player, ManaPotion.getDebugGachaApple(ManaEffect.MANA_LARGE, 5));
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1,(float) 0.5);
                break;
            case "100000":
                Util.addItem(player, ManaPotion.getDebugGachaApple(ManaEffect.MANA_TINY, 5));
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1,(float) 0.5);
                break;
            case "Mana":
                GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
                gp.getManager(ManaManager.class).setMana(0);
                gp.getManager(ManaManager.class).display(player);
                player.sendMessage(ChatColor.RED + "マナを0にセットしました。");
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1,(float) 0.5);
                break;
            default:
                return false;
        }
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
        return 9*1;
    }

    @Override
    public String getInventoryName(Player player) {
        return "建築系システム操作メニュー";
    }

    @Override
    protected InventoryType getInventoryType() {
        return null;
    }

    @Override
    protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
        ItemMeta itemmeta = itemstack.getItemMeta();
        List<String> lore;

        switch (slot) {
            case 0:
                itemmeta.setDisplayName(ChatColor.AQUA + "ガチャりんご(マナ完全回復) 5個付与");
                lore = new ArrayList<>();
                lore.add(ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで付与");
                itemmeta.setLore(lore);
                break;
            case 1:
                itemmeta.setDisplayName(ChatColor.AQUA + "ガチャりんご(マナ300回復) 5個付与");
                lore = new ArrayList<>();
                lore.add(ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで付与");
                itemmeta.setLore(lore);
                break;
            case 2:
                itemmeta.setDisplayName(ChatColor.AQUA + "ガチャりんご(マナ1500回復) 5個付与");
                lore = new ArrayList<>();
                lore.add(ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで付与");
                itemmeta.setLore(lore);
                break;
            case 3:
                itemmeta.setDisplayName(ChatColor.AQUA + "ガチャりんご(マナ10000回復) 5個付与");
                lore = new ArrayList<>();
                lore.add(ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで付与");
                itemmeta.setLore(lore);
                break;
            case 4:
                itemmeta.setDisplayName(ChatColor.AQUA + "ガチャりんご(マナ100000回復) 5個付与");
                lore = new ArrayList<>();
                lore.add(ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで付与");
                itemmeta.setLore(lore);
                break;
            case 8:
                itemmeta.setDisplayName(ChatColor.RED + "マナを0にセット");
                break;
        }
        return itemmeta;
    }

    @Override
    protected ItemStack getItemStack(Player player, int slot) {
        ItemStack itemstack = null;

        switch (slot) {
            case 0:
                itemstack = new ItemStack(Material.GOLDEN_APPLE);
                break;
            case 1:
                itemstack = new ItemStack(Material.GOLDEN_APPLE);
                break;
            case 2:
                itemstack = new ItemStack(Material.GOLDEN_APPLE);
                break;
            case 3:
                itemstack = new ItemStack(Material.GOLDEN_APPLE);
                break;
            case 4:
                itemstack = new ItemStack(Material.GOLDEN_APPLE);
                break;
            case 8:
                itemstack = new ItemStack(Material.BARRIER);
                break;
        }
        return itemstack;
    }

    @Override
    public Sound getSoundName() {
        return Sound.BLOCK_CHEST_OPEN;
    }

    @Override
    public float getVolume() {
        return 1;
    }

    @Override
    public float getPitch() {
        return (float) 0.5;
    }
}
