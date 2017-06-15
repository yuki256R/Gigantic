package com.github.unchama.listener;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.task.FootBlockBreakTaskRunnable;
import com.github.unchama.yml.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.SecondEvent;
import com.github.unchama.gigantic.PlayerManager;

import java.util.List;

/**
 * @author tar0ss
 *
 */
public class SecondListener implements Listener{
    private Gigantic plugin = Gigantic.plugin;

	@EventHandler
	public void GiganticLoadListener(SecondEvent event){
		//5秒に１回実行する．
		if(event.getSecond() % 5 != 0){
			return;
		}
		PlayerManager.multiload();
	}

	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	@EventHandler
    public void footBlockBreak(SecondEvent event) {
        List<Player> playerlist = event.getOnlinePlayers();

	    //Configから指定した時間に1回実行する
        if (event.getSecond() % config.getSkywalkBreakSec() != 0) {
            return;
        }
        new FootBlockBreakTaskRunnable(playerlist).runTaskTimerAsynchronously(plugin, 0, 1);
    }
}
