package com.github.unchama.listener;

import com.github.unchama.gigantic.Gigantic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.SecondEvent;
import com.github.unchama.gigantic.PlayerManager;

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
}
