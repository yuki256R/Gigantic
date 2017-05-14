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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        openmap.put(0, GuiMenu.ManagerType.BUILDMENU);
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
        ItemMeta itemmeta = itemstack.getItemMeta();
        List<String> lore;

        switch(slot) {
            case 0:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                    + "メインページへ");
                lore = new ArrayList<String>();
                lore.add("" + ChatColor.RED + "" + ChatColor.UNDERLINE + "クリックで移動");
                itemmeta.setLore(lore);
                break;

            case 4:
                itemmeta.setDisplayName();
        }
        return null;
    }

    @Override
    protected ItemStack getItemStack(Player player, int slot) {
        ItemStack itemstack = null;

        switch(slot) {
            //元のベージへ戻るボタン
            case 0:
                itemstack = new ItemStack(Material.BARRIER);
                break;

            //設置時に下の空洞を埋める機能
            case 4:
                itemstack = new ItemStack(Material.DIRT);
                break;

            //現在の設定
            case 13:
                itemstack = new ItemStack(Material.STONE);
                break;

            //範囲:最大値
            case 19:
                itemstack = new ItemStack(Material.SKULL_ITEM,11,(short)3);
                break;

            //範囲:一段階広く
            case 20:
                itemstack = new ItemStack(Material.SKULL_ITEM,7,(short)3);
                break;

            //範囲:初期値
            case 22:
                itemstack = new ItemStack(Material.SKULL_ITEM,5,(short)3);
                break;

            //範囲:一段階狭く
            case 24:
                itemstack = new ItemStack(Material.SKULL_ITEM,3,(short)3);
                break;

            //範囲:最小値
            case 25:
                itemstack = new ItemStack(Material.SKULL_ITEM,1,(short)3);
                break;

            //MineStack優先設定
            case 35:
                itemstack = new ItemStack(Material.CHEST);
                break;

        }
        return itemstack;
    }

    @Override
    public Sound getSoundName() {
        return Sound.BLOCK_FENCE_GATE_OPEN;
    }

    @Override
    public float getVolume() {
        return 1;
    }

    @Override
    public float getPitch() {
        return (float)0.1;
    }
}
