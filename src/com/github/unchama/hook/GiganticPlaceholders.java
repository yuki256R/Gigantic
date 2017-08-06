package com.github.unchama.hook;

import me.clip.placeholderapi.external.EZPlaceholderHook;

import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.exp.ExpManager;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.point.GiganticPointManager;
import com.github.unchama.player.point.UnchamaPointManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.active.ExplosionManager;
import com.github.unchama.player.time.PlayerTimeManager;

/**
 * @author tar0ss
 *
 */
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
		case "allmineblock":
			return Integer.toString((int)gp.getManager(MineBlockManager.class).getAllignoreDebug());
		case "totaljoin":
			return "" + gp.getManager(PlayerTimeManager.class).getTotalJoin();
		case "chainjoin":
			return "" + gp.getManager(PlayerTimeManager.class).getChainJoin();
		case "exp":
			return "" + gp.getManager(ExpManager.class).getExp();
		case "unchamapoint":
			return "" + gp.getManager(UnchamaPointManager.class).getPoint();
		case "giganticpoint":
			return "" + gp.getManager(GiganticPointManager.class).getPoint();
		case "abilitypoint":
			return "" + gp.getManager(SeichiLevelManager.class).getAP();
		default:
			return "";
		}
	}

}
