package com.github.unchama.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import zedly.zenchantments.Zenchantments;

import com.github.unchama.gigantic.Gigantic;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * @author tar0ss
 *
 */
public class Util {

	// 指定した文字列をクリップボードにコピーする
	public static void setClipboard(String str) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Clipboard clip = kit.getSystemClipboard();

		StringSelection ss = new StringSelection(str);
		clip.setContents(ss, ss);
	}


	// プレイヤーにインベントリ状況を見ながらアイテムを付与する
	public static void giveItem(Player player, String itemName) {
		Material material = StringToMaterialData(itemName);
		// 指定が間違っていれば終了
		if (material == null) {
			return;
		}
		ItemStack itemstack = new ItemStack(material);
		giveItem(player, itemstack, true);
	}

	/**
	 * プレイヤーにアイテムを正常に与える
	 *
	 * @param player
	 * @param itemstack
	 * @param messageflag
	 * @return 足元にドロップしたときtrue
	 */
	public static boolean giveItem(Player player, ItemStack itemstack,
			boolean messageflag) {
		//0個以下ならreturn
		if (itemstack.getAmount() <= 0)
			return false;
		// インベントリがフルなら足元に落とす
		if (!isPlayerInventryFill(player)) {
			addItem(player, itemstack);
			// 拾った音
			player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP,
					(float) 0.1, (float) 1);
			return false;
		} else if (messageflag) {
			if (itemstack.getItemMeta().hasDisplayName()) {
				player.sendMessage(itemstack.getItemMeta().getDisplayName()
						+ ChatColor.RESET + " は持ち切れなかったためドロップしました.");
			} else {
				player.sendMessage(itemstack.getType().toString()
						+ ChatColor.RESET + " は持ち切れなかったためドロップしました.");
			}
			dropItem(player, itemstack);
			return true;
		} else {
			dropItem(player, itemstack);
			return true;
		}
	}

	// プレイヤーのインベントリがフルかどうか確認
	public static boolean isPlayerInventryFill(Player player) {
		return (player.getInventory().firstEmpty() == -1);
	}

	// 指定されたアイテムを指定されたプレイヤーインベントリに追加する
	public static void addItem(Player player, ItemStack itemstack) {
		player.getInventory().addItem(itemstack);
	}

	// 指定されたアイテムを指定されたプレイヤーにドロップする
	public static void dropItem(Player player, ItemStack itemstack) {
		player.getWorld().dropItemNaturally(player.getLocation(), itemstack);
	}

	// 指定された文字列を指定されたアイテム名に付与する
	public static void setDisplayName(ItemStack item, String displayName) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		item.setItemMeta(meta);
	}

	// 指定された文字配列を指定されたアイテム説明文に付与する
	public static void setLore(ItemStack item, String lore_) {
		List<String> lore = new ArrayList<String>();
		lore.add(lore_);
		setLore(item, lore);
	}

	public static void setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public static void addLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		List<String> n_lore = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
		n_lore.addAll(lore);
		meta.setLore(n_lore);
		item.setItemMeta(meta);
	}

	/**エンチャント状態にします．
	 *
	 * @param item
	 */
	public static void addEnchant(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DIG_SPEED, 10, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
	}

	// 文字列からアイテムのマテリアルを作成
	public static Material StringToMaterialData(String name) {
		Material ret = null;
		try {
			if (name != null) {
				// 全て大文字にする
				ret = Material.valueOf(name.toUpperCase());
			}
		} catch (IllegalArgumentException e) {
			Bukkit.getLogger().warning(name + " というマテリアルは存在しません．");
		}
		return ret;
	}

	// コアプロテクトAPIを返す
	public static CoreProtectAPI getCoreProtect() {
		Plugin plugin = Bukkit.getServer().getPluginManager()
				.getPlugin("CoreProtect");

		// Check that CoreProtect is loaded
		if (plugin == null || !(plugin instanceof CoreProtect)) {
			return null;
		}

		// Check that the API is enabled
		CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
		if (CoreProtect.isEnabled() == false) {
			return null;
		}

		// Check that a compatible version of the API is loaded
		if (CoreProtect.APIVersion() < 4) {
			return null;
		}

		return CoreProtect;
	}

	// ワールドガードAPIを返す
	public static WorldGuardPlugin getWorldGuard() {
		Plugin pl = Bukkit.getServer().getPluginManager()
				.getPlugin("WorldGuard");
		if (pl instanceof WorldGuardPlugin)
			return (WorldGuardPlugin) pl;
		else
			return null;
	}

	// ワールドエディットAPIを返す
	public static WorldEditPlugin getWorldEdit() {
		Plugin pl = Bukkit.getServer().getPluginManager()
				.getPlugin("WorldEdit");
		if (pl instanceof WorldEditPlugin)
			return (WorldEditPlugin) pl;
		else
			return null;
	}

	// ZenchantmentAPIを返す
	public static Zenchantments getZenchantments() {
		Plugin pl = Bukkit.getServer().getPluginManager()
				.getPlugin("Zenchantments");
		if (pl instanceof Zenchantments)
			return (Zenchantments) pl;
		else
			return null;
	}

	public static void sendEveryMessage(String str) {
		Gigantic plugin = Gigantic.plugin;
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			player.sendMessage(str);
		}
	}

	public static void sendEverySound(Sound str, float a, float b) {
		Gigantic plugin = Gigantic.plugin;
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			player.playSound(player.getLocation(), str, a, b);
		}
	}



	// 固体ではないブロック類を返す
	public static Set<Material> tpm = new HashSet<Material>(Arrays.asList(
			Material.AIR, Material.WATER, Material.LAVA,
			Material.STATIONARY_WATER, Material.STATIONARY_LAVA));

	public static Set<Material> getFluidMaterials() {
		return tpm;
	}

}
