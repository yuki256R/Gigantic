package com.github.unchama.yml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gui.huntingpoint.HuntingPointShopItem;
import com.github.unchama.util.MobHead;
import com.github.unchama.yml.moduler.YmlManager;

public class HuntingPointDataManager extends YmlManager {
	public class HuntMobBaseData {
		public String jpName; // 日本語名
		public String headName; // MobHeadで呼び出すための名前

		public HuntMobBaseData(String jpName_, String headName_) {
			jpName = jpName_;
			headName = headName_;
		}
	}

	static Map<String, List<HuntingPointShopItem>> shopItems;

	static Map<String, HuntMobBaseData> MobNames;
	//Map.keysetで回すと順番が変わるため
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
		MobNames = new HashMap<String, HuntMobBaseData>();
		MobNameArray = new ArrayList<String>();
		List<String> baseData = this.fc.getStringList("huntmob");
		for (String data : baseData) {
			String[] d = data.split(" : ");
			if (d.length == 3) {
				MobNameArray.add(d[0]);
				MobNames.put(d[0], new HuntMobBaseData(d[1], d[2]));
			}
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
		String url = "";
		if (ret.getCategoryType() != null && item != null) {
			switch (ret.getCategoryType()) {
			case ToHead:
				String headName = MobNames.get(name).headName;
				url = MobHead.getMobURL(headName);
				MobHead.setURL(item, url);
				break;
			case CustomHead:
				url = MobHead.getMobURL(this.fc.getString(path
						+ ".headname", ""));
				MobHead.setURL(item, url);
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
		boolean ret = false;
		name = ConvertName(name);

		ret = MobNames.containsKey(name);
		return ret;
	}

	// 同種として扱われるMob名の変換
	public String ConvertName(String name) {
		String ret = name;
		if (ConvertNames.containsKey(name)) {
			ret = ConvertNames.get(name);
		}
		return ret;
	}

	public Map<String, HuntMobBaseData> getMobNames() {
		return MobNames;
	}

	public HuntMobBaseData getMobData(String name) {
		return MobNames.get(name);
	}

	public List<String> getMobNameArray(){
		return MobNameArray;
	}
}
