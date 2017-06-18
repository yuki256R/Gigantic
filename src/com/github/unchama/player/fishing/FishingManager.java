package com.github.unchama.player.fishing;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fishinglevel.FishingLevelManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.FishingTableManager;
import com.github.unchama.util.InventoryUtil;

/**
 *
 * @author ten_niti
 *
 */
public class FishingManager extends DataManager implements UsingSql {

	// 放置で釣った回数
	private int idleFishingCount;
	// タイミングを合わせて釣った回数
	private int activeFishingCount;

	// 釣ったアイテム入れ
	private Inventory coolerBox;

	// なぜか釣竿を空中に右クリックすると同時に左クリック判定が誤爆するのでチェック
	private int lastCheckTick = 0;

	FishingTableManager fm = sql.getManager(FishingTableManager.class);

	public FishingManager(GiganticPlayer gp) {
		super(gp);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void save(Boolean loginflag) {
		fm.save(gp, loginflag);
	}

	// 放置で釣った回数
	public void setIdleFishingCount(int count) {
		idleFishingCount = count;
	}

	public void addIdleFishingCount() {
		idleFishingCount++;
	}

	public int getIdleFishingCount() {
		return idleFishingCount;
	}

	// タイミングを合わせて釣った回数
	public void setActiveFishingCount(int count) {
		activeFishingCount = count;
	}

	public void addActiveFishingCount() {
		activeFishingCount++;
	}

	public int getActiveFishingCount() {
		return activeFishingCount;
	}

	// クーラーボックス
	public void SetInventory(Inventory inv) {
		coolerBox = inv;
	}

	// インベントリにアイテムを追加
	public boolean addItem(ItemStack item) {
		if (coolerBox == null) {
			Bukkit.getServer().getLogger().info("coolerBox == null");
		}
		return InventoryUtil.addItemStack(coolerBox, item, false);
	}

	public Inventory getCoolerBox() {
		return coolerBox;
	}

	// インベントリのサイズ
	public int getSize() {
		int level = gp.getManager(FishingLevelManager.class).getLevel();
		return fishing.getCapacity(level);
	}

	public void open(Player player) {
		resize();
		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 0.4F);
		player.openInventory(getCoolerBox());
	}

	// インベントリ名
	public String getName() {
		return "" + ChatColor.AQUA + ChatColor.BOLD + "クーラーボックス";
	}

	// サイズの更新処理
	public void resize() {
		// 今のインベントリとサイズが違う場合
		if (coolerBox.getSize() != this.getSize()) {
			// 新しいインベントリを作成
			Inventory inv = Bukkit.getServer().createInventory(null,
					this.getSize(), this.getName());
			// 新しいインベントリのほうが大きい場合
			if (coolerBox.getSize() <= this.getSize()) {
				inv.setContents(coolerBox.getContents());
			} else {
				// 新しいインベントリのほうが小さい場合
				for (int i = 0; i < inv.getSize(); i++) {
					inv.setItem(i, coolerBox.getItem(i));
				}
			}
			coolerBox = inv;
		}
	}

	// なぜか釣竿を空中に右クリックすると同時に左クリック判定が誤爆するのでチェック
	public boolean checkTick() {
		Player player = PlayerManager.getPlayer(gp);
		int currentTick = player
				.getStatistic(org.bukkit.Statistic.PLAY_ONE_TICK);
		boolean ret = lastCheckTick == currentTick;
		// Bukkit.getServer().getLogger().info(lastCheckTick + "==" +
		// currentTick + " = " + ret);
		lastCheckTick = currentTick;
		return ret;
	}
}
