package com.github.unchama.gui.ranking;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager.DebugEnum;

public abstract class RankingMenuManager extends GuiMenuManager {
	//ランクとスロットの対応付け
	private static final HashMap<Integer, Integer> rankslotMap = new HashMap<Integer, Integer>() {
		{
			for (int i = 1; i <= 45; i++) {
				put(i, i - 1);
			}
			for (int i = 46; i <= 50; i++) {
				put(i, i + 1);
			}
		}
	};

	//保持しておくインベントリマップ
	private LinkedHashMap<Integer, Inventory> invMap;

	// 前のページへボタン
	private ItemStack prevButton;
	private final int prevButtonSlot = 45;

	// 次のページへボタン
	private ItemStack nextButton;
	private final int nextButtonSlot = 53;

	public RankingMenuManager() {
		super();
		prevButton = head.getMobHead("left");
		Util.setDisplayName(prevButton, "前のページ");
		nextButton = head.getMobHead("right");
		Util.setDisplayName(nextButton, "次のページ");

		invMap = new LinkedHashMap<Integer, Inventory>();
		invMap.put(1, this.getEmptyPageInventory(1));
		invMap.put(2, this.getEmptyPageInventory(2));
		invMap.put(3, this.getEmptyPageInventory(3));
	}

	public void updateRanking(LinkedHashMap<String, Double> rankMap) {
		debug.sendMessage(DebugEnum.GUI, this.getInventoryName(null) +  "をアップデートします");
		int rank = 1;
		for (Map.Entry<String, Double> e : rankMap.entrySet()) {
			int page = (rank - 1) / 50 + 1;
			String name = e.getKey();
			double value = e.getValue();
			ItemStack is = head.getPlayerHead(name);
			Util.setDisplayName(is, "" + ChatColor.YELLOW + rank + "位:" + ChatColor.RESET + name);
			Util.setLore(is, "" + ChatColor.GREEN + this.getLore(value));
			int slot = rankslotMap.get(rank);
			invMap.get(page).setItem(slot, is);

			if(invMap.get(page).contains(is)){
				debug.sendMessage(DebugEnum.GUI, "page:" + page + " slot:" + slot + "に正しくセットしました");
			}
			rank++;
		}
	}

	protected abstract String getLore(double value);

	private Inventory getEmptyPageInventory(int page) {
		Inventory inv;
		InventoryType it = this.getInventoryType();
		if (it == null) {
			inv = Bukkit.getServer().createInventory(null,
					this.getInventorySize(), this.getInventoryName(null) + "-" + page);
		} else {
			inv = Bukkit.getServer().createInventory(null,
					this.getInventoryType(), this.getInventoryName(null) + "-" + page);
		}
		if (page != 1) {
			inv.setItem(prevButtonSlot, prevButton);
		}
		if (page != 3) {
			inv.setItem(nextButtonSlot, nextButton);
		}
		return inv;
	}

	@Override
	public Inventory getInventory(Player player) {
		return invMap.get(1);
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		idmap.put(prevButtonSlot, "go");
		idmap.put(nextButtonSlot, "back");
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		String title = player.getOpenInventory().getTitle();
		return false;
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
		// TODO 自動生成されたメソッド・スタブ
		return 54;
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
