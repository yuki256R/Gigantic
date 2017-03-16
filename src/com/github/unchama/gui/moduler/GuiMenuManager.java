package com.github.unchama.gui.moduler;

import java.util.List;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.gui.KeyItem;
import com.github.unchama.yml.moduler.YmlManager;

/**
 * Menu用のManagerです．Menuを作る時はこのクラスを継承すると簡単に作成できます．
 *
 * @author tar0ss
 *
 */
public abstract class GuiMenuManager extends YmlManager {
	private KeyItem keyitem;

	public GuiMenuManager() {
		super();
		String m = this.fc.getString("key.material");
		Material material;
		int damege;
		String name;
		List<String> lore;

		if (m != null) {
			try {
				material = Material.valueOf(m);
			} catch (IllegalArgumentException e) {
				material = null;
			}

			if (material == null) {
				damege = 0;
			} else {
				damege = this.fc.getInt("key.damege");
			}

		} else {
			material = null;
			damege = 0;
		}

		name = this.fc.getString("key.name");
		lore = this.fc.getStringList("key.lore");

		this.keyitem = new KeyItem(material, damege, name, lore);
	}

	@Override
	protected void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
	}

	/**
	 * メニュータイプを取得します． 1: 手に持った所定のアイテムを持った状態でクリックすることで開く 2:
	 * 本プラグインで作成されたメニューのアイテムをクリックすることで開く
	 *
	 * @return
	 */
	public int getType() {
		return this.fc.getInt("menutype");
	}

	/**
	 * キーとなるアイテム情報を返します．
	 *
	 * @return
	 */
	public KeyItem getKeyItem() {
		return this.keyitem;
	}

	/**
	 * クリックの種類を取得します．
	 *
	 * @return
	 */
	public String getClickType() {
		return this.fc.getString("click");
	}

	/**
	 * インベントリのサイズを取得します
	 *
	 * @return
	 */
	public int getInventorySize() {
		return this.fc.getInt("size");
	}

	/**
	 * インベントリの名前を取得します．
	 *
	 * @return
	 */
	public String getInventoryName(Player player) {
		return PlaceholderAPI
				.setPlaceholders(player, this.fc.getString("name"));
	}

	/**
	 * PlaceHolderを使用して与えられたナンバーのitemmetaを設定します．
	 *
	 * @param p
	 * @param n
	 * @param itemstack
	 */
	private void setItemMeta(Player player, int n, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(PlaceholderAPI.setPlaceholders(player,
				itemmeta.getDisplayName()));
		itemmeta.setLore(PlaceholderAPI.setPlaceholders(player,
				itemmeta.getLore()));

		if (this.fc.getBoolean(n + ".isSkullofOwner")) {
			SkullMeta skullmeta = (SkullMeta) itemmeta;
			skullmeta.setOwner(player.getName());
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		itemstack.setItemMeta(itemmeta);
	}

	/**
	 * 与えられたプレイヤー用のインベントリを作成して取得します．
	 *
	 * @param p
	 * @return
	 */
	public Inventory getInventory(Player player) {
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(), this.getInventoryName(player));
		for (int i = 0; i < this.getInventorySize(); i++) {
			String s = Integer.toString(i) + ".itemstack";
			ItemStack itemstack = this.fc.getItemStack(s);
			if (!(itemstack == null)) {
				this.setItemMeta(player, i, itemstack);
				inv.setItem(i, itemstack);
			}
		}
		return inv;
	}
	public Sound getSound() {
		String s = this.fc.getString("sound.name");
		return Sound.valueOf(s);
	}

	public float getVolume() {
		return (float)this.fc.getDouble("sound.volume");
	}

	public float getPitch() {
		return (float)this.fc.getDouble("sound.pitch");
	}

}
