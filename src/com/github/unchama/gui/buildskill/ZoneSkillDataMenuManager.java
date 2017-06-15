package com.github.unchama.gui.buildskill;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.buildskill.BuildSkillManager;
import com.github.unchama.yml.ConfigManager;
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
public class ZoneSkillDataMenuManager extends GuiMenuManager{

    @Override
    protected void setIDMap(HashMap<Integer, String> idmap) {
        idmap.put(4, "ZSDirt");
        idmap.put(13, "ZSSkill");
        idmap.put(19, "ZSAreaMax");
        idmap.put(20, "ZSAreaInc");
        idmap.put(22, "ZSAreaDef");
        idmap.put(24, "ZSAreaDec");
        idmap.put(25, "ZSAreaMin");
        idmap.put(35, "ZSMinestack");
    }

    @Override
    public boolean invoke(Player player, String identifier) {
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        BuildSkillManager bsm = gp.getManager(BuildSkillManager.class);

        switch (identifier) {
            case "ZSDirt":
                bsm.toggle_ZoneSkillDirt();
                break;
            case "ZSSkill":
                bsm.toggle_ZoneSkill();
                break;
            case "ZSAreaMax":
                bsm.setAREAint(5);
                break;
            case "ZSAreaInc":
                bsm.incAREAint(1);
                break;
            case "ZSAreaDef":
                bsm.setAREAint(2);
                break;
            case "ZSAreaDec":
                bsm.incAREAint(-1);
                break;
            case "ZSAreaMin":
                bsm.setAREAint(1);
                break;
            case "ZSMinestack":
                bsm.toggle_ZoneSkillMineStack();
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
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        BuildSkillManager buildSkillManager = gp.getManager(BuildSkillManager.class);
        ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

        String ZSSkill = buildSkillManager.getZoneSkillStatus();
        String ZSDirt = buildSkillManager.getZoneDirtStatus();
        int ZSSkillA = buildSkillManager.getAREAint() * 2 + 1;
        String ZSSkillM = buildSkillManager.getZoneMinestackStatus();

        ItemMeta itemmeta = itemstack.getItemMeta();
        List<String> lore;

        switch(slot) {
            case 0:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "メインページへ");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで移動");
                itemmeta.setLore(lore);
                break;

            case 4:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "設置時に下の空洞を埋める機能");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "機能の使用設定:"
                        + ZSDirt);
                lore.add("" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "機能の範囲:地下5マスまで");
                itemmeta.setLore(lore);
                break;

            case 13:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "現在の設定は以下の通りです");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.RESET + "" +  ChatColor.AQUA + "" + ChatColor.UNDERLINE
                        + "スキルの使用設定:" + ZSSkill);
                lore.add("" + ChatColor.RESET + "" +  ChatColor.AQUA + "" + ChatColor.UNDERLINE
                        + "スキルの範囲設定:" + ZSSkillA + "×" + ZSSkillA);
                itemmeta.setLore(lore);
                break;

            case 19:
                itemmeta.setDisplayName(ChatColor.RED + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "範囲設定を最大値に変更");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.AQUA + "現在の範囲設定:" + ZSSkillA + "×" + ZSSkillA);
                lore.add("" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "変更後の範囲設定:11×11");
                itemmeta.setLore(lore);
                SkullMeta skullmeta = (SkullMeta) itemmeta;
                skullmeta.setOwner("MHF_ArrowUp");
                break;

            case 20:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "範囲設定を一段階大きくする");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.AQUA + "現在の範囲設定:" + ZSSkillA + "×" + ZSSkillA);
                if (ZSSkillA >= 11) {
                    lore.add("" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "変更後の範囲設定:"
                            + ZSSkillA + "×" + ZSSkillA);
                } else {
                    lore.add("" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "変更後の範囲設定:"
                            + (ZSSkillA + 2) + "×" + (ZSSkillA + 2));
                }
                lore.add("" + ChatColor.RESET + "" +  ChatColor.RED + "" + "※範囲設定の最大値は11×11※");
                itemmeta.setLore(lore);
                SkullMeta skullmeta1 = (SkullMeta) itemmeta;
                skullmeta1.setOwner("MHF_ArrowUp");
                break;

            case 22:
                itemmeta.setDisplayName(ChatColor.RED + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "範囲設定を初期値に変更");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.AQUA + "現在の範囲設定:" + ZSSkillA + "×" + ZSSkillA);
                lore.add("" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "変更後の範囲設定:5×5");
                itemmeta.setLore(lore);
                SkullMeta skullmeta2 = (SkullMeta) itemmeta;
                skullmeta2.setOwner("MHF_TNT");
                break;

            case 24:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "範囲設定を一段階小さくする");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.AQUA + "現在の範囲設定:" + ZSSkillA + "×" + ZSSkillA);
                if (ZSSkillA <= 3) {
                    lore.add("" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "変更後の範囲設定:"
                            + ZSSkillA + "×" + ZSSkillA);
                } else {
                    lore.add("" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "変更後の範囲設定:"
                            + (ZSSkillA - 2) + "×" + (ZSSkillA - 2));
                }
                lore.add("" + ChatColor.RESET + "" +  ChatColor.RED + "" + "※範囲設定の最小値は3×3※");
                itemmeta.setLore(lore);
                SkullMeta skullmeta3 = (SkullMeta) itemmeta;
                skullmeta3.setOwner("MHF_ArrowDown");
                break;

            case 25:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "範囲設定を最小値に変更");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.AQUA + "現在の範囲設定:" + ZSSkillA + "×" + ZSSkillA);
                lore.add("" + ChatColor.AQUA + "" + ChatColor.UNDERLINE + "変更後の範囲設定:3×3");
                itemmeta.setLore(lore);
                SkullMeta skullmeta4 = (SkullMeta) itemmeta;
                skullmeta4.setOwner("MHF_ArrowDown");
                break;

            case 35:
                itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                        + "MineStack優先設定");
                lore = new ArrayList<>();
                lore.add("" + ChatColor.YELLOW + "範囲設置時にMineStackからアイテムを優先して");
                lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "取り出すかどうかを設定します。");
                lore.add("" + ChatColor.RESET + ChatColor.AQUA + "現在の設定:" + ZSSkillM);
                lore.add("" + ChatColor.RESET + ChatColor.GRAY + "建築Lv" + config.getZoneSetSkillMinestack()
                        + "以上で使用可能");
                itemmeta.setLore(lore);
                break;
        }
        return itemmeta;
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
