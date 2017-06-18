package com.github.unchama.yml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

import com.github.unchama.yml.moduler.YmlManager;

/**
*
* @author ten_niti
*
*/
public class FishingYmlManager extends YmlManager {

	// 最大釣りレベル
	private int maxFishingLevel;
	// 放置釣り経験値
	private int idleFishingExp;
	// 自力釣り経験値
	private int activeFishingExp;
	// 放置釣りが有効なワールド名
	private List<String> idleFishingEnableWorld;

	// クーラーボックスのレベルごとの容量
	private List<Map.Entry<Integer, Integer>> coolerBoxCapacityList;

	// コンストラクタ
	public FishingYmlManager() {
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
		maxFishingLevel = this.fc.getInt("maxFishingLevel");
		idleFishingExp = this.fc.getInt("idleFishingExp");
		activeFishingExp = this.fc.getInt("activeFishingExp");
		idleFishingEnableWorld = this.fc.getStringList("idleFishingEnableWorld");


		// クーラーボックスのレベルごとの容量
		Map<Integer, Integer> capacityMap = new LinkedHashMap<Integer, Integer>();
		ConfigurationSection data = this.fc.getConfigurationSection("coolerBoxCapacity");
		for (String lvStr : data.getKeys(false)) {
			int lv = Integer.valueOf(lvStr);
			int capa = data.getInt(lvStr);
			capacityMap.put(lv, capa);
		}

		// List 生成 (ソート用)
		coolerBoxCapacityList = new ArrayList<Map.Entry<Integer, Integer>>(
				capacityMap.entrySet());
		Collections.sort(coolerBoxCapacityList,
				new Comparator<Map.Entry<Integer, Integer>>() {

					@Override
					public int compare(Entry<Integer, Integer> entry1,
							Entry<Integer, Integer> entry2) {
						return ((Integer) entry2.getValue())
								.compareTo((Integer) entry1.getValue());
					}
				});
	}

	public int getMaxFishingLevel(){
		return maxFishingLevel;
	}

	public int getIdleFishingExp(){
		return idleFishingExp;
	}

	public int getActiveFishingExp(){
		return activeFishingExp;
	}

	// 放置釣りが有効なワールドならtrue
	public boolean isIdleFishingEnableWorld(String worldName){
		return idleFishingEnableWorld.contains(worldName);
	}

	// レベルに応じたインベントリの容量を返す
	public int getCapacity(int level){
		int ret = 0;
		for(int i = 0; i < coolerBoxCapacityList.size(); i++){
			if(level >= coolerBoxCapacityList.get(i).getKey()){
				ret = coolerBoxCapacityList.get(i).getValue();
				break;
			}
		}

		return ret;
	}
}
