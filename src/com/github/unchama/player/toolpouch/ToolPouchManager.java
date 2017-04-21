package com.github.unchama.player.toolpouch;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.sql.ToolPouchTableManager;

public class ToolPouchManager extends DataManager implements UsingSql {
	ToolPouchTableManager tm;

	private Inventory pouch;

	public ToolPouchManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(ToolPouchTableManager.class);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	/**
	 * @return pouch
	 */
	public Inventory getPouch() {
		return pouch;
	}

	/**
	 * @param pouch
	 *            セットする pouch
	 */
	public void setPouch(Inventory pouch) {
		this.pouch = pouch;
	}

	/**
	 * ポーチのサイズを取得
	 *
	 * @return
	 */
	public int getSize() {
		int level = gp.getManager(SeichiLevelManager.class).getLevel();
		int row = (int) level / 15 + 1;
		return row > 6 ? 9 * 6 : 9 * row;
	}

	/**
	 * ポーチの名前を取得
	 *
	 * @return
	 */
	public String getName() {
		return "" + ChatColor.AQUA + ChatColor.BOLD + "ツールポーチ";
	}

	public void open(Player player) {
		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 0.4F);
		player.openInventory(getPouch());
	}

}
