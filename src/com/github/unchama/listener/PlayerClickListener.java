package com.github.unchama.listener;

import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.KeyItem;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.yml.DebugManager;

public class PlayerClickListener  implements Listener{
	Gigantic plugin = Gigantic.plugin;
	GuiMenu guimenu = Gigantic.guimenu;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	/**木の棒メニュー
	 *
	 * @param event
	 */
	@EventHandler
	public void onPlayerOpenMenuListener(PlayerInteractEvent event){
		//プレイヤーを取得
		Player player = event.getPlayer();
		GiganticStatus gs = PlayerManager.getStatus(player);

		if(!gs.equals(GiganticStatus.AVAILABLE)){
			player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "プレイヤーデータを読み込んでいます．しばらくお待ちください．");
			return;
		}

		//プレイヤーが起こしたアクションを取得
		Action action = event.getAction();
		//アクションを起こした手を取得
		EquipmentSlot equipmentslot = event.getHand();

		if(equipmentslot.equals(EquipmentSlot.OFF_HAND)){
			return;
		}

		for(GuiMenu.ManagerType mt : GuiMenu.ManagerType.values()){
			GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt.getManagerClass());

			if(m.getType() != 1)continue;

			String click = m.getClickType();
			if(click.equalsIgnoreCase("left")){
				if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)){
					return;
				}
			}else if(click.equalsIgnoreCase("right")){
				if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)){
					return;
				}
			}else{
				return ;
			}
			KeyItem keyitem = m.getKeyItem();
			ItemStack item = player.getInventory().getItemInMainHand();

			if(keyitem.getMaterial() != null){
				if(!item.getType().equals(keyitem.getMaterial())){
					return;
				}else{
					if(!(item.getDurability() == (short)keyitem.getDamage())){
						return;
					}
				}
			}

			if(keyitem.getName() != null){
				if(!item.getItemMeta().getDisplayName().equalsIgnoreCase(keyitem.getName())){
					return;
				}
			}

			if(keyitem.getLore() != null){
				List<String> tmplore = keyitem.getLore();
				int maxcount = 20;
				int count = 0;
				while(!tmplore.isEmpty() && count <= maxcount ){
					String tmp = tmplore.get(0);
					for(String c : item.getItemMeta().getLore()){
						if(c.equalsIgnoreCase(tmp)){
							tmplore.remove(0);
							break;
						}
					}
					count ++;
				}

				if(!tmplore.isEmpty()){
					return;
				}
			}

			event.setCancelled(true);

			//開く音を再生
			player.playSound(player.getLocation(), m.getSound(), m.getVolume(), m.getPitch());
			player.openInventory(m.getInventory(player));
			return;
		}
	}

	/*
	@EventHandler
	public void testListener(PlayerInteractEvent event){
		//プレイヤーを取得
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		//プレイヤーが起こしたアクションを取得
		Action action = event.getAction();
		//アクションを起こした手を取得
		EquipmentSlot equipmentslot = event.getHand();


		if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)){
			return;
		}

		if(equipmentslot.equals(EquipmentSlot.OFF_HAND)){
			return;
		}

		MineStackManager tm = gp.getManager(MineStackManager.class);
		player.sendMessage("test");
		player.getWorld().dropItemNaturally(player.getLocation(), tm.getItemStack());
	}
	*/
}
