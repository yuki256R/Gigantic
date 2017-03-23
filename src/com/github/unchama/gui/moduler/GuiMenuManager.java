package com.github.unchama.gui.moduler;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.KeyItem;
import com.github.unchama.yml.ConfigManager;

/**
 * Menu用のManagerです．Menuを作る時はこのクラスを継承すると簡単に作成できます．
 *
 * @author tar0ss
 *
 */
public abstract class GuiMenuManager {
	protected Gigantic plugin = Gigantic.plugin;
	protected ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	protected KeyItem keyitem;

	public HashMap<Integer, Class<? extends GuiMenuManager>> openmenumap;

	public GuiMenuManager() {
		openmenumap = new HashMap<Integer, Class<? extends GuiMenuManager>>();
	}

	/**メニューを開くスロット番号を設定します．
	 *
	 */
	protected abstract void setOpenMenuMap();

	/**
	 * キーアイテムを設定します．
	 *
	 */
	protected abstract void setKeyItem();

	/**
	 * クリックの種類を取得します．
	 *
	 * @return
	 */
	public abstract String getClickType();

	/**
	 * インベントリのサイズを取得します
	 *
	 * @return
	 */
	public abstract int getInventorySize();

	/**
	 * インベントリの名前を取得します．
	 *
	 * @return
	 */
	public abstract String getInventoryName(Player player);

	/**
	 * キーとなるアイテム情報を返します．
	 *
	 * @return
	 */
	public KeyItem getKeyItem() {
		return this.keyitem;
	}

	/**
	 * インベントリタイプを取得します．
	 *
	 * @return
	 */
	protected abstract InventoryType getInventoryType();

	/**
	 * PlaceHolderを使用して与えられたナンバーのitemmetaを設定します．
	 *
	 * @param プレイヤー名
	 * @param インベントリ番号
	 * @param itemstack
	 * @param PlaceHolderAPIを使用する時true
	 */
	protected abstract ItemMeta
	getItemMeta(Player player, int slot, ItemStack itemstack);





	/**
	 * 与えられたプレイヤー用のインベントリを作成して取得します．
	 *
	 * @param プレイヤー名
	 * @param スロット番号
	 * @return
	 */
	public Inventory getInventory(Player player,int slot) {
		Inventory inv = this.getEmptyInventory(player);

		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack itemstack = this.getItemStack(player, i);
			if (itemstack == null)
				continue;
			inv.setItem(i, itemstack);
		}
		return inv;
	}

	/**
	 * 空のインベントリを取得します．
	 *
	 * @param player
	 * @return
	 */
	protected Inventory getEmptyInventory(Player player) {
		Inventory inv;
		InventoryType it = this.getInventoryType();
		if (it == null) {
			inv = Bukkit.getServer().createInventory(player,
					this.getInventorySize(), this.getInventoryName(player));
		} else {
			inv = Bukkit.getServer().createInventory(player,
					this.getInventoryType(), this.getInventoryName(player));
		}
		return inv;
	}

	/**
	 * 与えられたナンバーのItemStackを取得します．
	 *
	 * @param player名
	 * @param インベントリ番号
	 * @return
	 */
	protected abstract ItemStack getItemStack(Player player, int slot);

	/**
	 * 開いた時の音の名前を取得します．
	 *
	 * @return
	 */
	public abstract Sound getSoundName();

	/**
	 * 開いた時の音の大きさを取得します
	 *
	 * @return
	 */
	public abstract float getVolume();

	/**
	 * 開いた時の音の高さを取得します
	 *
	 * @return
	 */
	public abstract float getPitch();

	/**
	 * 鍵となるアイテムを持っている時trueとなります． メニューで作成した項目からジャンプするタイプのメニューではfalseになります．
	 *
	 * @return
	 */
	public boolean hasKey() {
		return keyitem.getMaterial() != null;
	}

}
