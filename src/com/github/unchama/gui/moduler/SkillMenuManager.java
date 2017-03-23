package com.github.unchama.gui.moduler;

import java.util.LinkedHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;

/**各スキルの情報メニュー
 *
 * @author tar0ss
 *
 */
public abstract class SkillMenuManager extends GuiMenuManager{
	public static enum MenuType{
		INFO(0),
		RANGE(1),
		ORIGIN(3),
		BOOK(5),
		EXTENSION(6),
		;
		private int slot;

		private static LinkedHashMap<Integer,MenuType> slotmap = new LinkedHashMap<Integer,MenuType>();

		static{
			for(MenuType mt : values()){
				slotmap.put(mt.getSlot(), mt);
			}
		}


		MenuType(int slot){
			this.slot = slot;
		}

		public int getSlot(){
			return this.slot;
		}

		public static MenuType getMenuTypebySlot(int slot){
			return slotmap.get(slot);
		}
	}
	@Override
	public Inventory getInventory(Player player,int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		if(gp == null){
			Bukkit.getLogger().warning(this.getClass().getName() + ":予期せぬ例外です．");
			return Bukkit.getServer().createInventory(player, 9);
		}

		Inventory inv = this.getEmptyInventory(player);

		for(int i = 0; i < this.getInventorySize() ; i++){
			ItemStack itemstack = this.getItemStack(player, i);
			if(itemstack == null)continue;
			inv.setItem(i, itemstack);
		}
		return inv;
	}

	@Override
	protected void setOpenMenuMap() {
	}

	@Override
	protected void setKeyItem() {
	}

	@Override
	public String getClickType() {
		return null;
	}

	@Override
	public int getInventorySize() {
		return 9;
	}


	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	public Sound getSoundName() {
		return Sound.valueOf("BLOCK_ENCHANTMENT_TABLE_USE");
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return (float) 0.8;
	}
}
