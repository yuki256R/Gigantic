package com.github.unchama.gui.moduler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.minestack.MineStack;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.minestack.StackType;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

/**
 * Created by Mon_chi on 2017/03/25.
 */
public abstract class MineStackMenuManager extends GuiMenuManager{

    public Map<Integer, StackType> typeMap;
    public List<ItemStack> skullList;

    public MineStackMenuManager(){
        typeMap = new HashMap<>();
        int count = 0;
        for (StackType st : StackType.values()) {
            if (st.getCategory() == getCategory()){
                typeMap.put(count, st);
                count += 1;
            }
        }
        for (int i = 0; i < 54; i++) {
            id_map.put(i, String.valueOf(i));
        }

        skullList = new ArrayList<>();
        skullList.add(getSkull("http://textures.minecraft.net/texture/3ebf907494a935e955bfcadab81beafb90fb9be49c7026ba97d798d5f1a23"));
        skullList.add(getSkull("http://textures.minecraft.net/texture/1b6f1a25b6bc199946472aedb370522584ff6f4e83221e5946bd2e41b5ca13b"));

//        1-9の数字頭
//        skullList.add(getSkull("http://textures.minecraft.net/texture/71bc2bcfb2bd3759e6b1e86fc7a79585e1127dd357fc202893f9de241bc9e530"));
//        skullList.add(getSkull("http://textures.minecraft.net/texture/4cd9eeee883468881d83848a46bf3012485c23f75753b8fbe8487341419847"));
//        skullList.add(getSkull("http://textures.minecraft.net/texture/1d4eae13933860a6df5e8e955693b95a8c3b15c36b8b587532ac0996bc37e5"));
//        skullList.add(getSkull("http://textures.minecraft.net/texture/d2e78fb22424232dc27b81fbcb47fd24c1acf76098753f2d9c28598287db5"));
//        skullList.add(getSkull("http://textures.minecraft.net/texture/6d57e3bc88a65730e31a14e3f41e038a5ecf0891a6c243643b8e5476ae2"));
//        skullList.add(getSkull("http://textures.minecraft.net/texture/334b36de7d679b8bbc725499adaef24dc518f5ae23e716981e1dcc6b2720ab"));
//        skullList.add(getSkull("http://textures.minecraft.net/texture/6db6eb25d1faabe30cf444dc633b5832475e38096b7e2402a3ec476dd7b9"));
//        skullList.add(getSkull("http://textures.minecraft.net/texture/59194973a3f17bda9978ed6273383997222774b454386c8319c04f1f4f74c2b5"));
//        skullList.add(getSkull("http://textures.minecraft.net/texture/e67caf7591b38e125a8017d58cfc6433bfaf84cd499d794f41d10bff2e5b840"));





    }

    public abstract StackCategory getCategory();

    public Inventory getInventory(Player player, int slot, int page){
        Inventory inv = Bukkit.getServer().createInventory(player,
                this.getInventorySize(), this.getInventoryName(player) + "- " + page + "ページ");

        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        MineStackManager manager = gp.getManager(MineStackManager.class);

        //とりだしボタン
        for (int i = 45*(page-1); i < 45*page; i++) {
            if (i >= typeMap.size()) break;
            StackType stackType = typeMap.get(i);
            long amount = manager.datamap.get(stackType).getNum();
            ItemStack itemStack = stackType.getItemStack();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GREEN + amount +"個"
                    , ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで1スタック取り出し"));
            itemStack.setItemMeta(itemMeta);
            inv.setItem(i-45*(page-1), itemStack);
        }

        //ページ遷移ボタン
        inv.setItem(45, skullList.get(0));
        inv.setItem(53, skullList.get(1));

        //カテゴリ選択ボタン
        StackCategory[] categories = StackCategory.values();
        for (int i = 0; i < categories.length; i++) {
            inv.setItem(47+i, categories[i].getMenuIcon());
        }
        return inv;
    }

    //カスタム頭取得
    private ItemStack getSkull(String url){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(short)3);
        ItemMeta meta = skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        skull.setItemMeta(meta);
        return skull;
    }

    @Override
    public boolean invoke(Player player, String identifier){
        String title = player.getOpenInventory().getTitle();
        //titleからページ判別
        int page = Integer.valueOf(title.substring(title.length() - 4, title.length() - 3));
        int slot = Integer.valueOf(identifier);
        //ページ戻るボタン
        if (slot == 45){
            if (page <= 1) return false;
            player.openInventory(getInventory(player, 45, page - 1));
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, (float)1.0, (float)4.0);
        }
        //ページ進むボタン
        else if (slot == 53){
            if (typeMap.size() <= 53 * page) return false;
            player.openInventory(getInventory(player, 53, page + 1));
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, (float)1.0, (float)4.0);
        }
        //カテゴリ選択ボタン
        else if (slot > 46 && slot < 52){
            player.openInventory(Gigantic.guimenu.getManager(StackCategory.values()[slot - 47].getManagerType().getManagerClass()).getInventory(player, slot));
            player.playSound(player.getLocation(), this.getSoundName(), this.getVolume(), this.getPitch());
        }
        //とりだしボタン
        else if (slot < 45){
            //空スロットならおわり
            if (typeMap.size() <= slot + 45*(page-1))
                return false;

            GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
            StackType stackType = typeMap.get(slot + 45*(page-1));
            MineStack mineStack = gp.getManager(MineStackManager.class).datamap.get(stackType);
            ItemStack itemStack = stackType.getItemStack();
            long stackAmount = mineStack.getNum();
            int maxStackAmount = stackType.getMaxStackAmount();
            int giveAmount;

            //MineStack内の量が1スタック未満か確認
            if (stackAmount == 0)
                return false;
            else if (stackAmount < maxStackAmount)
                giveAmount = (int)stackAmount;
            else
                giveAmount = maxStackAmount;

            itemStack.setAmount(giveAmount);

            //インベントリ満杯か確認
            if (player.getInventory().firstEmpty() == -1){
            	player.sendMessage(ChatColor.RED + "インベントリを空けてください．");
                return false;
            }else{
                player.getInventory().addItem(itemStack);
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, (float)1.0, (float)3.0);
            }

            mineStack.add(-giveAmount);

            //とりだしボタンの個数更新
            ItemStack button = player.getOpenInventory().getItem(slot);
            ItemMeta buttonMeta = button.getItemMeta();
            buttonMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GREEN + mineStack.getNum() +"個"
                    , ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで1スタック取り出し"));
            button.setItemMeta(buttonMeta);
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public Inventory getInventory(Player player, int slot){
        return getInventory(player, slot, 1);
    }

    @Override
    public int getInventorySize() {
        return 9*6;
    }

    @Override
    public String getInventoryName(Player player) {
        return getCategory().getName();
    }


    @Override
    protected void setIDMap(HashMap<Integer, String> idmap) {

    }

    @Override
    protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {

    }

    @Override
    protected void setKeyItem() {

    }

    @Override
    public String getClickType() {
        return null;
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
