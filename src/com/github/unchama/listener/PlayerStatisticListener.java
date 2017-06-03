package com.github.unchama.listener;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.BlockType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class PlayerStatisticListener implements Listener {
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler(priority = EventPriority.MONITOR)
	public void StatisticIncrementListener(PlayerStatisticIncrementEvent event){
		if(event.isCancelled())return;
		if(event.getStatistic().equals(Statistic.MINE_BLOCK)){
			Material material = event.getMaterial();
			int diff = event.getNewValue() - event.getPreviousValue();
			if(BlockType.contains(material)){
				Player p = event.getPlayer();
				GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);
				if(gp == null){
					return ;
				}
				gp.getManager(MineBlockManager.class).increase(material,diff);
				debug.sendMessage(p, DebugEnum.MINEBLOCK, material.name() + " is increment(" + diff + ")for player:" + p.getName());
			}
		}
	}
}
