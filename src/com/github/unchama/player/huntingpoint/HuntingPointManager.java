package com.github.unchama.player.huntingpoint;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.unchama.event.HuntingPointIncrementEvent;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.HuntingPointTableManager;

/**
*
* @author ten_niti
*
*/
public class HuntingPointManager extends DataManager implements UsingSql {
	private Map<String, Integer> currentPoints = new HashMap<String, Integer>();
	private Map<String, Integer> totalPoints = new HashMap<String, Integer>();

	// フライ中はポイントが入らない旨を説明したか
	private boolean isFlyWarned = false;

	//どのMobのショップを開くか
	private String shopMobName;


	HuntingPointTableManager hm = sql
			.getManager(HuntingPointTableManager.class);

	public HuntingPointManager(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public void save(Boolean loginflag) {
		hm.save(gp, loginflag);
	}

	//討伐時にポイントを加算する
	public void addPoint(String key, int value){
		int currentPoint = 0;
		int totalPoint = 0;
		if(currentPoints.containsKey(key) && totalPoints.containsKey(key)){
			currentPoint = currentPoints.get(key);
			totalPoint = totalPoints.get(key);
		}

		currentPoints.put(key, currentPoint + value);
		totalPoints.put(key, totalPoint + value);

		Bukkit.getPluginManager().callEvent(new HuntingPointIncrementEvent(gp, key, value, currentPoint, totalPoint));
	}

	// 現在ポイントをロード時などに追加する
	public void addCurrent(String key, int value) {
		currentPoints.put(key, value);
	}

	// ショップで支払う
	public void payPoint(String key, int value){
		currentPoints.put(key, currentPoints.get(key) - value);
	}

	// 現在ポイントのsetterとgetter
//	private void setCurrentPoint(String key, int value) {
//		if(!currentPoints.containsKey(key)){
//			return;
//		}
//		currentPoints.put(key, value);
//	}
	public int getCurrentPoint(String key) {
		int ret = 0;
		if (currentPoints.containsKey(key)) {
			ret = currentPoints.get(key);
		}
		return ret;
	}

	// 累計ポイントをロード時などに追加する
	public void addTotal(String key, int value) {
		totalPoints.put(key, value);
	}
	// 累計ポイントのsetterとgetter
//	private void setTotalPoint(String key, int value) {
//		if(!totalPoints.containsKey(key)){
//			return;
//		}
//		totalPoints.put(key, value);
//	}
	public int getTotalPoint(String key) {
		int ret = 0;
		if (totalPoints.containsKey(key)) {
			ret = totalPoints.get(key);
		}
		return ret;
	}


	//どのMobのショップを開くか
	public void setShopMobName(String name){
		shopMobName = name;
	}
	public String getShopMobName(){
		return shopMobName;
	}

	// フライ中はポイントが入らない旨をログイン中1度だけ警告
	public void FlyWarning(){
		if(!isFlyWarned){
			Player player = PlayerManager.getPlayer(gp);
			player.sendMessage("狩猟ポイントはfly中には反映されません.");
			isFlyWarned = true;
		}
	}
}
