package com.github.unchama.listener;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import com.github.unchama.gigantic.UserManager;
import com.github.unchama.player.mineblock.BlockType;

public class PlayerStatisticListener implements Listener {

	@EventHandler
	public void StatisticIncrementListener(PlayerStatisticIncrementEvent event){
		if(event.getStatistic().equals(Statistic.MINE_BLOCK)){
			Material material = event.getMaterial();
			if(BlockType.contains(material)){
				UserManager.getGiganticPlayer(event.getPlayer()).getMineBlockManager().increase(material);
			}
		}
	}

}
