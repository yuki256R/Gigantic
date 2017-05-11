package com.github.unchama.yml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.huntingpoint.HuntingPointShopItem;
import com.github.unchama.yml.moduler.YmlManager;

public class HuntingPointDataManager extends YmlManager {
	public class HuntMobData {
		public String name; // 呼び出し名の逆引き
		public String jpName; // 日本語名
		public String headName; // MobHeadで呼び出すための名前
		public boolean isTarget; // 狩猟対象ならtrue

		public HuntMobData(String name_, String jpName_, String headName_, boolean isTarget_) {
			name = name_;
			jpName = jpName_;
			headName = headName_;
			isTarget = isTarget_;
		}
	}

	static Map<String, List<HuntingPointShopItem>> shopItems;

	static Map<String, HuntMobData> MobNames;
	// Map.keysetで回すと順番が変わるため
	static List<String> MobNameArray;

	static Map<String, String> ConvertNames;

	// コンストラクタ
	public HuntingPointDataManager() {
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
		// ドロップ対象のMob名
		MobNameArray = new ArrayList<String>();

		// 表示データ
		ConfigurationSection basedata = this.fc
				.getConfigurationSection("mobdata");
		MobNames = new HashMap<String, HuntMobData>();
		for (String name : basedata.getKeys(false)) {
			boolean isTarget = false;
			if (basedata.getBoolean(name + ".target")) {
				isTarget = true;
				MobNameArray.add(name);
			}

			String jpname = basedata.getString(name + ".jpname");
			String headname = basedata.getString(name + ".headname");
			MobNames.put(name, new HuntMobData(name, jpname, headname, isTarget));
		}

		// 同種判定のリスト
		ConvertNames = new HashMap<String, String>();
		List<String> convertName = this.fc.getStringList("huntmob_convert");
		for (String name : convertName) {
			String[] n = name.split(" : ");
			if (n.length == 2) {
				ConvertNames.put(n[0], n[1]);
			}
		}

		// ショップのアイテム
		shopItems = new HashMap<String, List<HuntingPointShopItem>>();
		for (String name : MobNames.keySet()) {
			List<HuntingPointShopItem> list = new ArrayList<HuntingPointShopItem>();
			int count = 1;
			boolean isLoop = true;
			while (isLoop) {
				String path = "shop." + name + "." + count;
				String str = this.fc.getString(path + ".category", "");
				// Bukkit.getServer().getLogger().info(path + " : " + str);
				if (str != "") {
					HuntingPointShopItem item = getShopItem(path, name);
					// データが不足していなければ追加
					if (item.isEnable()) {
						list.add(item);
					} else {
						Bukkit.getServer().getLogger()
								.warning(path + " : disable");
					}
					count++;
				} else {
					isLoop = false;
				}
			}
			shopItems.put(name, list);
		}
	}

	// ショップのアイテムを取得
	private HuntingPointShopItem getShopItem(String path, String name) {
		HuntingPointShopItem ret = new HuntingPointShopItem();
		ret.setCategory(this.fc.getString(path + ".category"));
		ret.setPrice(this.fc.getInt(path + ".price", 0));
		// ret.setLogName(this.fc.getString(path + ".logname"));
		ret.setMeta(this.fc.getString(path + ".meta"));
		ItemStack item = this.fc.getItemStack(path + ".itemstack", null);
		String headName = "";
		if (ret.getCategoryType() != null && item != null) {
			switch (ret.getCategoryType()) {
			case ToHead:
				headName = MobNames.get(name).headName;
				Gigantic.yml.getManager(CustomHeadDataManager.class).setSkull(
						item, headName);
				break;
			case CustomHead:
				headName = this.fc.getString(path + ".headname", "");
				Gigantic.yml.getManager(CustomHeadDataManager.class).setSkull(
						item, headName);
				break;
			case Item:
				break;
			default:
				break;
			}
		}

		ret.setItemStack(item);
		return ret;
	}

	// ショップのアイテムリストを取得
	public List<HuntingPointShopItem> getShopItems(String name) {
		List<HuntingPointShopItem> ret = null;
		if (shopItems.containsKey(name)) {
			ret = shopItems.get(name);
		}
		return ret;
	}

	// 狩猟対象か否か
	public boolean isHuntMob(String name) {
		reload();
		name = ConvertName(name);
		if(!MobNames.containsKey(name)){
			return false;
		}

		return MobNames.get(name).isTarget;
	}

	// 同種として扱われるMob名の変換
	public String ConvertName(String name) {
		String ret = name;
		if (ConvertNames.containsKey(name)) {
			ret = ConvertNames.get(name);
		}
		// 「Magma Cube」が半角スペースが入っているせいでそちらに合わせると
		// SQL周りで不具合が起こるためこちらで吸い取る
		ret = ret.replace(" ", "");
		return ret;
	}

	public Map<String, HuntMobData> getMobNames() {
		return MobNames;
	}

	public HuntMobData getMobData(String name) {
		return MobNames.get(name);
	}

	public List<String> getMobNameArray() {
		return MobNameArray;
	}
}
