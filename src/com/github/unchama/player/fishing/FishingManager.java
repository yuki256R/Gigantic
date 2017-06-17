package com.github.unchama.player.fishing;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fishinglevel.FishingLevelManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.util.InventoryUtil;
import com.github.unchama.yml.DimensionalInventoryYmlManager;

/**
*
* @author ten_niti
*
*/
public class FishingManager extends DataManager implements UsingSql {

	// 放置で釣った回数
	private int idleFishingCount;
	// タイミングを合わせて釣った回数
	private int actionFishingCount;

	// 釣ったアイテム入れ
	private Inventory coolerBox;

	public FishingManager(GiganticPlayer gp) {
		super(gp);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void save(Boolean loginflag) {
		// TODO 自動生成されたメソッド・スタブ

	}

	// 放置で釣った回数
	public void setIdleFishingCount(int count){
		idleFishingCount = count;
	}
	public void addIdleFishingCount(){
		idleFishingCount++;
	}
	public int getIdleFishingCount(){
		return idleFishingCount;
	}

	// タイミングを合わせて釣った回数
	public void setActionFishingCount(int count){
		actionFishingCount = count;
	}
	public void addActionFishingCount(){
		actionFishingCount++;
	}
	public int getActionFishingCount(){
		return actionFishingCount;
	}

	// クーラーボックス
	public void SetInventory(Inventory inv) {
		coolerBox = inv;
	}
	// インベントリにアイテムを追加
	public boolean addItem(ItemStack item) {
		if(coolerBox == null){
			Bukkit.getServer().getLogger().info("coolerBox == null");
		}
		return InventoryUtil.addNewItemStack(coolerBox, item);
	}
	public Inventory getCoolerBox() {
		return coolerBox;
	}

	// インベントリのサイズ
	public int getSize() {
		int level = gp.getManager(FishingLevelManager.class).getLevel();
		return Gigantic.yml.getManager(DimensionalInventoryYmlManager.class)
				.getCapacity(level);
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
}
