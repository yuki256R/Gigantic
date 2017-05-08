package com.github.unchama.listener;

import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.github.unchama.yml.HuntingPointDataManager;

public class HuntingPointEventListener implements Listener {

	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	HuntingPointDataManager huntingPointData = Gigantic.yml
			.getManager(HuntingPointDataManager.class);


//	// モンスターを攻撃したとき
//	@EventHandler
//	public void onDamage(EntityDamageByEntityEvent event){
//		RaidHuntingManager.Instance().onAttack(event);
//	}

	// モンスターを倒した時
	@EventHandler
	public void onKill(EntityDeathEvent event) {

		if (/* !(event.getEntity() instanceof Monster) || */!(event.getEntity()
				.getKiller() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity().getKiller();

		String name = event.getEntity().getName();
		String message = name;

		name = nameConvert(name, event);
		if (huntingPointData.isHuntMob(name)) {
			message += " 狩猟対象";
		} else {
			message += " 要らない子";
		}

		// ポイントの追加
		int addPoint = 1;

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		HuntingPointManager huntingPointManager = gp
				.getManager(HuntingPointManager.class);
		huntingPointManager.addPoint(name, addPoint);

		debug.sendMessage(player, DebugEnum.HUNT, message);
	}

	// 同種扱い、別種扱いの名前を変換
	@SuppressWarnings("deprecation")
	private String nameConvert(String name, EntityDeathEvent event) {
		String ret = name;
		// ウィザースケルトン
		if ((event.getEntity() instanceof Skeleton)) {
			// ウィザスケはスケルトンクラスのタイプの違いを比較しないと名前がわからない
			if (((Skeleton) event.getEntity()).getSkeletonType() == Skeleton.SkeletonType.WITHER) {
				ret = "WitherSkeleton";
			}

			// 同種扱い
		} else {
			ret = huntingPointData.ConvertName(name);
		}
		// //エルダーガーディアン
		// if ((event.getEntity() instanceof Guardian)){
		// message += " : " + (((Guardian)event.getEntity()).isElder() ?
		// ":elder" : "not elder");
		// }
		return ret;
	}
}
