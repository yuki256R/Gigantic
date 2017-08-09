package com.github.unchama.yml;

import java.util.HashMap;
import java.util.Map;

import com.github.unchama.achievement.AnotherName;
import com.github.unchama.yml.moduler.YmlManager;

/**
 *
 * @author tar0ss
 *
 */
public class AnotherNameManager extends YmlManager {

	private static Map<Integer,AnotherName> map = new HashMap<Integer,AnotherName>();


	@Override
	protected void saveDefaultFile() {
		// ファイルが存在しない場合作成する
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
	}

	/**生成していなければ生成する．
	 *
	 * @param id
	 * @return
	 */
	public AnotherName getAnotherName(int id) {
		if(map.containsKey(id)){
			return map.get(id);
		}else{
			String idStr = Integer.toString(id);
			AnotherName aN = new AnotherName(
					fc.getString(idStr + ".name"),
					fc.getString(idStr + ".top"),
					fc.getString(idStr + ".middle"),
					fc.getString(idStr + ".bottom"));
			map.put(id, aN);
			return aN;
		}
	}

}
