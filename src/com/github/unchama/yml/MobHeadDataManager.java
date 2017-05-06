package com.github.unchama.yml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.unchama.yml.moduler.YmlManager;

public class MobHeadDataManager extends YmlManager{

	public class MobHeadData{
		public String name;
		public String url;

		public MobHeadData(String name_, String url_){
			name = name_;
			url = url_;
		}
	}

	static private Map<String, MobHeadData> headData;

	// コンストラクタ
	public MobHeadDataManager() {
		super();
		reload();
	}

	@Override
	protected void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
	}

	private void reload(){
		headData = new HashMap<String, MobHeadData>();
		List<String> data_ = this.fc.getStringList("mobhead");
		for(String line : data_){
			String[] data = line.split(" : ");
			if(data.length == 3){
				headData.put(data[0], new MobHeadData(data[1], data[2]));
			}
		}
	}

	public Map<String, MobHeadData> getMap(){
		return headData;
	}

	public MobHeadData getData(String id){
		return headData.get(id);
	}
}
