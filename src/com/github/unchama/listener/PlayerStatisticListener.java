package com.github.unchama.listener;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.mineblock.BlockType;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class PlayerStatisticListener implements Listener {
	private DebugManager debug = Gigantic.yml.getDebugManager();

	@EventHandler
	public void StatisticIncrementListener(PlayerStatisticIncrementEvent event){
		if(event.getStatistic().equals(Statistic.MINE_BLOCK)){
			Material material = event.getMaterial();
			if(BlockType.contains(material)){
				Player p = event.getPlayer();
				PlayerManager.getGiganticPlayer(p).getMineBlockManager().increase(material);
				debug.sendMessage(p, DebugEnum.MINEBLOCK,"called StatisticIncrementListener for player:" + p.getName());
			}
		}
	}

}
