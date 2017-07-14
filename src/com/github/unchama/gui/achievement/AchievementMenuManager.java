package com.github.unchama.gui.achievement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.achievement.AchievementCategory;
import com.github.unchama.achievement.AchievementEnum;
import com.github.unchama.achievement.AnotherName;
import com.github.unchama.achievement.AnotherNameParts;
import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.achievement.AchievementManager;
import com.github.unchama.player.gui.GuiStatusManager;
import com.github.unchama.util.Util;

public final class AchievementMenuManager extends GuiMenuManager {

	@Override
	public Inventory getInventory(Player player, int slot) {
		Inventory inv = this.getEmptyInventory(player);
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		AchievementManager aM = gp.getManager(AchievementManager.class);
		GuiStatusManager sM = gp.getManager(GuiStatusManager.class);
		String identifier = (String) sM.getCurrentObject(this);
		Integer c = sM.getCurrentPage(this);
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack is = null;
			List<String> lore = new ArrayList<String>();;
			if (i < 9) {
				switch (i) {
				case 0:
					is = head.getPlayerHead(player.getName());
					Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + player.getName() + "の実績情報");
					lore.add(ChatColor.GREEN + "実績解除状況:" + aM.getUnlockedAchivementNum() + "/"
							+ AchievementEnum.getAchivementNum());
					lore.add(ChatColor.GREEN + "二つ名パーツ獲得状況");
					lore.add(ChatColor.GREEN + "前パーツ：" + aM.getUnlockedAnotherNameNum(AnotherNameParts.TOP) + "/"
							+ AchievementEnum.getAnotherNameNum(AnotherNameParts.TOP));
					lore.add(ChatColor.GREEN + "中パーツ：" + aM.getUnlockedAnotherNameNum(AnotherNameParts.MIDDLE) + "/"
							+ AchievementEnum.getAnotherNameNum(AnotherNameParts.MIDDLE));
					lore.add(ChatColor.GREEN + "後パーツ：" + aM.getUnlockedAnotherNameNum(AnotherNameParts.BOTTOM) + "/"
							+ AchievementEnum.getAnotherNameNum(AnotherNameParts.BOTTOM));
					Util.setLore(is, lore);
					break;
				case 2:
					is = new ItemStack(Material.BOOK_AND_QUILL);
					Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "実績");
					lore.add(ChatColor.GREEN + "実績を確認します．");
					Util.setLore(is, lore);
					break;
				case 4:
					is = new ItemStack(Material.NAME_TAG);
					Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "二つ名");
					lore.add(ChatColor.GREEN + "二つ名の設定・確認を");
					lore.add(ChatColor.GREEN + "行います．");
					Util.setLore(is, lore);
					break;
				}
			}else if(identifier == "achievement"){
				AchievementCategory[] catList = AchievementCategory.getCategorys();
				if(i >= 9 && i < 18){
					is = new ItemStack(Material.STAINED_GLASS,1,(short) (i - 9));
					Util.setDisplayName(is, "" + ChatColor.DARK_AQUA + "【実績】 " + catList[i - 9].getName());
				}else if(c.intValue() < 9){
					AchievementCategory aC = catList[c.intValue()];
					List<GiganticAchievement> aList =  AchievementCategory.getAchivList(aC);
					if(i-18 < aList.size()){
						GiganticAchievement ga = aList.get(i-18);
						AnotherName aN = ga.getAnotherName();
						if(aM.getFlag(ga.getID())){
							is = head.getMobHead("pickel_chalice");
							Util.setDisplayName(is, "" + ChatColor.BLUE + aN.getName());
							lore.add(ChatColor.WHITE + "獲得条件");
							lore.add(ChatColor.WHITE + ga.getUnlockInfo());
							lore.add(ChatColor.GRAY + "前パーツ：" + aN.getTopName());
							lore.add(ChatColor.GRAY + "中パーツ：" + aN.getMiddleName());
							lore.add(ChatColor.GRAY + "後パーツ：" + aN.getBottomName());
							Util.setLore(is, lore);
						}else{
							is = head.getMobHead("n_question");
							Util.setDisplayName(is, "" + ChatColor.BLUE + aN.getName());
							lore.add(ChatColor.WHITE + "獲得条件");
							lore.add(ChatColor.WHITE + ga.getLockInfo());
							Util.setLore(is, lore);
						}
					}
				}
			}else if(identifier == "anothername"){

			}
			inv.setItem(i, is);
		}
		inv.setMaxStackSize(Integer.MAX_VALUE);
		return inv;
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		idmap.put(2,"achievement");
		idmap.put(4,"anothername");
		for(int i = 9 ; i < 18 ; i++){
			idmap.put(i, Integer.toString(i));
		}
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		GuiStatusManager sm = gp.getManager(GuiStatusManager.class);
		switch (identifier) {
		case "achievement":
			sm.setCurrentObject(this, identifier);
			this.update(player);
			return true;
		case "anothername":
			sm.setCurrentObject(this, identifier);
			this.update(player);
			return true;
		case "9":
		case "10":
		case "11":
		case "12":
		case "13":
		case "14":
		case "15":
		case "16":
		case "17":
			int s = Integer.valueOf(identifier);
			String c = (String) sm.getCurrentObject(this);
			switch(c){
			case "achievement":
				sm.setCurrentPage(this, s - 9);
				this.update(player);
				return true;
			case "anothername":
				sm.setCurrentPage(this, s - 9);
				this.update(player);
				return true;
			}
			return true;
		default:
			return false;
		}
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// TODO 自動生成されたメソッド・スタブ

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
		return 9 * 5;
	}

	@Override
	public String getInventoryName(Player player) {
		return  "" + ChatColor.BOLD + ChatColor.UNDERLINE + ChatColor.DARK_AQUA + "実績・二つ名";
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_IRON_TRAPDOOR_OPEN;
	}

	@Override
	public float getVolume() {
		return 1.0F;
	}

	@Override
	public float getPitch() {
		return 1.4F;
	}

}
