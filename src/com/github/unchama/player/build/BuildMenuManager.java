package com.github.unchama.player.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.gui.moduler.KeyItem;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fly.FlyManager;
import com.github.unchama.yml.ConfigManager;

public class BuildMenuManager extends GuiMenuManager{

	public BuildMenuManager(){
		setKeyItem();
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		idmap.put(3, "FLY=1");
		idmap.put(4, "FLY=5");
		idmap.put(5, "FLY=endless");
		idmap.put(6, "FLY=fin");
		
		idmap.put(13, "TotalBuildNum=0");
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		BuildManager bm = gp.getManager(BuildManager.class);
		BuildLevelManager blm = gp.getManager(BuildLevelManager.class);
		
		switch(identifier){
			//FLY1分
			case "FLY=1":
				player.chat("/fly 1");
				break;
				
			//FLY5分
			case "FLY=5":
				player.chat("/fly 5");
				break;
				
			//FLY無制限
			case "FLY=endless":
				player.chat("/fly endless");
				break;
				
			//FLY終了
			case "FLY=fin":
				player.chat("/fly finish");
				break;
				
			//TODO:スキルについては後で
				
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
		ManagerType mt = GuiMenu.ManagerType.BLOCKCRAFTMENU1;
		openmap.put(35, mt);
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
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		
		BuildManager bm = gp.getManager(BuildManager.class);
		BuildLevelManager blm = gp.getManager(BuildLevelManager.class);
		FlyManager fm = gp.getManager(FlyManager.class);
		ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore;

		switch(slot){
		//自身の統計データ
		case 0:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + 
					ChatColor.BOLD + gp.name + "の統計データ");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "建築レベル:" + blm.getBuildLevel());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "総建築量:" + bm.getTotalbuildnum());
			itemmeta.setLore(lore);
			SkullMeta skullmeta = (SkullMeta) itemmeta;
			skullmeta.setOwner(gp.name);
			break;
		
		//Flyの情報
		case 2:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" +
					ChatColor.BOLD + "FLY機能 情報表示");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "FLY 効果:" + fm.getFlyState());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "FLY 残り時間:" + fm.getFlyTimeState());
			itemmeta.setLore(lore);
			break;
			
		//Fly1分
		case 3:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能 ON" + 
					ChatColor.AQUA + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "(1分)");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" +  ChatColor.YELLOW + "クリックすると以降1分間に渡り");
			lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "経験値を消費しつつFLYが可能になります。");
			lore.add("" + ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE +
					"必要経験値量:毎分 " + config.getFlyExp());
			itemmeta.setLore(lore);
			break;
			
		//Fly5分
		case 4:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能 ON" + 
					ChatColor.GREEN + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "(5分)");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" +  ChatColor.YELLOW + "クリックすると以降5分間に渡り");
			lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "経験値を消費しつつFLYが可能になります。");
			lore.add("" + ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE +
					"必要経験値量:毎分 " + config.getFlyExp());
			itemmeta.setLore(lore);
			break;
			
		//Fly無制限
		case 5:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能 ON" + 
					ChatColor.RED + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "(無制限)");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" +  ChatColor.YELLOW + "クリックすると以降OFFにするまで");
			lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "経験値を消費しつつFLYが可能になります。");
			lore.add("" + ChatColor.RESET + "" +  ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE +
					"必要経験値量:毎分 " + config.getFlyExp());
			itemmeta.setLore(lore);
			break;
			
		//Fly終了
		case 6:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能 OFF");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" +  ChatColor.RED + "クリックすると残り時間にかかわらず");
			lore.add("" + ChatColor.RESET + "" + ChatColor.RED + "FLYを終了します。");
			itemmeta.setLore(lore);
			itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			break;
			
		//TODO:スキル系の作成は後で
			
		//MineStack一括クラフトシステム
		case 35:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" 
					+ ChatColor.BOLD + "MineStackブロック一括クラフト画面へ");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで移動");
			itemmeta.setLore(lore);
			break;
			
		//DEBUG
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
			itemstack = new ItemStack(Material.WOOD,1,(short)1);
			break;
		
		//ブロックを並べるスキル・設定画面
		case 28:
			itemstack = new ItemStack(Material.PAPER);
			break;
			
		//MineStack一括クラフト
		case 35:
			itemstack = new ItemStack(Material.WORKBENCH);
			break;
			
		//DEBUG
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
