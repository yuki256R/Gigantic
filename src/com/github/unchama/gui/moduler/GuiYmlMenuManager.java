package com.github.unchama.gui.moduler;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

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
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.menu.PlayerMenuManager;
import com.github.unchama.player.toolpouch.ToolPouchManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.lobbyswitch.LobbySwitch;

/**
 * Ymlから編集できるようにしたMenuClass
 *
 * @author tar0ss
 *
 */
public abstract class GuiYmlMenuManager extends GuiMenuManager {
	private File file;
	private String filename;
	protected FileConfiguration fc;

	public GuiYmlMenuManager() {
		super();
		this.plugin = Gigantic.plugin;
		this.filename = GuiMenu.ManagerType.getMenuNamebyClass(this.getClass());
		this.file = new File(plugin.getDataFolder(), filename);
		saveDefaultFile();
		this.fc = loadFile();
		setKeyItem();
		setOpenMenuMap(openmap);
		setIDMap(id_map);
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
	public boolean islocked(Player player, int slot) {
		boolean perm = this.fc.getBoolean(Integer.toString(slot) + ".onlyop");
		if (perm) {
			if (!player.isOp()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void open(Player player, int slot, boolean clearflag) {
		boolean perm = this.fc.getBoolean(Integer.toString(slot) + ".onlyop");
		if (perm) {
			if (!player.isOp()) {
				return;
			}
		}
		player.openInventory(this.getInventory(player, slot));
		// 開く音を再生
		player.playSound(player.getLocation(), getSoundName(), getVolume(),
				getPitch());
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		PlayerMenuManager m = gp.getManager(PlayerMenuManager.class);
		if (clearflag) {
			m.clear();
		}
		debug.sendMessage(player, DebugEnum.GUI, getInventoryName(player)
				+ ChatColor.RESET + "を開きます．");
		m.push(ManagerType.getTypebyClass(this.getClass()));
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
			String menu = this.fc.getString(Integer.toString(i) + ".openmenu");
			if (menu != null) {
				ManagerType mt;
				try {
					mt = GuiMenu.ManagerType.valueOf(menu.toUpperCase());
					openmap.put(new Integer(i), mt);
				} catch (IllegalArgumentException e) {
					Bukkit.getLogger().warning(menu + " というメニューは存在しません．");
				}
			}
		}
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> methodmap) {
		for (int i = 0; i < this.getInventorySize(); i++) {
			String name = this.fc.getString(Integer.toString(i)
					+ ".openinventory");
			if (name == null) {
				continue;
			}
			switch (name) {
			case "toolpouch":
				methodmap.put(i, "openToolPouch");
				break;
			case "garbagecan":
				methodmap.put(i, "openGarbageCan");
				break;
			case "lobbyswitch":
				methodmap.put(i, "openServerMenu");
				break;
			default:
				break;
			}
		}
		for (int i = 0; i < this.getInventorySize(); i++) {
			String name = this.fc.getString(Integer.toString(i) + ".command");
			if (name == null) {
				continue;
			}
			switch (name) {
			case "fastcraft":
				methodmap.put(i, "commandFastCraft");
				break;
			case "spawn":
				methodmap.put(i, "commandSpawn");
				break;
			default:
				break;
			}
		}
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		switch (identifier) {
		case "openToolPouch":
			gp.getManager(ToolPouchManager.class).open(player);
			return true;
		case "openGarbageCan":
			player.openInventory(Bukkit.createInventory(player, 54,
					ChatColor.RED + "" + ChatColor.BOLD + "ゴミ箱(取扱注意)"));
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1,
					(float) 1.5);
			return true;
		case "commandFastCraft":
			player.closeInventory();
			player.playSound(player.getLocation(),
					Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			player.chat("/fc craft");
			return true;
		case "commandSpawn":
			player.closeInventory();
			player.playSound(player.getLocation(),
					Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			player.chat("/spawn");
			return true;
		case "openServerMenu":
			if(Bukkit.getPluginManager().isPluginEnabled("LobbySwitch")){
				LobbySwitch.p.getConfigManager().getInventory();
			}
			return true;
		default:
			return false;
		}
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
	protected ItemMeta getItemMeta(Player player, int i, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		Boolean b = this.fc.getBoolean(i + ".isSkullofOwner");
		if (b != null) {
			if (b && itemmeta instanceof SkullMeta) {
				SkullMeta skullmeta = (SkullMeta) itemmeta;
				skullmeta.setOwner(player.getName());
			}
		}
		if (itemmeta.getDisplayName() != null) {
			itemmeta.setDisplayName(PlaceholderAPI.setPlaceholders(player,
					itemmeta.getDisplayName()));
		}
		if (itemmeta.getLore() != null) {
			itemmeta.setLore(PlaceholderAPI.setPlaceholders(player,
					itemmeta.getLore()));
		}

		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int i) {
		boolean perm = this.fc.getBoolean(Integer.toString(i) + ".onlyop");

		if (perm) {
			if (!player.isOp()) {
				return null;
			}
		}

		String s = Integer.toString(i) + ".itemstack";
		ItemStack tmp = this.fc.getItemStack(s);
		ItemStack itemstack = tmp != null ? new ItemStack(tmp) : null;

		String mobhead = this.fc.getString(i + ".mobhead");
		if (mobhead != null) {
			itemstack = head.getMobHead(mobhead);
			ItemMeta tmpim = tmp.getItemMeta();
			ItemMeta im = itemstack.getItemMeta();
			im.setDisplayName(tmpim.getDisplayName());
			im.setLore(tmpim.getLore());
			itemstack.setItemMeta(im);
		}

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
