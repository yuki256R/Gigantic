package com.github.unchama.achievement.achievements;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;

import de.tr7zw.itemnbtapi.NBTItem;

public final class FullTicketAchievement extends GiganticAchievement implements Listener {

	private final int id;


	public FullTicketAchievement(int id) {
		super();
		this.id = id;
	}

	@Override
	public int getID() {
		return this.id;
	}



	@EventHandler(priority = EventPriority.MONITOR)
	public void InventoryOpenListener(InventoryOpenEvent event){
		HumanEntity he = event.getPlayer();
		if(he instanceof Player){
			Player p = (Player) he;
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);

			PlayerInventory pinv = p.getInventory();
			for(int s = 0 ; s < 36 ; s ++){
				ItemStack is = pinv.getItem(s);
				NBTItem nbti = new NBTItem(is);
				// gacha券tagを判定
				if (!GachaManager.isTicket(nbti)) {
					return;
				}
			}
			this.unlockAchievement(gp);
		}
	}

	@Override
	public String getUnlockInfo() {
		return "インベントリを全てガチャ券で埋める";
	}

	@Override
	public String getLockInfo() {
		return ChatColor.MAGIC + "????????????";
	}
}
