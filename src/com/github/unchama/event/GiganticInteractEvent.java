package com.github.unchama.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;
/**
 * @author tar0ss
 *
 */
public class GiganticInteractEvent extends CustomEvent implements Cancellable{
	private GiganticPlayer gp;
	private Player p;
	private ItemStack item;
	private Action action;
	private Block blockClicked;
	private BlockFace blockFace;
	private EquipmentSlot hand;
	private boolean cancelled;

	public GiganticInteractEvent(GiganticPlayer gp,PlayerInteractEvent ie){
		this.gp = gp;
		this.p = ie.getPlayer();
		this.item = ie.getItem();
		this.action = ie.getAction();
		this.blockClicked = ie.getClickedBlock();
		this.blockFace = ie.getBlockFace();
		this.hand = ie.getHand();
		this.cancelled = false;
	}

	/**
	 * gpを取得します。
	 * @return gp
	 */
	public GiganticPlayer getGiganticPlayer() {
	    return gp;
	}

	/**
	 * pを取得します。
	 * @return p
	 */
	public Player getPlayer() {
	    return p;
	}

	/**
	 * itemを取得します。
	 * @return item
	 */
	public ItemStack getItem() {
	    return item;
	}

	/**
	 * actionを取得します。
	 * @return action
	 */
	public Action getAction() {
	    return action;
	}

	/**
	 * blockClickedを取得します。
	 * @return blockClicked
	 */
	public Block getBlockClicked() {
	    return blockClicked;
	}

	/**
	 * blockFaceを取得します。
	 * @return blockFace
	 */
	public BlockFace getBlockFace() {
	    return blockFace;
	}

	/**
	 * handを取得します。
	 * @return hand
	 */
	public EquipmentSlot getHand() {
	    return hand;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
