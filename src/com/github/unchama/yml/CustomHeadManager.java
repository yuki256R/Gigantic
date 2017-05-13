package com.github.unchama.yml;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.util.Util;
import com.github.unchama.yml.moduler.YmlManager;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class CustomHeadManager extends YmlManager {

	public class CustomHead {
		public String name; // 呼び出し名の逆引き
		public String dispName; // 表示名
		public String category; // カテゴリ
		public String url; // url
		private ItemStack skull; // 頭

		public CustomHead(String name_, String dispName_, String category_,
				String url_, ItemStack skull_) {
			name = name_;
			dispName = dispName_;
			category = category_;
			url = url_;
			skull = skull_;
		}

		public ItemStack getSkull() {
			return skull.clone();
		}
	}

	public class headCategory {
		public String name; // 呼び出し名の逆引き
		public String dispName; // 表示名

		public headCategory(String name_, String dispName_) {
			name = name_;
			dispName = dispName_;
		}
	}

	// Map.keysetで回すと順番が変わるため
	static private List<String> headArray;
	static private Map<String, CustomHead> customHeads;
	// カテゴリごとのリスト
	static private Map<String, List<CustomHead>> headCategory;

	static private Map<String, headCategory> categoryData;

	// コンストラクタ
	public CustomHeadManager() {
		super();
		reload();
	}

	@Override
	protected void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
	}

	// ymlファイルからデータを取りなおす
	public void reload() {
		// ヘッドデータ
		headArray = new ArrayList<String>();
		customHeads = new HashMap<String, CustomHead>();
		headCategory = new HashMap<String, List<CustomHead>>();

		ConfigurationSection basedata = this.fc
				.getConfigurationSection("mobhead");

		// ヘッド生成
		for (String name : basedata.getKeys(false)) {
			headArray.add(name);
			String dispname = basedata.getString(name + ".dispname");
			String category = basedata.getString(name + ".category");
			String url = basedata.getString(name + ".url");
			ItemStack skull = getSkull(url);
			Util.setDisplayName(skull, ChatColor.RESET + dispname);
			// Bukkit.getServer().getLogger()
			// .info("name : " + name + ", dispname: " + dispname + ", url : " +
			// url + "," + (skull != null));
			CustomHead headData = new CustomHead(name, dispname, category, url,
					skull);
			customHeads.put(name, headData);
			// カテゴリに追加
			if (!headCategory.containsKey(category)) {
				headCategory.put(category, new ArrayList<CustomHead>());
			}
			headCategory.get(category).add(headData);
		}

		// カテゴリデータ
		categoryData = new HashMap<String, headCategory>();
		ConfigurationSection categorydata = this.fc
				.getConfigurationSection("mobhead");

		for (String name : categorydata.getKeys(false)) {
			String dispname = basedata.getString(name + ".dispname");
			categoryData.put(name, new headCategory(name, dispname));
		}
	}

	/**
	 * 与えられた文字に合致する頭を取得する．
	 *
	 * @param s
	 * @return
	 */
	public ItemStack getMobHead(String name) {
		ItemStack ans = customHeads.get(name).skull;
		if (ans == null) {
			notFindWarning(name);
			return customHeads.get("grass").skull.clone();
		} else {
			return ans.clone();
		}
	}

	/**
	 * 与えられた文字に合致する頭のURLを付与する．
	 *
	 * @param s
	 * @return
	 */
	public void setSkull(ItemStack skull, String name) {
		if (!customHeads.containsKey(name)) {
			notFindWarning(name + "という名前のCustomHeadは見つかりません．");
			return;
		}
		setURL(skull, customHeads.get(name).url);
	}

	/**
	 * 与えられた文字に合致する頭のURLを取得する．
	 *
	 * @param name
	 * @return
	 */
	public String getURL(String name) {
		if (!customHeads.containsKey(name)) {
			notFindWarning(name + "という名前のCustomHeadは見つかりません．");
			return null;
		}
		return customHeads.get(name).url;

	}

	/**
	 * 与えられたMOBHEADにURLを付与する．
	 *
	 * @param skull
	 * @param url
	 */
	private void setURL(ItemStack skull, String url) {
		// privateに変更して動作の担保が取れたためコメントアウト
//		// 不正なURLをセットすると、表示したクライアントがクラッシュするため
//		if (url == null || !URLMap.containsValue(url)) {
//			return;
//		}

		ItemMeta meta = skull.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.getEncoder()
				.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url)
						.getBytes());
		profile.getProperties().put("textures",
				new Property("textures", new String(encodedData)));
		Field profileField;
		try {
			profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException
				| IllegalAccessException e1) {
			e1.printStackTrace();
		}
		skull.setItemMeta(meta);
	}

	// カスタム頭取得
	private ItemStack getSkull(String url) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		setURL(skull, url);
		return skull;
	}

	// 指定したカテゴリのデータの配列を返す
	public List<CustomHead> getCategoryHeads(String category) {
		if (headCategory.containsKey(category)) {
			notFindWarning(category + "という名前のCustomHeadCategoryは見つかりません．");
			return headCategory.get("other");
		}

		return headCategory.get(category);
	}

	// nameに一致する頭が見つからなかった時のwarningメッセージ
	private void notFindWarning(String text) {
		Bukkit.getServer().getLogger().warning(text);
	}
}
