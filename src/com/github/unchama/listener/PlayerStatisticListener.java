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
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.mineblock.BlockType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

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
				debug.sendMessage(p, DebugEnum.MINEBLOCK, material.name() + "is increment(" + diff + ")for player:" + p.getName());
				if(gp.getManager(SeichiLevelManager.class).updateLevel()){
					int level = gp.getManager(SeichiLevelManager.class).getLevel();
					gp.getManager(ManaManager.class).Levelup();
					gp.getManager(SideBarManager.class).updateInfo(Information.SEICHI_LEVEL,level);
				}
				double rb = gp.getManager(SeichiLevelManager.class).getRemainingBlock();
				gp.getManager(SideBarManager.class).updateInfo(Information.MINE_BLOCK, rb);
				gp.getManager(SideBarManager.class).refresh();
			}
		}
	}

}
