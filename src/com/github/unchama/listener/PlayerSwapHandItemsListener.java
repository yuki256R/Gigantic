package com.github.unchama.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.seichiskill.active.ActiveSkillToggleMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;

/**
 * @author tar0ss
 *
 */
public class PlayerSwapHandItemsListener implements Listener{
	Gigantic plugin = Gigantic.plugin;
	GuiMenu guimenu = Gigantic.guimenu;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler
	public void openToggleMenu(PlayerSwapHandItemsEvent event){
		Player player = event.getPlayer();

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

		if(!gp.getStatus().equals(GiganticStatus.AVAILABLE))return;

		ItemStack tool = event.getOffHandItem();

		ItemStack main = event.getMainHandItem();

		ItemStack tmp;

		if (!ActiveSkillManager.canBreak(tool))
			return;

		tmp = new ItemStack(main);
		main = new ItemStack(tool);
		tool = tmp;


		event.setCancelled(true);
		guimenu.getManager(ActiveSkillToggleMenuManager.class).open(player, 0, true);
		return;
	}
}
