package com.github.unchama.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.seichiskill.moduler.SkillManager;
import com.github.unchama.player.seichiskill.moduler.SkillType;
import com.github.unchama.yml.DebugManager;

/**プレイヤーのスロットを切り替える動作から起こるイベントを扱う
 *
 * @author tar0ss
 *
 */
public class PlayerItemHeldListener implements Listener{
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	/**スキルのトグルを切り替えます．
	 *
	 * @param event
	 */
	@EventHandler
	public void toggle(PlayerItemHeldEvent event){
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		if(!gp.getStatus().equals(GiganticStatus.AVAILABLE))return;
		PlayerInventory inv = player.getInventory();
		int pre_slot = event.getPreviousSlot();
		int slot = event.getNewSlot();
		ItemStack is = inv.getItem(slot);
		if(is == null)return;

		String displayname = is.getItemMeta().getDisplayName();
		if(displayname == null)return;

		if(Math.abs(pre_slot - slot) <= 1)return;

		ItemStack pre_is = inv.getItem(pre_slot);
		if(pre_is == null)return;

		for(SkillType st : SkillType.values()){
			SkillManager sm = (SkillManager)gp.getManager(st.getSkillClass());
			if(displayname.contains(sm.getJPName()) && !pre_is.getType().equals(Material.ENCHANTED_BOOK)){
				sm.toggle();
				inv.setHeldItemSlot(pre_slot);
				break;
			}

		}
	}
}
