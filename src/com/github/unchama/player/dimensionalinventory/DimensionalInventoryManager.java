package com.github.unchama.player.dimensionalinventory;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.sql.dimensionalinventory.DimensionalInventoryTableManager;
import com.github.unchama.yml.DimensionalInventoryYmlManager;

// 四次元ポケット
public class DimensionalInventoryManager extends DataManager implements
		UsingSql, Initializable {

	private Inventory inventory;

	DimensionalInventoryTableManager tableManager = sql
			.getManager(DimensionalInventoryTableManager.class);

	public DimensionalInventoryManager(GiganticPlayer gp) {
		super(gp);
	}

	// 四次元ポケットのgetterとsetter
	public Inventory getInventory() {
		return inventory;
	}

	public void SetInventory(Inventory inv) {
		inventory = inv;
	}

	// 使用可能か
	public boolean isUse(){
		// 0は使用不可
		return getSize() > 0;
	}

	// インベントリのサイズ
	public int getSize() {
		int level = gp.getManager(SeichiLevelManager.class).getLevel();
		return Gigantic.yml.getManager(DimensionalInventoryYmlManager.class)
				.getCapacity(level);
	}

	public void open(Player player) {
		resize();
		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 0.4F);
		player.openInventory(getInventory());
	}

	// インベントリ名
	public String getName() {
		return "" + ChatColor.AQUA + ChatColor.BOLD + "四次元ポケット";
	}

	// サイズの更新処理
	public void resize() {
		// 今のインベントリとサイズが違う場合
		if (inventory.getSize() != this.getSize()) {
			// 新しいインベントリを作成
			Inventory inv = Bukkit.getServer().createInventory(null,
					this.getSize(), this.getName());
			// 新しいインベントリのほうが大きい場合
			if (inventory.getSize() <= this.getSize()) {
				inv.setContents(inventory.getContents());
			} else {
				// 新しいインベントリのほうが小さい場合
				for (int i = 0; i < inv.getSize(); i++) {
					inv.setItem(i, inventory.getItem(i));
				}
			}
			inventory = inv;
		}
	}

	@Override
	public void save(Boolean loginflag) {
		tableManager.save(gp, loginflag);

	}

	@Override
	public void init() {
		this.resize();
	}

}
