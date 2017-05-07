package com.github.unchama.gui.moduler;

import java.util.ArrayList;
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
import com.github.unchama.player.build.BuildLevelManager;
import com.github.unchama.player.build.CraftType;
import com.github.unchama.player.minestack.MineStack;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.util.MobHead;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.github.unchama.yml.Yml;

public abstract class MinestackBlockCraftMenuManager extends GuiMenuManager {
	
	public Map<Integer,CraftType> craftmap;
	public List<ItemStack> skullList;
	int max_menu_num = 0;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	public MinestackBlockCraftMenuManager() {
		craftmap = new HashMap<>();
		for (CraftType ct : CraftType.values()) {
			max_menu_num = Math.max(ct.getMenunum(),max_menu_num);
			if (ct.getMenunum() == getMenuNum()) {
				craftmap.put(ct.getSlot(), ct);
				id_map.put(ct.getSlot(), String.valueOf(ct.getSlot()));
			}
		}
		
		skullList = new ArrayList<>();
        skullList.add(MobHead.getMobHead("left"));
        skullList.add(MobHead.getMobHead("right"));
        ItemMeta meta = skullList.get(0).getItemMeta();
        meta.setDisplayName("前のページ");
        skullList.get(0).setItemMeta(meta);
        meta = skullList.get(1).getItemMeta();
        meta.setDisplayName("次のページ");
        skullList.get(1).setItemMeta(meta);
	}
	
	//メニュー番号指定用
	public abstract int getMenuNum();
	
	public Inventory getInventory(Player player,int slot,int page) {
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(), this.getInventoryName(player) + "- " + page + "ページ");
		
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		MineStackManager ms = gp.getManager(MineStackManager.class);
		
		//ボタンを並べる
		for (CraftType ct : craftmap.values()) {
			ItemStack menu_icon = ct.getMenuIconItemStack();
			ItemMeta itemmeta = menu_icon.getItemMeta();
			String need_name = ct.getNeed_JPname();
			String produce_name = ct.getProduce_JPname();
			long need_minestack_amount = ms.datamap.get(ct.getNeed_stacktype()).getNum();
			long produce_minestack_amount = ms.datamap.get(ct.getProduce_stacktype()).getNum();
			int need_amount = ct.getNeed_amount();
			int produce_amount = ct.getProduce_amount();
			int menu_slot = ct.getSlot();
			
			itemmeta.setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "" + ChatColor.UNDERLINE
					+ "" + ChatColor.BOLD + need_name + "を" + produce_name + "に変換します");
			
			List<String>lore = new ArrayList<String>();
			
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + need_name + need_amount
					+ "個→" + produce_name + produce_amount + "個");
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + need_name + "の数:" + String.format("%,d",need_minestack_amount));
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + produce_name + "の数:" + String.format("%,d",produce_minestack_amount));
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(ct.getConfig_Num()) + "以上で利用可能");
			lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
			itemmeta.setLore(lore);
			menu_icon.setItemMeta(itemmeta);
			
			inv.setItem(menu_slot, menu_icon);
		}
		
		//進む・戻るボタン
		inv.setItem(45, skullList.get(0));
		inv.setItem(53, skullList.get(1));
		
		return inv;
	}
	
	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		String title = player.getOpenInventory().getTitle();
		int page = Integer.valueOf(title.substring(title.length() - 4, title.length() - 3));
		int slot = Integer.valueOf(identifier);
		
		//空スロットなら終わり
		if (craftmap.get(slot) == null) return false;
		//ページ戻るボタン
		if (slot == 45) {
			if(page <= 1) return false;
			player.openInventory(getInventory(player, 45, page - 1));
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, (float)1.0, (float)4.0);
		}
		//ページ進むボタン
		else if (slot == 53) {
			if (max_menu_num <= page) return false;
			player.openInventory(getInventory(player, 53, page + 1));
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, (float)1.0, (float)4.0);
		}
		//各ボタンの処理
		else if (slot < 45) {
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
			CraftType ct = craftmap.get(slot);
			MineStack need_minestack = gp.getManager(MineStackManager.class).datamap.get(ct.getNeed_stacktype());
			MineStack produce_minestack = gp.getManager(MineStackManager.class).datamap.get(ct.getProduce_stacktype());
			long check_amount = need_minestack.getNum();
			int need_buildlevel = config.getBlockCraftLevel(ct.getConfig_Num());
			
			//建築レベルが足りているかを確認
			if (!(need_buildlevel <= gp.getManager(BuildLevelManager.class).getBuildLevel())) {
				player.sendMessage(ChatColor.RED + "建築レベルが不足しています。必要建築レベル:" + need_buildlevel + "Lv");
				return false;
			}
			
			//MineStack内の量が必要数以上あるか確認
			if (!(ct.getNeed_amount() <= check_amount)) {
				player.sendMessage(ChatColor.RED + "アイテムが不足しています。必要数:" + ct.getNeed_amount());
				return false;
			}
					
			//あった場合は変換を開始する
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, (float)1.0, (float)3.0);
			
			need_minestack.add(-ct.getNeed_amount());
			produce_minestack.add(ct.getProduce_amount());
			debug.sendMessage(player,DebugEnum.BUILD, "変換終了");
			
			//ボタンの更新処理
			ItemStack button = player.getOpenInventory().getItem(slot);
			ItemMeta buttonmeta = button.getItemMeta();
			
			MineStackManager ms = gp.getManager(MineStackManager.class);
			String need_name = ct.getNeed_JPname();
			String produce_name = ct.getProduce_JPname();
			long need_minestack_amount = ms.datamap.get(ct.getNeed_stacktype()).getNum();
			long produce_minestack_amount = ms.datamap.get(ct.getProduce_stacktype()).getNum();
			int need_amount = ct.getNeed_amount();
			int produce_amount = ct.getProduce_amount();
			
			List<String>lore = new ArrayList<String>();
			
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + need_name + need_amount
					+ "個→" + produce_name + produce_amount + "個");
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + need_name + "の数:" + String.format("%,d",need_minestack_amount));
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + produce_name + "の数:" + String.format("%,d",produce_minestack_amount));
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(ct.getConfig_Num()) + "以上で利用可能");
			lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
			
			buttonmeta.setLore(lore);
			button.setItemMeta(buttonmeta);
			
		}
		else {
			return false;
		}
		return true;
	}

	@Override
	public Inventory getInventory(Player player, int slot){
		return getInventory(player, slot, this.getMenuNum());
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
	public int getInventorySize() {
		return 9*6;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.DARK_PURPLE + "MineStack一括クラフトシステム";
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
		return Sound.BLOCK_FENCE_GATE_OPEN;
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
