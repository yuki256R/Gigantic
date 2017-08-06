package com.github.unchama.listener.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.item.items.ManaApple;
import com.github.unchama.item.moduler.ManaEffect;
import com.github.unchama.item.moduler.NBTTag;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.util.OldUtil;

/**
 * @author karayuu
 */
public class GachaAppleListener implements Listener, NBTTag {

	@EventHandler
	public void onPlayerGachaAppleConsumeEvent(PlayerItemConsumeEvent event) {
		Player p = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);

		if (gp == null) {
			event.setCancelled(true);
			return;
		}
		ItemStack is = event.getItem();
		ManaEffect effect = null;
		if (this.containNBTTag(is, ManaApple.MANAAPPLENBT)) {
			effect = this.getNBTTagValue(is, ManaApple.MANAAPPLENBT,
					ManaEffect.class);
			effect.run(p);
		} else {
			effect = OldUtil.getOldGachaAppleManaEffect(event);
			if (effect != null)
				ManaEffect.run(p, effect);
		}
	}


}
