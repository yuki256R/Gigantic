package com.github.unchama.task;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.command.presentboxCommand;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.player.PresentBoxTableManager;

public class PresentAllPlayerTaskRunnable extends BukkitRunnable {
	Gigantic plugin = Gigantic.plugin;
	private Player player;
	private ItemStack item;
	private List<String> uuids;

	private int size;
	private int count;

	public PresentAllPlayerTaskRunnable(Player player_, ItemStack item_){
		player = player_;
		item = item_;

		uuids = Gigantic.sql.getManager(PresentBoxTableManager.class).getUUIDs();
		count = -1;
		size = uuids.size();
	}

	@Override
	public void run() {
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				count++;
				if (count >= size) {
					player.sendMessage(size + " 名にプレゼントを贈りました.");
					cancel();
					return;
				} else {

				}
				String uuid = uuids.get(count);
				presentboxCommand.sendItem(player, uuid, item);
			}
		});
	}

}
