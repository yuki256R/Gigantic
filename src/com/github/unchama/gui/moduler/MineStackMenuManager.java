package com.github.unchama.gui.moduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import com.github.unchama.player.menu.PlayerMenuManager;
import com.github.unchama.player.minestack.MineStack;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.util.MobHead;

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
        skullList.add(MobHead.getMobHead("left"));
        skullList.add(MobHead.getMobHead("right"));
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
        	GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        	PlayerMenuManager pm = gp.getManager(PlayerMenuManager.class);
        	ManagerType mt = StackCategory.values()[slot - 47].getManagerType();
        	GuiMenuManager m = (GuiMenuManager)Gigantic.guimenu.getManager(mt.getManagerClass());
        	pm.pop();
        	pm.push(mt);
            player.openInventory(m.getInventory(player, slot));
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
