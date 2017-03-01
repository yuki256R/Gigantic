package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.SecondEvent;
import com.github.unchama.gigantic.Gigantic;

public class SecondListener implements Listener{
	private Gigantic plugin = Gigantic.plugin;
	
	@EventHandler
	public void GiganticLoadListener(SecondEvent event){
		
	}
}
