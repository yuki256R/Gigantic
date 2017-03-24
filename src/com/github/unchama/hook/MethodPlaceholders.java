package com.github.unchama.hook;

import me.clip.placeholderapi.external.EZPlaceholderHook;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;

public class MethodPlaceholders extends EZPlaceholderHook{

	public MethodPlaceholders(Plugin plugin) {
		super(plugin, "m");
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if(p == null){
			return "";
		}
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);
		if(gp == null){
			return "";
		}
		//player型を使うPlaceholder

		switch(identifier){
		default:
			return "";
		}
	}

}
