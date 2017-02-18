package com.github.unchama.hook;

import me.clip.placeholderapi.external.EZPlaceholderHook;

import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.UserManager;
import com.github.unchama.player.GiganticPlayer;

public class GiganticPlaceholders extends EZPlaceholderHook{
	Gigantic plugin = Gigantic.plugin;
	
	public GiganticPlaceholders(Gigantic plugin) {
		super(plugin, "gp");
	}


	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		//player型を使わないPlaceholder
		
		if(p == null){
			return "";
		}
		GiganticPlayer gp = UserManager.getGiganticPlayer(p);
		//player型を使うPlaceholder
		
		switch(identifier){
		case "name":
			return gp.name;
		default:
			return "";	
		}
	}

}
