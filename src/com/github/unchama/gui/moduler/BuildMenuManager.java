package com.github.unchama.gui.moduler;

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

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildLevelManager;
import com.github.unchama.player.build.BuildManager;

public class BuildMenuManager extends GuiMenuManager{

	public BuildMenuManager(){
		setKeyItem();
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		idmap.put(13, "TotalBuildNum=0");
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		BuildManager bm = gp.getManager(BuildManager.class);
		BuildLevelManager blm = gp.getManager(BuildLevelManager.class);
		
		switch(identifier){
			case "TotalBuildNum=0":
				bm.setTotalbuildnum(0);
				blm.calcLevel();
				player.sendMessage(ChatColor.RED + "総建築量を0にセットしました。");
				break;
			
			default:
				return false;
		}
		return true;
	}
	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
	}

	@Override
	protected void setKeyItem() {
		this.keyitem = new KeyItem(Material.STICK);
	}

	@Override
	public String getClickType() {
		return "left";
	}

	@Override
	public int getInventorySize() {
		return 9*4;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.DARK_PURPLE + "Buildメニュー";
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.CHEST;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore;
		
		switch(slot){
		case 0:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + 
					ChatColor.BOLD + gp.name + "の統計データ");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "建築レベル:" + gp.getManager(BuildLevelManager.class).getBuildLevel());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "総建築量:" + gp.getManager(BuildManager.class).getTotalbuildnum());
			itemmeta.setLore(lore);
			SkullMeta skullmeta = (SkullMeta) itemmeta;
			skullmeta.setOwner(gp.name);
			break;
		
		case 13:
			itemmeta.setDisplayName("[DEBUG]総建築量を0にセット");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.GREEN + "総建築量=0");
			lore.add("" + ChatColor.RESET + ChatColor.RED + "※DEBUG用。本番環境での使用厳禁");//TODO:DEBUG
			itemmeta.setLore(lore);
			break;
		}
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack itemstack = null;
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		switch(slot){
		//自身の統計データ
		case 0:
			itemstack = new ItemStack(Material.SKULL_ITEM,1,(short)3);
			break;
			
		//Flyの情報
		case 2:
			itemstack = new ItemStack(Material.COOKED_CHICKEN);
			break;
			
		//Fly1分追加
		case 3:
			itemstack = new ItemStack(Material.FEATHER);
			break;
		
		//Fly5分追加
		case 4:
			itemstack = new ItemStack(Material.FEATHER,5);
			break;
			
		//Fly無制限
		case 5:
			itemstack = new ItemStack(Material.ELYTRA);
			break;
			
		//Fly終了
		case 6:
			itemstack = new ItemStack(Material.CHAINMAIL_BOOTS);
			break;
			
		//範囲設置スキル
		case 18:
			itemstack = new ItemStack(Material.STONE);
			break;
			
		//範囲設置スキル・設定画面
		case 19:
			itemstack = new ItemStack(Material.SKULL_ITEM,1,(short)2);
			break;
			
		//ブロックを並べるスキル
		case 27:
			itemstack = new ItemStack(Material.LOG,1,(short)1);
			break;
		
		case 13:
			itemstack = new ItemStack(Material.BEDROCK,64);
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
