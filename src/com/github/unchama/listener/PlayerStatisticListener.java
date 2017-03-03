package com.github.unchama.listener;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.BlockType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class PlayerStatisticListener implements Listener {
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler
	public void StatisticIncrementListener(PlayerStatisticIncrementEvent event){
		if(event.getStatistic().equals(Statistic.MINE_BLOCK)){
			Material material = event.getMaterial();
			if(BlockType.contains(material)){
				Player p = event.getPlayer();
				GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);
				if(gp == null){
					return ;
				}
				MineBlockManager mm = gp.getManager(MineBlockManager.class);
				mm.increase(material);
				debug.sendMessage(p, DebugEnum.MINEBLOCK,"called StatisticIncrementListener for player:" + p.getName());
				mm.updateMineBlock();
			}
		}
	}

}
