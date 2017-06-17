package com.github.unchama.gui.moduler;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gui.GuiStatusManager;
import com.github.unchama.player.seichiskill.EffectCategory;
import com.github.unchama.player.seichiskill.SkillEffectManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.util.Util;

public abstract class EffectSellectManager extends GuiMenuManager {
	// 前のページへボタン
	private static ItemStack prevButton;
	private static final int prevButtonSlot = 45;

	// 次のページへボタン
	private static ItemStack nextButton;
	private static final int nextButtonSlot = 53;

	// 下部メニューボタン
	private Map<Integer, ItemStack> menuButtons;

	public EffectSellectManager() {
		// メニューボタンの表示設定
		menuButtons = new HashMap<Integer, ItemStack>();

		int slot = 47;
		for (EffectCategory ec : EffectCategory.values()) {
			menuButtons.put(slot, ec.getMenuItem());
			slot++;
		}

		prevButton = head.getMobHead("left");
		Util.setDisplayName(prevButton, "前のページ");
		menuButtons.put(prevButtonSlot, prevButton);

		nextButton = head.getMobHead("right");
		Util.setDisplayName(nextButton, "次のページ");
		menuButtons.put(nextButtonSlot, nextButton);

	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		// Invoke設定
		for (int i = 0; i < getInventorySize(); i++) {
			id_map.put(i, Integer.toString(i));
		}
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		return getInventory(player, slot, 1, EffectCategory.NORMAL);
	}

	public Inventory getInventory(Player player, int slot, int page, EffectCategory ec) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		GuiStatusManager Sm = gp.getManager(GuiStatusManager.class);
		Sm.setCurrentPage(this, page);
		Sm.setCurrentObject(this, ec);

		SkillEffectManager Em = gp.getManager(SkillEffectManager.class);

		Inventory inv = Bukkit.getServer()
				.createInventory(null, this.getInventorySize(),
						ec.getName() + ChatColor.RESET + " " + this.getInventoryName(player) + "-" + page + "ページ");

		menuButtons.forEach((s, i) -> {
			inv.setItem(s, i);
		});

		setEffectMenuButtons(inv, Em, page, ec);

		return inv;
	}

	protected abstract ActiveSkillType getActiveSkillType();

	private void setEffectMenuButtons(Inventory inv, SkillEffectManager Em, int page, EffectCategory ec) {
		int current_id = Em.getId(this.getActiveSkillType());
		Em.getEffectFlagMap().forEach((id, flag) -> {
			EffectCategory idc = EffectCategory.getCategory(id);
			int slot = idc.getSlot(id);
			int spage = (int) (slot / 45) + 1;
			if (idc.equals(ec) && spage == page) {
				ItemStack is = idc.getSellectButton(id);
				String fs;
				if (current_id == id) {
					fs = ChatColor.YELLOW + "選択中";
					Util.addEnchant(is);
				} else if (flag) {
					fs = ChatColor.GREEN + "選択可能";
					Util.addEnchant(is);
				} else {
					fs = ChatColor.RED + "解除されていません";
				}
				Util.setLore(is, fs);

				inv.setItem(slot, is);
			}
		});
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		SkillEffectManager Em = gp.getManager(SkillEffectManager.class);
		GuiStatusManager Sm = gp.getManager(GuiStatusManager.class);
		int page = Sm.getCurrentPage(this);
		int slot = Integer.parseInt(identifier);

		if(player.getOpenInventory().getTopInventory().getItem(slot) == null){
			return false;
		}


		EffectCategory ec = (EffectCategory) Sm.getCurrentObject(this);
		if(slot >= 45){
			switch(slot){
			case prevButtonSlot:
				if(page > 1){
					player.openInventory(this.getInventory(player, slot, page - 1, ec));
					player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0F, 1.5F);
				}
				break;
			case 47:
			case 48:
			case 49:
			case 50:
			case 51:
				EffectCategory next_ec = EffectCategory.getCategorybyID(slot - 47);
				player.openInventory(this.getInventory(player, slot,1, next_ec));
				player.playSound(player.getLocation(), this.getSoundName(), this.getVolume(), this.getPitch());
				break;
			case nextButtonSlot:
				if(page < 1){
					player.openInventory(this.getInventory(player, slot, page + 1, ec));
					player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0F, 1.5F);
				}
				break;
			}
		}else{
			int effect_id = ec.getEffectID(slot + ((page-1) * 45));
			if(Em.getEffectFlagMap().get(effect_id) && Em.getId(this.getActiveSkillType()) != effect_id){
				Em.setId(this.getActiveSkillType(),effect_id);
				this.update(player);
			}else{
				player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0F, 1.5F);
			}
		}
		return true;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
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
		return 9 * 6;
	}

	@Override
	public String getInventoryName(Player player) {
		return "エフェクト";
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_ENCHANTMENT_TABLE_USE;
	}

	@Override
	public float getVolume() {
		return 0.8F;
	}

	@Override
	public float getPitch() {
		return 0.7F;
	}

}
