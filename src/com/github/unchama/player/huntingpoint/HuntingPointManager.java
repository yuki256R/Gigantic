package com.github.unchama.player.huntingpoint;

import java.util.HashMap;
import java.util.Map;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.HuntingPointTableManager;

public class HuntingPointManager extends DataManager implements UsingSql {
	private Map<String, Integer> currentPoints = new HashMap<String, Integer>();
	private Map<String, Integer> totalPoints = new HashMap<String, Integer>();

	HuntingPointTableManager hm = sql
			.getManager(HuntingPointTableManager.class);

	public HuntingPointManager(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public void save(Boolean loginflag) {
		hm.save(gp, loginflag);
	}

	// 現在ポイントのsetterとgetter
	public void setCurrentPoint(String key, int value) {
		currentPoints.put(key, value);
	}
	public int getCurrentPoint(String key) {
		int ret = 0;
		if (currentPoints.containsKey(key)) {
			ret = currentPoints.get(key);
		}
		return ret;
	}

	// 累計ポイントのsetterとgetter
	public void setTotalPoint(String key, int value) {
		totalPoints.put(key, value);
	}
	public int getTotalPoint(String key) {
		int ret = 0;
		if (totalPoints.containsKey(key)) {
			ret = totalPoints.get(key);
		}
		return ret;
	}
}
