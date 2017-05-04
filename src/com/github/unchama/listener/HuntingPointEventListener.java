package com.github.unchama.listener;

import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.github.unchama.yml.HuntingPointDataManager;

public class HuntingPointEventListener implements Listener{

	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	HuntingPointDataManager huntingPointData = Gigantic.yml.getManager(HuntingPointDataManager.class);

	// モンスターを倒した時
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onKill(EntityDeathEvent event) {

		if (/*!(event.getEntity() instanceof Monster) || */!(event.getEntity().getKiller() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity().getKiller();
		//GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

		String name = event.getEntity().getName();
		String message = name;
		if ((event.getEntity() instanceof Skeleton)){
			message += " : " + ((Skeleton)event.getEntity()).getSkeletonType().toString();
		}
		if ((event.getEntity() instanceof Guardian)){
			message += " : " + (((Guardian)event.getEntity()).isElder() ? ":elder" : "not elder");
		}
		if(huntingPointData.isHuntMob(name)){
			message += " 狩猟対象";
		}else{
			message += " 要らない子";
		}
		debug.sendMessage(player,DebugEnum.BUILD,message);
		debug.sendMessage(player,DebugEnum.BUILD,huntingPointData.test1());
		debug.sendMessage(player,DebugEnum.BUILD,huntingPointData.test2());

	}
}
