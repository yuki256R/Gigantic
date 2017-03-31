package com.github.unchama.listener;

import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Sound;
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
		List<String> lore = is.getItemMeta().getLore();
		if(Math.abs(pre_slot - slot) <= 1)return;

		for(SkillType st : SkillType.values()){
			SkillManager sm = (SkillManager)gp.getManager(st.getSkillClass());
			if(sm.getSkillBookLore().equals(lore)){
				sm.toggle();
				if(sm.getToggle()){
					player.sendMessage(sm.getJPName() + ":" + ChatColor.GREEN + "ON");
				}else{
					player.sendMessage(sm.getJPName() + ":" + ChatColor.RED + "OFF");
				}
				player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, (float)0.7 , (float) 2.2);
				inv.setHeldItemSlot(pre_slot);
				break;
			}

		}
	}
}
