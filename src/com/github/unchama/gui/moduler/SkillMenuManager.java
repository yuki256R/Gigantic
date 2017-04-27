package com.github.unchama.gui.moduler;

import java.util.LinkedHashMap;

import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryType;

/**各スキルの情報メニュー用クラス
 *
 * @author tar0ss
 *
 */
public abstract class SkillMenuManager extends GuiMenuManager{
	public static enum MenuType{
		INFO(0),
		RANGE(1),
		ORIGIN(2),
		EXTENSION(3),
		HELP(4),
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
	protected void setKeyItem() {
	}

	@Override
	public String getClickType() {
		return "";
	}

	@Override
	public int getInventorySize() {
		return 5;
	}


	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
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
