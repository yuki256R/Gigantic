package com.github.unchama.yml;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.unchama.yml.moduler.YmlManager;

public class HuntingPointDataManager extends YmlManager {
	static List<String> MobNames;
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

	//ymlファイルからデータを取りなおす
	public void reload() {
		// ドロップ対象のMob名
		String[] mobName = this.fc.getString("huntmob", "").split(",");
		MobNames = Arrays.asList(mobName);

		// 同種判定のリスト
		ConvertNames = new HashMap<String, String>();
		String[] convertName = this.fc.getString("huntmob_convert", "").split(",");
		for(String name : convertName){
			String[] n = name.split("=");
			if(n.length == 2){
				ConvertNames.put(n[0], n[1]);
			}
		}
		//debug.info(DebugEnum.SQL, "ドロップ対象のMob名 : " + this.fc.getString("huntMob", ""));
		//debug.info(DebugEnum.SQL, "同種判定のリスト" + this.fc.getString("huntmob_convert", ""));
	}

	public String test1(){
		return this.fc.getString("huntMob", "");
	}

	public String test2(){
		return this.fc.getString("huntmob_convert", "");
	}

	//狩猟対象か否か
	 public boolean isHuntMob(String name){
		 reload();
		 boolean ret = false;
		 name = ConvertName(name);

		 ret = MobNames.contains(name);
		 return ret;
	 }

	 //同種として扱われるMob名の変換
	 public String ConvertName(String name){
		 String ret = name;
		 if(ConvertNames.containsKey(name)){
			 ret = ConvertNames.get(name);
		 }
		 return ret;
	 }

	 public List<String> getMobNames(){
		 return MobNames;
	 }
}
