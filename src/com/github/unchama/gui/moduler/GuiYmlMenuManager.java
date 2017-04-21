package com.github.unchama.gui.moduler;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
/**Ymlから編集できるようにしたMenuClass
 *
 * @author tar0ss
 *
 */
public abstract class GuiYmlMenuManager extends GuiMenuManager{
	private File file;
	private String filename;
	protected FileConfiguration fc;

	public GuiYmlMenuManager(){
		super();
		this.plugin = Gigantic.plugin;
		this.filename = GuiMenu.ManagerType.getMenuNamebyClass(this.getClass());
		this.file = new File(plugin.getDataFolder(), filename);
		saveDefaultFile();
		this.fc = loadFile();
		setKeyItem();
		setOpenMenuMap(openmap);
	}
	/**
	 * デフォルトのファイルを生成します
	 *
	 */
	private void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
	}
	/**
	 * データフォルダーにあるfileを読み込みます
	 *
	 * @param filename
	 * @return
	 */
	private FileConfiguration loadFile() {
		return YamlConfiguration.loadConfiguration(file);
	}

	@Override
	protected void setKeyItem() {
		Material material = null;
		int damege;
		String name;
		List<String> lore;

		damege = this.fc.getInt("key.damege");
		name = this.fc.getString("key.name");
		lore = this.fc.getStringList("key.lore");

		try {
			String s = this.fc.getString("key.material");
			if (s != null) {
				material = Material.valueOf(s.toUpperCase());
			}
		} catch (IllegalArgumentException e) {
			Bukkit.getLogger().warning(
					this.fc.getString("key.material") + " というマテリアルは存在しません．");
		}
		this.keyitem = new KeyItem(material, damege, name, lore);
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		for (int i = 0; i < this.getInventorySize(); i++) {
			String menu = this.fc.getString(Integer.toString(i) + ".open");
			if (menu != null) {
				ManagerType mt;
				try {
					mt = GuiMenu.ManagerType.valueOf(menu.toUpperCase())
							;
					openmap.put(new Integer(i), mt);
				} catch (IllegalArgumentException e) {
					Bukkit.getLogger().warning(menu + " というメニューは存在しません．");
				}
			}
		}
	}
	@Override
	protected void setIDMap(HashMap<Integer, String> methodmap) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public boolean invoke(Player player, String identifier) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
	@Override
	public String getClickType() {
		return this.fc.getString("click");
	}

	@Override
	public int getInventorySize() {
		int size;
		if (this.getInventoryType() != null) {
			size = this.getInventoryType().getDefaultSize();
		} else {
			size = this.fc.getInt("size");
		}
		return size;
	}

	@Override
	public String getInventoryName(Player player) {
		String s = this.fc.getString("name");
		return s != null ? PlaceholderAPI.setPlaceholders(player, s) : "";
	}

	@Override
	protected InventoryType getInventoryType() {
		String s = this.fc.getString("inventorytype");
		InventoryType it = null;
		try {
			if (s != null) {
				it = InventoryType.valueOf(s.toUpperCase());
			}
		} catch (IllegalArgumentException e) {
			Bukkit.getLogger().warning(s + " というInventoryTypeは見つかりません．");
			it = null;
		}
		return it;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int n, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(PlaceholderAPI.setPlaceholders(player,
				itemmeta.getDisplayName()));
		itemmeta.setLore(PlaceholderAPI.setPlaceholders(player,
				itemmeta.getLore()));
		Boolean b = this.fc.getBoolean(n + ".isSkullofOwner");
		if (b != null) {
			if(b && itemmeta instanceof SkullMeta){
				SkullMeta skullmeta = (SkullMeta) itemmeta;
				skullmeta.setOwner(player.getName());
			}
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int i) {
		String s = Integer.toString(i) + ".itemstack";
		ItemStack itemstack = this.fc.getItemStack(s);
		return itemstack;
	}

	@Override
	public Sound getSoundName() {
		String s = this.fc.getString("sound.name");
		return Sound.valueOf(s);
	}

	@Override
	public float getVolume() {
		return (float) this.fc.getDouble("sound.volume");
	}

	@Override
	public float getPitch() {
		return (float) this.fc.getDouble("sound.pitch");
	}



}
