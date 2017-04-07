package com.github.unchama.hook;

import me.clip.placeholderapi.external.EZPlaceholderHook;

import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.ExplosionManager;

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
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);
		if(gp == null){
			return "";
		}
		//player型を使うPlaceholder

		switch(identifier){
		case "name":
			return gp.name;
		case "seichilevel":
			return "" + (1 + gp.getManager(SeichiLevelManager.class).getLevel());
		case "skill_explosion_jpname":
			return gp.getManager(ExplosionManager.class).getJPName();
		default:
			return "";
		}
	}

}
