package com.github.unchama.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.github.unchama.gigantic.UserManager;
import com.github.unchama.menu.button.Button;
import com.github.unchama.menu.button.PlayerDataButton;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.util.Message;

public class MainMenu extends Menu{
	enum MainButton{
		PLAYERDATA(0,new PlayerDataButton()),

		;

		private int i;
		private Button b;

		MainButton(int i,Button b){
			this.i = i;
			this.b = b;
		}


	}

	public static Inventory getMainMenu(Player p) {
		GiganticPlayer gp = UserManager.getGiganticPlayer(p);

		if(gp.equals(null)){
			Message.sendPlayerDataNullMessage(p);
			plugin.getLogger().warning("PlayerData is not found (player:"+ p.getName() +")in StickMenu");
		}
		//TODO
		return null;
	}

}
