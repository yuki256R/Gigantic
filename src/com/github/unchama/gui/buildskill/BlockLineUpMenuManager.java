package com.github.unchama.gui.buildskill;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.buildskill.BuildSkillManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author karayuu
 */
public class BlockLineUpMenuManager extends GuiMenuManager{
    @Override
    protected void setIDMap(HashMap<Integer, String> idmap) {
        idmap.put(0, "Skill");
        idmap.put(1, "HalfBlock");
        idmap.put(2, "Break");
        idmap.put(8, "MineStack");
    }

    @Override
    public boolean invoke(Player player, String identifier) {
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        BuildSkillManager bsm = gp.getManager(BuildSkillManager.class);

        switch (identifier) {
            case "Skill":
                bsm.toggle_LineUp();
                break;
            case "HalfBlock":
                bsm.toggle_LineUpHalfBlock();
                break;
            case "Break":
                bsm.toggle_LineUpBreak();
                break;
            case "MineStack":
                bsm.toggle_LineUpMinestack();
                break;
            default:
                return false;
        }
        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
        player.openInventory(this.getInventory(player));
        return true;
    }

    @Override
    protected void setOpenMenuMap(HashMap<Integer, GuiMenu.ManagerType> openmap) {
        openmap.put(9, GuiMenu.ManagerType.BUILDMENU);
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
        return 9*2;
    }

    @Override
    public String getInventoryName(Player player) {
        return ChatColor.DARK_PURPLE + "「ブロックを並べるスキル」設定";
    }

    @Override
    protected InventoryType getInventoryType() {
        return null;
    }

    @Override
    protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        BuildSkillManager bsm = gp.getManager(BuildSkillManager.class);

        ItemMeta itemmeta = itemstack.getItemMeta();
        List<String> lore;

        switch (slot) {
            case 0:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "ブロックを並べるスキル:" + bsm.getBlockLineUpStatus());
                lore = new ArrayList<>();
                lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "オフハンドに木の棒、メインハンドに設置したいブロックを持って");
                lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "左クリックすると向いてる方向に並べて設置します。");
                lore.add("" + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "＜クリックで切り替え＞");
                lore.add("" + ChatColor.RESET + ChatColor.GRAY + "建築Lv" + config.getBlockLineUpSkillLevel()
                        + "以上で利用可能");
                itemmeta.setLore(lore);
                break;

            case 1:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "ハーフブロック設定:" + bsm.getHalfblock_modeStatus());
                lore = new ArrayList<>();
                lore.add("" + ChatColor.RESET + ChatColor.GRAY + "ハーフブロックを並べる時の位置を設定します。");
                lore.add("" + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "＜クリックで切り替え＞");
                itemmeta.setLore(lore);
                break;

            case 2:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "破壊設定:" + bsm.getBlockBreakStatus());
                lore = new ArrayList<>();
                lore.add("" + ChatColor.RESET + ChatColor.GRAY + "ブロックを並べる際、対象のブロックを破壊して並べます");
                lore.add("" + ChatColor.RESET + ChatColor.GRAY + "破壊対象ブロック:草,花,水,雪,松明,きのこ");
                lore.add("" + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "＜クリックで切り替え＞");
                itemmeta.setLore(lore);
                break;

            case 8:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "MineStack優先設定:" + bsm.getBlockLineUpMinestackStatus());
                lore = new ArrayList<>();
                lore.add("" + ChatColor.GRAY + "スキルでブロックを並べる際");
                lore.add("" + ChatColor.RESET + ChatColor.GRAY + "MineStackの在庫を優先して消費するか指定します。");
                lore.add("" + ChatColor.RESET + ChatColor.GRAY + "建築Lv" + config.getBlockLineUpSkillMSLevel()
                        + "以上で利用可能");
                itemmeta.setLore(lore);
                break;

            case 9:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "ホームメニューへ");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE
                        + "クリックで移動");
                itemmeta.setLore(lore);
                SkullMeta skullmeta = (SkullMeta) itemmeta;
                skullmeta.setOwner("MHF_ArrowLeft");
                break;
        }
        return itemmeta;
    }

    @Override
    protected ItemStack getItemStack(Player player, int slot) {
        ItemStack itemstack = null;

        switch (slot) {
            case 0:
                itemstack = new ItemStack(Material.WOOD,1,(short)1);
                break;
            case 1:
                itemstack = new ItemStack(Material.STEP);
                break;
            case 2:
                itemstack = new ItemStack(Material.TNT);
                break;
            case 8:
                itemstack = new ItemStack(Material.CHEST);
                break;
            case 9:
                itemstack = new ItemStack(Material.SKULL_ITEM,1,(short)3);
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
        return (float) 0.1;
    }
}
