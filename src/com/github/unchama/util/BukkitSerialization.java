package com.github.unchama.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

/**
 * @author tar0ss
 *
 */
public class BukkitSerialization {
	public static String toBase64(Inventory inventory) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(
					outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(inventory.getSize());
			// Write the name of the inventory
			dataOutput.writeObject(inventory.getName());

			// Save every element in the list
			for (int i = 0; i < inventory.getSize(); i++) {
				dataOutput.writeObject(inventory.getItem(i));
			}

			// Serialize that array
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	public static Inventory getInventoryfromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt(),
					(String) dataInput.readObject());

			// Read the serialized inventory
			for (int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, (ItemStack) dataInput.readObject());
			}
			dataInput.close();
			return inventory;
		} catch (ClassNotFoundException | IOException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	public static String toBase64(ItemStack itemstack) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(
					outputStream);

			dataOutput.writeObject(itemstack);

			// Serialize that array
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stack.", e);
		}
	}

	public static ItemStack getItemStackfromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

			ItemStack is = (ItemStack) dataInput.readObject();
			dataInput.close();
			return is;
		} catch (ClassNotFoundException | IOException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	public static Inventory fromBase64(String data) throws IOException {
		if (data.length() == 0 || data.equals(null)) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "SeichiAssist[四次元ポケットロード処理]でエラー発生");
			Bukkit.getLogger().warning("四次元ポケットのデータがnullです。開発者に報告してください");
			return null;
		}
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt(),
					ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "4次元ポケット");

			// Read the serialized inventory
			for (int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, (ItemStack) dataInput.readObject());
			}
			dataInput.close();
			return inventory;
		} catch (ClassNotFoundException | IOException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	public static String toBase64(List<ItemStack> items) {
		String serial = "";
		try {
			// List検査
			if (!items.isEmpty()) {
				// ByteArray出力ストリーム
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				// Object出力ストリーム
				BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

				// 要素数格納
				dataOutput.writeInt(items.size());
				// アイテム実態格納
				for (ItemStack item : items) {
					dataOutput.writeObject(item);
				}

				// ストリームを閉じる
				dataOutput.close();
				// 変換後のシリアルデータを取得
				serial = Base64Coder.encodeLines(outputStream.toByteArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
			serial = "";
		}
		return serial;
	}

	public static List<ItemStack> getItemStackListfromBase64(String serial) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		try {
			// String検査
			if ((serial.length() != 0) && (!serial.equals(null))) {
				// ByteArray入力ストリーム
				ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(serial));
				// Object入力ストリーム
				BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

				// 要素数格納
				int length = dataInput.readInt();
				// アイテム実態格納
				for (int i = 0; i < length; i++) {
					items.add((ItemStack) dataInput.readObject());
				}

				// ストリームを閉じる
				dataInput.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			items.clear();
		}
		return items;
	}
}
