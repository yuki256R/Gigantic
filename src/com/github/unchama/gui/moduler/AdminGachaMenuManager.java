package com.github.unchama.gui.moduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.util.Util;

public abstract class AdminGachaMenuManager extends AdminMenuManager {
	public static enum MenuType {
		INFO(0), GIVE(1), LIST(2), EDIT(3), MENTE(4), SAVE(5), RELOAD(6), DEMO(
				7);

		private int slot;

		private static HashMap<Integer, MenuType> slotmap;

		static {
			slotmap = new HashMap<Integer, MenuType>();
			for (MenuType m : values()) {
				slotmap.put(m.getSlot(), m);
			}
		}

		MenuType(int slot) {
			this.slot = slot;
		}

		public int getSlot() {
			return slot;
		}

		public static MenuType getType(int slot) {
			return slotmap.get(slot);
		}
	}

	Class<? extends GachaManager> gachaClass;
	GachaManager gm;

	public AdminGachaMenuManager() {
		super();
		gachaClass = GachaType.getManagerClassbyMenu(this.getClass());
		gm = gacha.getManager(gachaClass);
	}

	@Override
	public int getInventorySize() {
		return 9;
	}

	@Override
	public String getInventoryName(Player player) {
		return gm.getGachaName();
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		for(MenuType mt : MenuType.values()){
			idmap.put(mt.getSlot(), mt.name());
		}
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		MenuType mt = MenuType.valueOf(identifier);

		if(mt == null){
			return false;
		}

		switch(mt){
		case INFO:
			break;
		case GIVE:
			ItemStack gacha = gm.getGachaTicket();
			if (!Util.isPlayerInventryFill(player)) {
				Util.addItem(player, gacha);
				player.playSound(player.getLocation(),
						Sound.ENTITY_ITEM_PICKUP, (float) 0.1, (float) 1);
			} else {
				Util.dropItem(player, gacha);
			}
			break;
		case LIST:
			break;
		case EDIT:
			break;
		case MENTE:
			break;
		case SAVE:
			break;
		case RELOAD:
			break;
		case DEMO:
			break;

		}

		return true;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta im = itemstack.getItemMeta();
		List<String> lore;
		switch (MenuType.getType(slot)) {
		case INFO:
			im.setDisplayName(ChatColor.GREEN + "コマンド一覧");
			lore = new ArrayList<String>();
			im.setLore(lore);
			break;
		case GIVE:
			im.setDisplayName(ChatColor.YELLOW + "ガチャ券配布");
			lore = new ArrayList<String>();
			im.setLore(lore);
			break;
		case LIST:
			im.setDisplayName(ChatColor.AQUA + "排出アイテムリスト");
			lore = new ArrayList<String>();
			im.setLore(lore);
			break;
		case EDIT:
			im.setDisplayName(ChatColor.LIGHT_PURPLE + "ガチャの内容を編集");
			lore = new ArrayList<String>();
			im.setLore(lore);
			break;
		case MENTE:
			im.setDisplayName(ChatColor.BLUE + "メンテナンスモード");
			lore = new ArrayList<String>();
			im.setLore(lore);
			break;
		case SAVE:
			im.setDisplayName(ChatColor.GOLD + "Sqlにデータを保存");
			lore = new ArrayList<String>();
			im.setLore(lore);
			break;
		case RELOAD:
			im.setDisplayName(ChatColor.RED + "Sqlからデータをロード");
			lore = new ArrayList<String>();
			im.setLore(lore);
			break;
		case DEMO:
			im.setDisplayName(ChatColor.WHITE+ "デモ");
			lore = new ArrayList<String>();
			im.setLore(lore);
			break;
		default:
			break;
		}
		return im;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack is = null;
		MenuType mt = MenuType.getType(slot);
		if(mt == null){
			return null;
		}
		switch (mt) {
		case INFO:
			is = gm.getGachaTypeInfo();
			break;
		case GIVE:
			is = head.getMobHead("orange");
			break;
		case LIST:
			is = head.getMobHead("corn");
			break;
		case EDIT:
			is = head.getMobHead("riceball");
			break;
		case MENTE:
			is = head.getMobHead("grape");
			break;
		case SAVE:
			is = head.getMobHead("tomato");
			break;
		case RELOAD:
			is = head.getMobHead("milk_chocolate");
			break;
		case DEMO:
			is = head.getMobHead("sushi_roll");
			break;
		default:
			break;
		}
		return is;
	}

}
