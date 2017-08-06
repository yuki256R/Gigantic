package com.github.unchama.player.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.presentbox.PresentBoxManager;
import com.github.unchama.sql.player.InventoryTableManager;

public final class InventoryManager extends DataManager implements UsingSql {

	InventoryTableManager tm;
	PlayerInventory pinv;
	//元々あったアイテム達
	List<ItemStack> rests;

	private static final int OFFHANDINDEX = 0;

	private static final int ARMORINDEXSTART = 1;
	private static final int ARMORINDEXEND = 5;

	public InventoryManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(InventoryTableManager.class);
		pinv = PlayerManager.getPlayer(gp).getInventory();
		rests = new ArrayList<ItemStack>();
	}

	public void onAvailable() {
		PresentBoxManager pM = gp.getManager(PresentBoxManager.class);
		if (!rests.isEmpty()) {
			PlayerManager.getPlayer(gp).sendMessage(ChatColor.AQUA + "従来のインベントリに残っていたアイテムをプレゼントボックスに送信しました");
			pM.addInventory(rests.toArray(new ItemStack[0]));
			rests.clear();
		}

	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	public List<ItemStack> getItemList() {
		Player player = PlayerManager.getPlayer(gp);
		PlayerInventory pi = player.getInventory();
		List<ItemStack> items = new ArrayList<ItemStack>();

		// アイテム一覧をリストに取り出す
		ItemStack offhand = pi.getItemInOffHand();
		items.add(offhand);
		ItemStack[] armor = pi.getArmorContents();
		items.addAll(Arrays.asList(armor));
		ItemStack[] contents = pi.getStorageContents();
		items.addAll(Arrays.asList(contents));
		return items;
	}

	public void setPlayerInventory(List<ItemStack> itemlist) {
		this.saveRestItem();
		this.resetPlayerInventory();
		pinv.setItemInOffHand(itemlist.get(OFFHANDINDEX));
		pinv.setArmorContents(itemlist.subList(ARMORINDEXSTART, ARMORINDEXEND).toArray(new ItemStack[0]));
		pinv.setStorageContents(itemlist.subList(ARMORINDEXEND, itemlist.size()).toArray(new ItemStack[0]));
	}

	public void resetPlayerInventory() {
		pinv.clear();
	}

	private void saveRestItem() {
		this.sendtoRest(pinv.getItemInOffHand());
		for (ItemStack is : pinv.getStorageContents()) {
			this.sendtoRest(is);
		}
		for (ItemStack is : pinv.getArmorContents()) {
			this.sendtoRest(is);
		}
	}

	private void sendtoRest(ItemStack is) {
		if (is != null && !is.getType().equals(Material.AIR)) {
			rests.add(is);
		}
	}

}
