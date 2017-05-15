package com.github.unchama.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class PlayerPickupItemListener implements Listener {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler(priority = EventPriority.HIGHEST)
	public void addMineStack(PlayerPickupItemEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

		if (gp == null)
			return;

		if (!gp.getStatus().equals(GiganticStatus.AVAILABLE))
			return;
		Item item = event.getItem();
		ItemStack dropitem = item.getItemStack().clone();

		debug.sendMessage(player, DebugEnum.BREAK, "your item is catched");

		MineStackManager m = gp.getManager(MineStackManager.class);
		if (m.add(dropitem)) {
			debug.sendMessage(player, DebugEnum.MINESTACK,
					"your item is added in minestack");
			//拾った音を再生
			player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP,
					(float) 1, (float) 1);
			item.remove();
			event.setCancelled(true);
		}

	}
}
