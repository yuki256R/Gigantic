package com.github.unchama.gui.buildskill;

import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

/**
 * Created by karayuu on 2017/05/12.
 */
public class ZoneSkillDataMenuManager extends GuiMenuManager{
    @Override
    protected void setIDMap(HashMap<Integer, String> idmap) {

    }

    @Override
    public boolean invoke(Player player, String identifier) {
        return false;
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
        return 9*4;
    }

    @Override
    public String getInventoryName(Player player) {
        return ChatColor.DARK_PURPLE + "「範囲設置スキル」設定画面";
    }

    @Override
    protected InventoryType getInventoryType() {
        return null;
    }

    @Override
    protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
        return null;
    }

    @Override
    protected ItemStack getItemStack(Player player, int slot) {
        ItemStack itemstack = null;

        switch(slot) {
            //元のベージへ戻るボタン
            case 0:
                itemstack = new ItemStack(Material.BARRIER);

            //設置時に下の空洞を埋める機能

        }
        return null;
    }

    @Override
    public Sound getSoundName() {
        return null;
    }

    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public float getPitch() {
        return 0;
    }
}
