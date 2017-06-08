package com.github.unchama.gui.presentbox;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.presentbox.PresentBoxManager;

public class PresentBoxMenuManager extends GuiMenuManager{

	public PresentBoxMenuManager(){
		// Invoke設定
		for (int i = 0; i < getInventorySize(); i++) {
			id_map.put(i, Integer.toString(i));
		}
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		PresentBoxManager manager = gp.getManager(PresentBoxManager.class);

		// インベントリ基本情報
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(), this.getInventoryName(player));

		for(int i = 0; i < manager.getInventory().getSize(); i++){
			if(i >= inv.getSize()){
				break;
			}

			ItemStack item = manager.getInventory().getItem(i);
			if(item == null){
				continue;
			}
			inv.setItem(i, item);
		}

		return inv;
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		int slot;
		try {
			slot = Integer.valueOf(identifier);
		} catch (NumberFormatException nfex) {
			return false;
		}

		// アイテムを付与
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		PresentBoxManager manager = gp.getManager(PresentBoxManager.class);
		ItemStack givedItem = manager.giveItem(slot);
		if(givedItem != null){
			player.openInventory(getInventory(player, slot));
		}else{
			return false;
		}

		return true;
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
		return 54;
	}

	@Override
	public String getInventoryName(Player player) {
		return "プレゼントBOX（クリックで受取）";
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
		return Sound.ENTITY_PLAYER_LEVELUP;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return 0.5f;
	}

}
