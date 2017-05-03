package com.github.unchama.yml;

import java.util.ArrayList;
import java.util.List;

import com.github.unchama.yml.moduler.YmlManager;

public class HuntingPointDataManager extends YmlManager {

	static List<String> MobNames;

	//コンストラクタ
	public HuntingPointDataManager() {
		super();
	}

	@Override
	protected void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
			MobNames = new ArrayList<String>();
		}
	}

}
