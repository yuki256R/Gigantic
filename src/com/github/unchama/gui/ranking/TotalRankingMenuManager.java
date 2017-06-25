/**
 *
 */
package com.github.unchama.gui.ranking;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

/**
 * @author tar0ss
 *
 */
public abstract class TotalRankingMenuManager extends RankingMenuManager {

	@Deprecated
	@Override
	protected TimeType getTimeType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected Inventory getEmptyPageInventory(int page) {
		Inventory inv;
		InventoryType it = this.getInventoryType();
		if (it == null) {
			inv = Bukkit.getServer().createInventory(null,
					this.getInventorySize(), this.getInventoryName(null) + "-" + page + "ページ");
		} else {
			inv = Bukkit.getServer().createInventory(null,
					this.getInventoryType(), this.getInventoryName(null) + "-" + page + "ページ");
		}
		if (page != 1) {
			inv.setItem(prevButtonSlot, prevButton);
		}
		if (page != 3) {
			inv.setItem(nextButtonSlot, nextButton);
		}
		return inv;
	}
}
