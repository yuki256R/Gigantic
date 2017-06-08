package com.github.unchama.gui.donate;

import com.github.unchama.donate.DonateData;
import com.github.unchama.donate.DonateDataManager;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mon_chi on 2017/06/08.
 */
public class DonateHistoryMenuManager extends GuiMenuManager {

    @Override
    public String getInventoryName(Player player) {
        return "寄付履歴";
    }

    @Override
    public Inventory getInventory(Player player, int slot){
        Inventory inv = this.getEmptyInventory(player);
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        List<DonateData> dataMap = gp.getManager(DonateDataManager.class).getDonateList();

        int count = 0;
        for (DonateData data : dataMap) {
            inv.setItem(count, getDonateItemStack(data));
            count += 1;
        }
        return inv;
    }

    private ItemStack getDonateItemStack(DonateData data) {
        Material material;
        if (data.money <= 3000)
            material = Material.IRON_INGOT;
        else if (data.money <= 10000)
            material = Material.GOLD_INGOT;
        else if (data.money <= 50000)
            material = Material.DIAMOND;
        else
            material = Material.EMERALD;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        String name = ChatColor.GOLD + data.time.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "金額: " + data.money);
        lore.add(ChatColor.YELLOW + "GP: " + data.point);

        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    protected ItemStack getItemStack(Player player, int slot) {
        return null;
    }

    @Override
    protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
        return null;
    }

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
        return 9 * 6;
    }

    @Override
    protected InventoryType getInventoryType() {
        return null;
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
        return (float)0.5;
    }
}
