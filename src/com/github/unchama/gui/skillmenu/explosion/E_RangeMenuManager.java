package com.github.unchama.gui.skillmenu.explosion;



import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.skill.ExplosionManager;

public class E_RangeMenuManager extends GuiMenuManager{

	@Override
	protected void setOpenMenuMap(HashMap<Integer, Class<? extends GuiMenuManager>> openmap) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	protected void setIDMap(HashMap<Integer, String> methodmap) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public boolean invoke(Player player, String identifier) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
	@Override
	protected void setKeyItem() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getClickType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getInventorySize() {
		return 9;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.LIGHT_PURPLE + "範囲設定";
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.DISPENSER;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		switch(slot){
		case 1:
			itemmeta.setDisplayName(ChatColor.GREEN + "高さを1上げる");
			break;
		case 2:
			itemmeta.setDisplayName(ChatColor.GREEN + "奥行を1伸ばす");
			break;
		case 3:
			itemmeta.setDisplayName(ChatColor.GREEN + "幅を1狭くする");
			break;
		case 4:
			itemmeta.setDisplayName(ChatColor.GREEN + "範囲設定");
			break;
		case 5:
			itemmeta.setDisplayName(ChatColor.GREEN + "幅を1広げる");
			break;
		case 6:
			itemmeta.setDisplayName(ChatColor.GREEN + "奥行を1縮める");
			break;
		case 7:
			itemmeta.setDisplayName(ChatColor.GREEN + "高さを１下げる");
			break;
		case 8:
			itemmeta.setDisplayName(ChatColor.RED + "初期設定に戻す");
			break;
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ItemStack itemstack = null;
		switch(slot){
		case 1:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14);

			break;
		case 2:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)1);
			break;
		case 3:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)10);
			break;
		case 4:
			itemstack = new ItemStack(gp.getManager(ExplosionManager.class).getMenuMaterial());
			break;
		case 5:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)5);
			break;
		case 6:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)11);
			break;
		case 7:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)13);
			break;
		case 8:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)0);
			break;
		}
		return itemstack;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_ANVIL_PLACE;
	}

	@Override
	public float getVolume() {
		return (float)0.8;
	}

	@Override
	public float getPitch() {
		return (float)0.6;
	}

}
