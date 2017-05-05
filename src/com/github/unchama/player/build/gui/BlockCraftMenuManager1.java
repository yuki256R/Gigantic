package com.github.unchama.player.build.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.util.MobHead;
import com.github.unchama.yml.ConfigManager;

public class BlockCraftMenuManager1 extends GuiMenuManager{
	
	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		for(int i = 0; i < 5; i++) {
			idmap.put(i, String.valueOf(i));//石ハーフ用
			idmap.put(i+9, String.valueOf(i+9));//石レンガブロック用
		}
		
		for(int i = 0; i < 4; i++) {
			idmap.put(i+18, String.valueOf(i+18));//磨かれた花崗岩用
			idmap.put(i+23, String.valueOf(i+23));//磨かれた閃緑岩用
			idmap.put(i+27, String.valueOf(i+27));//磨かれた安山岩用
			idmap.put(i+32, String.valueOf(i+32));//ネザー水晶ブロック用
			idmap.put(i+36, String.valueOf(i+36));//レンガブロック用
			idmap.put(i+41, String.valueOf(i+41));//ネザーレンガブロック用
		}
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		
		
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(45, GuiMenu.ManagerType.BUILDMENU);
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
		return ChatColor.DARK_PURPLE + "MineStackブロック一括クラフト1";
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		MineStackManager ms = gp.getManager(MineStackManager.class);
		ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore;
		
		//石を石ハーフに変換10~10万
		long stone = ms.datamap.get(StackType.STONE).getNum();
		long step = ms.datamap.get(StackType.STEP).getNum();
		
		for(int i = 0; i < 5; i++) {
			if(slot == i) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
						+ "石を石ハーフブロックに変換します");
				lore = new ArrayList<String>();
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "石" + (int)Math.pow(10, i+1) + "個→石ハーフブロック" + ((int)Math.pow(10, i+1)*2) + "個");
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "石の数:" + String.format("%,d",stone));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "石ハーフブロックの数:" + String.format("%,d",step));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(1) + "以上で利用可能");
				lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
				itemmeta.setLore(lore);
				continue;
			}
		}
		
		//石を石レンガに変換10~10万
		long  smooth_brick = ms.datamap.get(StackType.SMOOTH_BRICK).getNum();
		
		for(int i = 0; i < 5; i++) {
			if(slot == i+9) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
						+ "石を石レンガに変換します");
				lore = new ArrayList<String>();
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "石" + (int)Math.pow(10, i+1) + "個→石レンガ" + ((int)Math.pow(10, i+1)) + "個");
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "石の数:" + String.format("%,d",stone));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "石レンガの数:" + String.format("%,d",smooth_brick));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(1) + "以上で利用可能");
				lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
				itemmeta.setLore(lore);
				continue;
			}
		}
		
		//花崗岩を花崗岩に変換10~1万
		long granite = ms.datamap.get(StackType.GRANITE).getNum();
		long polished_granite = ms.datamap.get(StackType.POLISHED_GRANITE).getNum();
				
		for(int i = 0; i < 4; i++) {
			if(slot == i+18) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
						+ "花崗岩を磨かれた花崗岩に変換します");
				lore = new ArrayList<String>();
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "花崗岩" + (int)Math.pow(10, i+1) + "個→磨かれた花崗岩" + ((int)Math.pow(10, i+1)) + "個");
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "花崗岩の数:" + String.format("%,d",granite));	
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "磨かれた花崗岩の数:" + String.format("%,d",polished_granite));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(2) + "以上で利用可能");
				lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
				itemmeta.setLore(lore);
				continue;
			}
		}
		
		//閃緑岩を磨かれた閃緑岩に変換10~1万
		long diorite = ms.datamap.get(StackType.DIORITE).getNum();
		long polished_diorite = ms.datamap.get(StackType.POLISHED_DIORITE).getNum();
				
		for(int i = 0; i < 4; i++) {
			if(slot == i+23) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
						+ "閃緑岩を磨かれた閃緑岩に変換します");
				lore = new ArrayList<String>();
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "閃緑岩" + (int)Math.pow(10, i+1) + "個→磨かれた閃緑岩" + ((int)Math.pow(10, i+1)) + "個");
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "閃緑岩の数:" + String.format("%,d",diorite));	
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "磨かれた閃緑岩の数:" + String.format("%,d",polished_diorite));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(2) + "以上で利用可能");
				lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
				itemmeta.setLore(lore);
				continue;
			}
		}
		
		//安山岩を磨かれた安山岩に変換10~1万
		long andesite = ms.datamap.get(StackType.ANDESITE).getNum();
		long polished_andesite = ms.datamap.get(StackType.POLISHED_ANDESITE).getNum();
				
		for(int i = 0; i < 4; i++) {
			if(slot == i+27) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
						+ "安山岩を磨かれた安山岩に変換します");
				lore = new ArrayList<String>();
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "安山岩" + (int)Math.pow(10, i+1) + "個→磨かれた安山岩" + ((int)Math.pow(10, i+1)) + "個");
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "安山岩の数:" + String.format("%,d",andesite));	
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "磨かれた安山岩の数:" + String.format("%,d",polished_andesite));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(2) + "以上で利用可能");
				lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
				itemmeta.setLore(lore);
				continue;
			}
		}
		
		//ネザー水晶を磨かれた安山岩に変換10~1万
		long quartz = ms.datamap.get(StackType.QUARTZ).getNum();
		long quartz_block = ms.datamap.get(StackType.QUARTZ_BLOCK).getNum();
				
		for(int i = 0; i < 4; i++) {
			if(slot == i+32) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
						+ "クォーツ水晶をクォーツ水晶ブロックに変換します");
				lore = new ArrayList<String>();
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ("クォーツ水晶" + (int)Math.pow(10, i+1) + "個→クォーツ水晶ブロック" + ((int)Math.pow(10, i+1)) + "個"));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "クォーツ水晶の数:" + String.format("%,d",quartz));	
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "クォーツ水晶ブロックの数:" + String.format("%,d",quartz_block));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(2) + "以上で利用可能");
				lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
				itemmeta.setLore(lore);
				continue;
			}
		}
		
		//レンガをレンガブロックに変換40~4万
		long brick = ms.datamap.get(StackType.BRICK).getNum();
		long brick_block = ms.datamap.get(StackType.CLAY_BRICK).getNum();
				
		for(int i = 0; i < 4; i++) {
			if(slot == i+36) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
						+ "レンガをレンガブロックに変換します");
				lore = new ArrayList<String>();
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ("レンガ" + (int)Math.pow(10, i+1) + "個→レンガブロック" + ((int)Math.pow(10, i+1)*4) + "個"));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "レンガの数:" + String.format("%,d",brick));	
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "レンガブロックの数:" + String.format("%,d",brick_block));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(2) + "以上で利用可能");
				lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
				itemmeta.setLore(lore);
				continue;
			}
		}
		
		//ネザーレンガをネザーレンガブロックに変換40~4万
		long nether_brick = ms.datamap.get(StackType.NETHER_BRICK_ITEM).getNum();
		long nether_brick_block = ms.datamap.get(StackType.NETHER_BRICK).getNum();
				
		for(int i = 0; i < 4; i++) {
			if(slot == i+41) {
				itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
						+ "ネザーレンガをネザーレンガブロックに変換します");
				lore = new ArrayList<String>();
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ("ネザーレンガ" + (int)Math.pow(10, i+1) + "個→ネザーレンガブロック" + ((int)Math.pow(10, i+1)*4) + "個"));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "レンガの数:" + String.format("%,d",nether_brick));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "レンガブロックの数:" + String.format("%,d",nether_brick_block));
				lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "建築レベル" + config.getBlockCraftLevel(2) + "以上で利用可能");
				lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで変換");
				itemmeta.setLore(lore);
				continue;
			}
		}
		
		//メインメニューに戻る
		if(slot == 45) {
			SkullMeta skullmeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
			skullmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
					+ "ホームへ");
			lore = new ArrayList<String>();
			lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで移動");
			skullmeta.setLore(lore);
			skullmeta.setOwner("MHF_ArrowLeft");
			itemmeta = (ItemMeta) skullmeta;
		}
		
		//次のページへ移動する
		if(slot == 53) {
			SkullMeta skullmeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
			skullmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + ChatColor.BOLD + "2ページ目へ");
			lore = new ArrayList<String>();
			lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで移動");
			skullmeta.setLore(lore);
			skullmeta.setOwner("MHF_ArrowRight");
			itemmeta = (ItemMeta) skullmeta;
		}
		
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack itemstack = null;
		
		for(int i = 0; i < 5; i++) {
			if(slot == i) {//スロット0~4に石ハーフを
				itemstack = new ItemStack(Material.STEP,i+1);
				continue;
			}
		}
		
		for(int i = 0; i < 5; i++) {
			if(slot == i+9) {//スロット9~13に石レンガを
				itemstack = new ItemStack(Material.SMOOTH_BRICK,i+1);
				continue;
			}
		}
		
		for(int i = 0; i < 4; i++) {
			if(slot == i+18) {//スロット18~21に花崗岩を
				itemstack = new ItemStack(Material.STONE,i+1,(short)2);
				continue;
			}
		}
		
		for(int i = 0; i < 4; i++) {
			if(slot == i+23) {//スロット23~26に閃緑岩を
				itemstack = new ItemStack(Material.STONE,i+1,(short)4);
				continue;
			}
		}
		
		for(int i = 0; i < 4; i++){ 
			if(slot == i+27) {//スロット27~30に安山岩を
				itemstack = new ItemStack(Material.STONE,i+1,(short)6);
				continue;
			}
		}
		
		for(int i = 0; i < 4; i++) {
			if(slot == i+32) {//スロット32~35にネザー水晶ブロックを
				itemstack = new ItemStack(Material.QUARTZ_BLOCK,i+1);
				continue;
			}
		}
		
		for(int i = 0; i < 4; i++) {
			if(slot == i+36) {//スロット36~39にレンガブロックを
				itemstack = new ItemStack(Material.BRICK,i+1);
				continue;
			}
		}
		
		for(int i = 0; i < 4; i++) {
			if(slot == i+41) {
				itemstack = new ItemStack(Material.NETHER_BRICK,i+3);
				continue;
			}
		}
		
		if(slot == 45 || slot == 53) {
			//スロット45にメインメニューに戻るボタン,スロット53に次ページに移動ボタン
			itemstack = new ItemStack(Material.SKULL_ITEM,1,(short)3);
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
		return (float)0.5;
	}

}
