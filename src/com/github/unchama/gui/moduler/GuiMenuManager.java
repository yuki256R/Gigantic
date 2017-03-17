package com.github.unchama.gui.moduler;

import java.util.HashMap;
import java.util.List;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.gui.GuiMenu;
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
	public HashMap<Integer,Class<? extends GuiMenuManager> > openmenumap;


	public GuiMenuManager() {
		super();
		openmenumap = new HashMap<Integer,Class<? extends GuiMenuManager> >();
		setKeyItem();
		for (int i = 0; i < this.getInventorySize(); i++) {
			String menu = this.fc.getString(Integer.toString(i) + ".open");
			if(menu != null){
				Class<? extends GuiMenuManager> clazz;
				try {
					clazz = GuiMenu.ManagerType.valueOf(menu.toUpperCase()).getManagerClass();
					openmenumap.put(new Integer(i), clazz);
				} catch (IllegalArgumentException e) {
					Bukkit.getLogger().warning(menu + " というメニューは存在しません．");
				}
			}
		}

	}

	private void setKeyItem(){
		Material material;
		int damege;
		String name;
		List<String> lore;

		damege = this.fc.getInt("key.damege");
		name = this.fc.getString("key.name");
		lore = this.fc.getStringList("key.lore");

		try {
			material = Material.valueOf(this.fc.getString("key.material").toUpperCase());
		} catch (IllegalArgumentException e) {
			Bukkit.getLogger().warning(this.fc.getString("key.material") + " というマテリアルは存在しません．");
			material = null;
		}
		this.keyitem = new KeyItem(material, damege, name, lore);
	}

	@Override
	protected void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
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
		int size;
		if(this.getInventoryType() != null){
			size = this.getInventoryType().getDefaultSize();
		}else{
			size = this.fc.getInt("size");
		}
		return size;
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
	 * @param プレイヤー名
	 * @param インベントリ番号
	 * @param itemstack
	 * @param PlaceHolderAPIを使用する時true
	 */
	private void setItemMeta(Player player, int n, ItemStack itemstack,boolean flag) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		if(flag == true){
			itemmeta.setDisplayName(PlaceholderAPI.setPlaceholders(player,
					itemmeta.getDisplayName()));
			itemmeta.setLore(PlaceholderAPI.setPlaceholders(player,
					itemmeta.getLore()));
			if (this.fc.getBoolean(n + ".isSkullofOwner")) {
				SkullMeta skullmeta = (SkullMeta) itemmeta;
				skullmeta.setOwner(player.getName());
			}
		}else{
			itemmeta.setDisplayName(itemmeta.getDisplayName());
			itemmeta.setLore(itemmeta.getLore());
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
		Inventory inv;
		InventoryType it = this.getInventoryType();
		if(it == null){
			inv = Bukkit.getServer().createInventory(player,
					this.getInventorySize(), this.getInventoryName(player));
		}else{
			inv = Bukkit.getServer().createInventory(player,
					this.getInventoryType(), this.getInventoryName(player));
		}


		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack itemstack = this.getItemStack(player,i,true);
			if(itemstack == null)continue;
			inv.setItem(i, itemstack);
		}
		return inv;
	}

	/**インベントリタイプを取得します．
	 *
	 * @return
	 */
	private InventoryType getInventoryType() {
		String s = this.fc.getString("inventorytype");
		InventoryType it = null;
		try{
			if(s != null){
				it = InventoryType.valueOf(s.toUpperCase());
			}
		}catch(IllegalArgumentException e){
			Bukkit.getLogger().warning(s + " というInventoryTypeは見つかりません．");
			it = null;
		}
		return it;
	}

	/**与えられたナンバーのItemStackを取得します．
	 *
	 * @param player名
	 * @param インベントリ番号
	 * @param PlaceHolderAPIを使用する時true
	 * @return
	 */
	private ItemStack getItemStack(Player player, int i,boolean flag) {
		String s = Integer.toString(i) + ".itemstack";
		ItemStack itemstack = this.fc.getItemStack(s);
		if (!(itemstack == null)) {
			this.setItemMeta(player, i, itemstack,flag);
		}
		return itemstack;
	}

	/**開いた時の音の名前を取得します．
	 *
	 * @return
	 */
	public Sound getSoundName() {
		String s = this.fc.getString("sound.name");
		return Sound.valueOf(s);
	}

	/**開いた時の音の大きさを取得します
	 *
	 * @return
	 */
	public float getVolume() {
		return (float)this.fc.getDouble("sound.volume");
	}

	/**開いた時の音の高さを取得します
	 *
	 * @return
	 */
	public float getPitch() {
		return (float)this.fc.getDouble("sound.pitch");
	}

	/**鍵となるアイテムを持っている時trueとなります．
	 * メニューで作成した項目からジャンプするタイプのメニューではfalseになります．
	 *
	 * @return
	 */
	public boolean hasKey() {
		return keyitem.getMaterial() != null;
	}

}
