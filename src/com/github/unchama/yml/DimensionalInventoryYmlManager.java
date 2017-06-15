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
public class DimensionalInventoryYmlManager extends YmlManager {

	private List<Map.Entry<Integer, Integer>> capacityList;


	public DimensionalInventoryYmlManager() {
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
	private void reload() {
		Map<Integer, Integer> capacityMap = new LinkedHashMap<Integer, Integer>();
		ConfigurationSection data = this.fc.getConfigurationSection("capacity");
		for (String lvStr : data.getKeys(false)) {
			int lv = Integer.valueOf(lvStr);
			int capa = data.getInt(lvStr);
			capacityMap.put(lv, capa);
		}

		// List 生成 (ソート用)
		capacityList = new ArrayList<Map.Entry<Integer, Integer>>(
				capacityMap.entrySet());
		Collections.sort(capacityList,
				new Comparator<Map.Entry<Integer, Integer>>() {

					@Override
					public int compare(Entry<Integer, Integer> entry1,
							Entry<Integer, Integer> entry2) {
						return ((Integer) entry2.getValue())
								.compareTo((Integer) entry1.getValue());
					}
				});
	}

	public int getCapacity(int level){
		int ret = 0;
		for(int i = 0; i < capacityList.size(); i++){
			if(level >= capacityList.get(i).getKey()){
				ret = capacityList.get(i).getValue();
				break;
			}
		}

		return ret;
	}
}
