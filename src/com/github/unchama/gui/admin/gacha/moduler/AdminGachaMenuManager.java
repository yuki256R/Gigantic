package com.github.unchama.gui.admin.gacha.moduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gui.moduler.AdminMenuManager;
import com.github.unchama.util.Util;

/**
 * @author tar0ss
 *
 */
public abstract class AdminGachaMenuManager extends AdminMenuManager {
	public static enum MenuType {
		INFO(0), GIVE(1), LIST(2), EDIT(3), TICKET(4),APPLE(5),MENTE(6), SAVE(7), RELOAD(8),;

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


	GachaManager gm;
	GachaType gt;

	public AdminGachaMenuManager() {
		super();
		gt = GachaType.getGachaTypebyMenu(this.getClass());
		gm = gacha.getManager(gt.getManagerClass());

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
			gacha.setAmount(64);
			Util.giveItem(player, gacha, false);
			break;
		case LIST:
			break;
		case EDIT:
			break;
		case TICKET:
			break;
		case APPLE:
			break;
		case MENTE:
			player.chat("/gacha mente " + gt.name());
			break;
		case SAVE:
			player.chat("/gacha save " + gt.name());
			break;
		case RELOAD:
			player.chat("/gacha reload " + gt.name());
			break;
		}

		return true;
	}



	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta im = itemstack.getItemMeta();
		List<String> lore;
		switch (MenuType.getType(slot)) {
		case INFO:
			lore = im.getLore();
			lore.add(ChatColor.GREEN + "クリックしてガチャのデモを回す（未実装）");
			im.setLore(lore);
			break;
		case GIVE:
			im.setDisplayName(ChatColor.YELLOW + "ガチャ券を配布する（未実装）");
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
		case TICKET:
			im.setDisplayName(ChatColor.LIGHT_PURPLE + "ガチャ券を変更する");
			lore = new ArrayList<String>();
			im.setLore(lore);
			break;
		case APPLE:
			im.setDisplayName(ChatColor.LIGHT_PURPLE + "がちゃりんごを変更する");
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
		case TICKET:
			is = head.getMobHead("sushi_roll");
			break;
		case APPLE:
			is = head.getMobHead("apple");
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
		default:
			break;
		}
		return is;
	}

}
