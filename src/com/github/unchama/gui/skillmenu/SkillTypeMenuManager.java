package com.github.unchama.gui.skillmenu;
import java.util.HashMap;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.gui.skillmenu.condendation.CondensationMenuManager;
import com.github.unchama.gui.skillmenu.explosion.ExplosionMenuManager;
import com.github.unchama.gui.skillmenu.fairyaegis.FairyAegisMenuManager;
import com.github.unchama.gui.skillmenu.magicdrive.MagicDriveMenuManager;
import com.github.unchama.gui.skillmenu.ruinfield.RuinFieldMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.skill.Condensation;
import com.github.unchama.player.skill.Explosion;
import com.github.unchama.player.skill.FairyAegis;
import com.github.unchama.player.skill.MagicDrive;
import com.github.unchama.player.skill.RuinField;
import com.github.unchama.player.skill.SkillManager;
import com.github.unchama.player.skill.SkillManager.SkillType;

/**スキルタイプ選択メニュー
 *
 * @author tar0ss
 *
 */
public class SkillTypeMenuManager extends GuiMenuManager {
	GuiMenu guimenu = Gigantic.guimenu;

	public SkillTypeMenuManager(){
		super();
	}

	@Override
	public Inventory getInventory(Player player,int slot) {
		Inventory inv = this.getEmptyInventory(player);
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		SkillType[] st = SkillType.values();
		for(int i = 0 ; i < this.getInventorySize(); i++){
			ItemStack itemstack = gp.getManager(SkillManager.class).getSkill(st[i].getSkillClass()).getSkillTypeInfo();
			if (itemstack == null)
				continue;
			inv.setItem(i, itemstack);
		}
		return inv;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, Class<? extends GuiMenuManager>> openmap) {
	}
	@Override
	protected void setIDMap(HashMap<Integer, String> id_map) {
		for(int i = 0 ; i < this.getInventorySize() ; i++){
			id_map.put(i,"check_level_" + i);
		}
	}
	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		int sl = gp.getManager(SeichiLevelManager.class).getLevel();
		if(identifier.startsWith("check_level_")){
			switch(identifier){
			case "check_level_0":
				if(sl <Explosion.getUnlockLevel()){
					GuiMenuManager m = (GuiMenuManager)guimenu.getManager(ExplosionMenuManager.class);
					player.openInventory(m.getInventory(player,0));
					player.playSound(player.getLocation(), m.getSoundName(), m.getVolume(), m.getPitch());
				}
				break;
			case "check_level_1":
				if(sl <MagicDrive.getUnlockLevel()){
					GuiMenuManager m = guimenu.getManager(MagicDriveMenuManager.class);
					player.openInventory(m.getInventory(player,0));
					player.playSound(player.getLocation(), m.getSoundName(), m.getVolume(), m.getPitch());
				}
				break;
			case "check_level_2":
				if(sl < Condensation.getUnlockLevel()){
					GuiMenuManager m = guimenu.getManager(CondensationMenuManager.class);
					player.openInventory(m.getInventory(player,0));
					player.playSound(player.getLocation(), m.getSoundName(), m.getVolume(), m.getPitch());
				}
				break;
			case "check_level_3":
				if(sl < RuinField.getUnlockLevel()){
					GuiMenuManager m = guimenu.getManager(RuinFieldMenuManager.class);
					player.openInventory(m.getInventory(player,0));
					player.playSound(player.getLocation(), m.getSoundName(), m.getVolume(), m.getPitch());
				}
				break;
			case "check_level_4":
				if(sl < FairyAegis.getUnlockLevel()){
					GuiMenuManager m = guimenu.getManager(FairyAegisMenuManager.class);
					player.openInventory(m.getInventory(player,0));
					player.playSound(player.getLocation(), m.getSoundName(), m.getVolume(), m.getPitch());
				}
				break;
			}
			return true;
		}
		return true;
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
		return this.getInventoryType().getDefaultSize();
	}

	@Override
	public String getInventoryName(Player player) {
		return PlaceholderAPI.setPlaceholders(player, "&5&lスキルタイプを選択してください．");
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int n, ItemStack itemstack) {
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int i) {
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
