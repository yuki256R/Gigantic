package com.github.unchama.gui.moduler;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.menu.PlayerMenuManager;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * Menu用のManagerです．Menuを作る時はこのクラスを継承すると簡単に作成できます．
 *
 * @author tar0ss
 *
 */
public abstract class GuiMenuManager {
	protected Gigantic plugin = Gigantic.plugin;
	protected ConfigManager config = Gigantic.yml
			.getManager(ConfigManager.class);
	protected DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	public static GuiMenu gui = Gigantic.guimenu;

	/**
	 * マテリアル，ダメージ値，名前，説明文を保存する． このキーをもって左（右）クリックするとこのクラスのメニューを開く．
	 * Ymlを使用しない場合は非推奨です．
	 */
	protected KeyItem keyitem;

	/**
	 * スロット番号と開くメニューのクラスを保存する．
	 *
	 */
	protected HashMap<Integer, ManagerType> openmap = new HashMap<Integer, ManagerType>();

	protected HashMap<Integer, String> id_map = new HashMap<Integer, String>();

	public GuiMenuManager() {
		if (!GuiYmlMenuManager.class.isAssignableFrom(this.getClass())) {
			setOpenMenuMap(openmap);
			setIDMap(id_map);
		}

	}


	/**プレイヤーにオープンさせる．履歴を削除したい場合はflagをtrueにする．
	 *
	 * @param player
	 * @param slot
	 * @param clearflag
	 */
	public void open(Player player,int slot,boolean clearflag){
		player.openInventory(this.getInventory(player, slot));
		// 開く音を再生
		player.playSound(player.getLocation(), getSoundName(), getVolume(), getPitch());
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		PlayerMenuManager m = gp.getManager(PlayerMenuManager.class);
		if(clearflag){
			m.clear();
		}
		debug.sendMessage(player, DebugEnum.GUI,
				getInventoryName(player) + ChatColor.RESET
						+ "を開きます．");
		m.push(ManagerType.getTypebyClass(this.getClass()));
	}



	/**
	 * 何かメソッドを実行するキーストリングを設定します．
	 *
	 * @param methodmap
	 */
	protected abstract void setIDMap(HashMap<Integer, String> idmap);

	/**
	 * メソッドを呼び出します．
	 *
	 * @param player
	 *            プレイヤー名
	 * @param identifier
	 *            呼び出すメソッド名
	 * @return 成否
	 */
	public abstract boolean invoke(Player player, String identifier);

	/**
	 * このメニュー内のスロットから実行するメソッド識別子を取得します．
	 *
	 * @param slot
	 * @return
	 */
	public String getIdentifier(int slot) {
		Integer s = new Integer(slot);
		return id_map.isEmpty() ? null : (id_map.containsKey(s) ? id_map.get(s)
				: null);
	}

	/**
	 * メニューを開くスロット番号を設定します．
	 *
	 */
	protected abstract void setOpenMenuMap(
			HashMap<Integer, ManagerType> openmap);

	/**
	 * このメニュ内のスロットから次に開くメニューのクラスを取得します．
	 *
	 * @param slot
	 * @return
	 */
	public ManagerType getMenuManager(int slot) {
		Integer s = new Integer(slot);
		return openmap.isEmpty() ? null : (openmap.containsKey(s) ? openmap
				.get(s) : null);
	}

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
	protected abstract ItemMeta getItemMeta(Player player, int slot,
			ItemStack itemstack);

	/**
	 * 与えられたプレイヤー用のインベントリを作成して取得します．
	 *
	 * @param プレイヤー名
	 * @param スロット番号
	 * @return
	 */
	public Inventory getInventory(Player player, int slot) {
		Inventory inv = this.getEmptyInventory(player);

		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack itemstack = this.getItemStack(player, i);
			if (itemstack == null)
				continue;
			ItemMeta itemmeta = this.getItemMeta(player, i, itemstack);
			if (itemmeta != null)
				itemstack.setItemMeta(itemmeta);
			inv.setItem(i, itemstack);
		}
		inv.setMaxStackSize(Integer.MAX_VALUE);
		return inv;
	}
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
	 * 鍵となるアイテムを持っている時trueとなります．
	 *
	 * @return
	 */
	public boolean hasKey() {
		return keyitem != null;
	}

}
