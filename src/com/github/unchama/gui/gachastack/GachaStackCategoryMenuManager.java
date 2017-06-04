package com.github.unchama.gui.gachastack;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gachastack.GachaStackManager;
import com.github.unchama.player.gui.GuiStatusManager;
import com.github.unchama.util.Converter;

public class GachaStackCategoryMenuManager extends GuiMenuManager{
	private Gacha gacha = Gigantic.gacha;

	public GachaStackCategoryMenuManager() {
		// Invoke設定
		for (int i = 0; i < getInventorySize(); i++) {
			id_map.put(i, String.valueOf(i));
		}
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		GachaStackManager manager = gp.getManager(GachaStackManager.class);

		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(),
				this.getInventoryName(player));

		GachaType type = getGachaType(player);
		GachaManager gm = gacha.getManager(type.getManagerClass());

		for (GachaItem gi : gm.getGachaItemMap().values()) {
			int id = gi.getID();
			ItemStack itemStack = gi.getItem();
            ItemMeta itemMeta = itemStack.getItemMeta();
            int amount = manager.getAmount(type, id);
            itemMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GREEN + amount +"個"
                    , ChatColor.RESET + "" +  ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで1スタック取り出し"));
            itemStack.setItemMeta(itemMeta);

			inv.setItem(id, itemStack);
		}
		return inv;
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		int id = Converter.toInt(identifier);
		GachaType type = getGachaType(player);
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		GachaStackManager manager = gp.getManager(GachaStackManager.class);
		boolean isSuccess = manager.takeOutGachaItem(type, id);
		if(isSuccess){
			player.openInventory(getInventory(player, 0));
		}

		return isSuccess;
	}

	private GachaType getGachaType(Player player){
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		GuiStatusManager manager = gp.getManager(GuiStatusManager.class);
		GachaType type = GachaType.valueOf(manager.getSelectedCategory("GachaStackMainMenuManager"));
		return type;
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
		return 9 * 6;
	}

	@Override
	public String getInventoryName(Player player) {
		return "ガチャスタック";
	}

	@Override
	protected InventoryType getInventoryType() {
		// TODO 自動生成されたメソッド・スタブ
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
		return Sound.BLOCK_CHEST_LOCKED;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return (float) 0.5;
	}

}
