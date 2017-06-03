package com.github.unchama.raidhunting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.sk89q.worldedit.entity.Player;

//共同でモンスターを倒しても参加者全員にポイントを分配できるようにするクラス
public class RaidHuntingManager {
	static private RaidHuntingManager instance_ = new RaidHuntingManager();
	static public RaidHuntingManager Instance(){
		return instance_;
	}
	private RaidHuntingManager(){

	}

	static private Map<LivingEntity, List<Player>> monsters = new HashMap<LivingEntity, List<Player>>();

	public void onAttack(EntityDamageByEntityEvent event){
		if (!(event.getDamager() instanceof Player)) {
			return;
		}
		Player player = (Player)event.getDamager();
		LivingEntity monster = (LivingEntity) event.getEntity();
		if(!monsters.containsKey(monster)){
			monsters.put(monster, new ArrayList<Player>());
		}
		List<Player> playerList = monsters.get(monster);
		if(!playerList.contains(player)){
			monsters.get(monster).add(player);
		}
	}
}
