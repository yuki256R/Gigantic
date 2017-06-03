package com.github.unchama.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.player.time.PlayerTimeManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.github.unchama.yml.HuntingPointDataManager;

public class HuntingPointEventListener implements Listener {

	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	HuntingPointDataManager huntingPointData = Gigantic.yml
			.getManager(HuntingPointDataManager.class);

	// // モンスターを攻撃したとき
	// @EventHandler
	// public void onDamage(EntityDamageByEntityEvent event){
	// RaidHuntingManager.Instance().onAttack(event);
	// }

	// モンスターを倒した時
	@EventHandler
	public void onKill(EntityDeathEvent event) {

		if (/* !(event.getEntity() instanceof Monster) || */!(event.getEntity()
				.getKiller() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity().getKiller();

		String name = event.getEntity().getName();

		name = nameConvert(name, event);

		String message = name;
		if (huntingPointData.isHuntMob(name)) {
			message += " 狩猟対象";
		} else {
			message += " 要らない子";
		}

		// ポイントの追加
		int addPoint = 1;
		double distance = huntingPointData.getMobData(name).raidDistance;
		message += " raid : " + distance;
		if (distance > 0) {
			// ボス
			GivePointByRaidBoss(event.getEntity(), name, distance, addPoint);
		} else {
			// 通常Mob
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			HuntingPointManager huntingPointManager = gp
					.getManager(HuntingPointManager.class);

			// フライ中は無効
			if(player.isFlying()){
				huntingPointManager.FlyWarning();
				return;
			}

			huntingPointManager.addPoint(name, addPoint);
		}
		debug.sendMessage(player, DebugEnum.HUNT, message);

	}

	// 近くにいるプレイヤー全員にポイントを付与
	private void GivePointByRaidBoss(Entity entity, String name, Double distance, int addPoint) {
		for (Entity e : entity.getNearbyEntities(distance, distance, distance)) {
			if (!(e instanceof Player)) {
				continue;
			}
			Player player = (Player) e;
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			// 放置中のプレイヤーは除外
			if(gp.getManager(PlayerTimeManager.class).isIdle()){
				continue;
			}

			HuntingPointManager huntingPointManager = gp
					.getManager(HuntingPointManager.class);

			// フライ中は無効
			if(player.isFlying()){
				huntingPointManager.FlyWarning();
				continue;
			}
			huntingPointManager.addPoint(name, addPoint);

			player.sendMessage(huntingPointData.getMobData(name).jpName
					+ " が討伐されたため,狩猟ポイント " + addPoint + " が付与されました");
		}
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
			// エルダーガーディアン
		} else if ((event.getEntity() instanceof Guardian)) {
			if (((Guardian) event.getEntity()).isElder()) {
				ret = "ElderGuardian";
			}
			// 同種扱い
		} else {
			ret = huntingPointData.ConvertName(name);
		}

		return ret;
	}
}
