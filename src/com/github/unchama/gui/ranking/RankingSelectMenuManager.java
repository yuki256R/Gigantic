package com.github.unchama.gui.ranking;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.util.Util;

/**
 * @author tar0ss
 *
 */
public class RankingSelectMenuManager extends GuiMenuManager {

	private final String Total = "（総合）";
	private final String Day = "（日間）";
	private final String Week = "（週間）";
	private final String Month = "（月間）";
	private final String Year = "（年間）";

	private final String MineBlockTitleName = ChatColor.YELLOW + "整地量ランキング";
	private final String BuildTitleName = ChatColor.YELLOW + "建築量ランキング";
	private final String HuntingExpTitleName = ChatColor.YELLOW + "狩猟経験値ランキング";
	private final String FishingExpTitleName = ChatColor.YELLOW + "釣り経験値ランキング";
	private final String LoginTimeTitleName = ChatColor.YELLOW + "接続時間ランキング";

	private final String MineBlockHeadName = "blue_chalice";
	private final String BuildHeadName = "spruce_log";
	private final String HuntingExpHeadName = "Creeper";
	private final String FishingExpHeadName = "sushi_roll";
	private final String LoginTimeHeadName = "light_bulb";

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean invoke(Player player, String identifier) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(0, ManagerType.TOTALMINEBLOCKRANKINGMENU);
		openmap.put(9, ManagerType.DAYMINEBLOCKRANKINGMENU);
		openmap.put(18, ManagerType.WEEKMINEBLOCKRANKINGMENU);
		openmap.put(27, ManagerType.MONTHMINEBLOCKRANKINGMENU);
		openmap.put(36, ManagerType.YEARMINEBLOCKRANKINGMENU);
		openmap.put(1, ManagerType.TOTALBUILDRANKINGMENU);
		openmap.put(10, ManagerType.DAYBUILDRANKINGMENU);
		openmap.put(19, ManagerType.WEEKBUILDRANKINGMENU);
		openmap.put(28, ManagerType.MONTHBUILDRANKINGMENU);
		openmap.put(37, ManagerType.YEARBUILDRANKINGMENU);
		openmap.put(2, ManagerType.TOTALHUNTINGEXPRANKINGMENU);
		openmap.put(11, ManagerType.DAYHUNTINGEXPRANKINGMENU);
		openmap.put(20, ManagerType.WEEKHUNTINGEXPRANKINGMENU);
		openmap.put(29, ManagerType.MONTHHUNTINGEXPRANKINGMENU);
		openmap.put(38, ManagerType.YEARHUNTINGEXPRANKINGMENU);
		openmap.put(3, ManagerType.TOTALFISHINGEXPRANKINGMENU);
		openmap.put(12, ManagerType.DAYFISHINGEXPRANKINGMENU);
		openmap.put(21, ManagerType.WEEKFISHINGEXPRANKINGMENU);
		openmap.put(30, ManagerType.MONTHFISHINGEXPRANKINGMENU);
		openmap.put(39, ManagerType.YEARFISHINGEXPRANKINGMENU);
		openmap.put(4, ManagerType.TOTALLOGINTIMERANKINGMENU);
		openmap.put(13, ManagerType.DAYLOGINTIMERANKINGMENU);
		openmap.put(22, ManagerType.WEEKLOGINTIMERANKINGMENU);
		openmap.put(31, ManagerType.MONTHLOGINTIMERANKINGMENU);
		openmap.put(40, ManagerType.YEARLOGINTIMERANKINGMENU);
	}

	@Override
	protected void setKeyItem() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getClickType() {
		return null;
	}

	@Override
	public int getInventorySize() {
		return 54;
	}

	@Override
	public String getInventoryName(Player player) {
		return "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "各種ランキング一覧";
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
		ItemStack itemstack = null;
		switch (slot) {
		// 整地量ランキング
		case 0:
			itemstack = head.getMobHead(MineBlockHeadName);
			Util.setDisplayName(itemstack, MineBlockTitleName + Total);
			break;
		case 9:
			itemstack = head.getMobHead(MineBlockHeadName);
			Util.setDisplayName(itemstack, MineBlockTitleName + Day);
			break;
		case 18:
			itemstack = head.getMobHead(MineBlockHeadName);
			Util.setDisplayName(itemstack, MineBlockTitleName + Week);
			break;
		case 27:
			itemstack = head.getMobHead(MineBlockHeadName);
			Util.setDisplayName(itemstack, MineBlockTitleName + Month);
			break;
		case 36:
			itemstack = head.getMobHead(MineBlockHeadName);
			Util.setDisplayName(itemstack, MineBlockTitleName + Year);
			break;
		// 建築量ランキング
		case 1:
			itemstack = head.getMobHead(BuildHeadName);
			Util.setDisplayName(itemstack, BuildTitleName + Total);
			break;
		case 10:
			itemstack = head.getMobHead(BuildHeadName);
			Util.setDisplayName(itemstack, BuildTitleName + Day);
			break;
		case 19:
			itemstack = head.getMobHead(BuildHeadName);
			Util.setDisplayName(itemstack, BuildTitleName + Week);
			break;
		case 28:
			itemstack = head.getMobHead(BuildHeadName);
			Util.setDisplayName(itemstack, BuildTitleName + Month);
			break;
		case 37:
			itemstack = head.getMobHead(BuildHeadName);
			Util.setDisplayName(itemstack, BuildTitleName + Year);
			break;
		// 狩猟経験値ランキング
		case 2:
			itemstack = head.getMobHead(HuntingExpHeadName);
			Util.setDisplayName(itemstack, HuntingExpTitleName + Total);
			break;
		case 11:
			itemstack = head.getMobHead(HuntingExpHeadName);
			Util.setDisplayName(itemstack, HuntingExpTitleName + Day);
			break;
		case 20:
			itemstack = head.getMobHead(HuntingExpHeadName);
			Util.setDisplayName(itemstack, HuntingExpTitleName + Week);
			break;
		case 29:
			itemstack = head.getMobHead(HuntingExpHeadName);
			Util.setDisplayName(itemstack, HuntingExpTitleName + Month);
			break;
		case 38:
			itemstack = head.getMobHead(HuntingExpHeadName);
			Util.setDisplayName(itemstack, HuntingExpTitleName + Year);
			break;
		// 釣り経験値ランキング
		case 3:
			itemstack = head.getMobHead(FishingExpHeadName);
			Util.setDisplayName(itemstack, FishingExpTitleName + Total);
			break;
		case 12:
			itemstack = head.getMobHead(FishingExpHeadName);
			Util.setDisplayName(itemstack, FishingExpTitleName + Day);
			break;
		case 21:
			itemstack = head.getMobHead(FishingExpHeadName);
			Util.setDisplayName(itemstack, FishingExpTitleName + Week);
			break;
		case 30:
			itemstack = head.getMobHead(FishingExpHeadName);
			Util.setDisplayName(itemstack, FishingExpTitleName + Month);
			break;
		case 39:
			itemstack = head.getMobHead(FishingExpHeadName);
			Util.setDisplayName(itemstack, FishingExpTitleName + Year);
			break;
		// 接続時間ランキング
		case 4:
			itemstack = head.getMobHead(LoginTimeHeadName);
			Util.setDisplayName(itemstack, LoginTimeTitleName + Total);
			break;
		case 13:
			itemstack = head.getMobHead(LoginTimeHeadName);
			Util.setDisplayName(itemstack, LoginTimeTitleName + Day);
			break;
		case 22:
			itemstack = head.getMobHead(LoginTimeHeadName);
			Util.setDisplayName(itemstack, LoginTimeTitleName + Week);
			break;
		case 31:
			itemstack = head.getMobHead(LoginTimeHeadName);
			Util.setDisplayName(itemstack, LoginTimeTitleName + Month);
			break;
		case 40:
			itemstack = head.getMobHead(LoginTimeHeadName);
			Util.setDisplayName(itemstack, LoginTimeTitleName + Year);
			break;
		default:
			break;
		}
		return itemstack;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_WOODEN_TRAPDOOR_OPEN;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return 0.6F;
	}

}
