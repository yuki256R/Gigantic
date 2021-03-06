package com.github.unchama.gui.moduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import com.github.unchama.player.seichiskill.moduler.effect.EffectRunner;
import com.github.unchama.util.Util;

public abstract class EffectSellectMenuManager extends GuiMenuManager {
	// 前のページへボタン
	private static ItemStack prevButton;
	private static final int prevButtonSlot = 45;

	// 次のページへボタン
	private static ItemStack nextButton;
	private static final int nextButtonSlot = 53;

	private HashMap<EffectCategory, Integer> maxPageMap;

	// 下部メニューボタン
	private Map<Integer, ItemStack> menuButtons;

	private static final int yesButtonSlot = 33;
	private static final int noButtonSlot = 29;

	//購入メニューボタン
	private Map<Integer, ItemStack> purchaseButtons;

	public EffectSellectMenuManager() {
		// メニューボタンの表示設定
		menuButtons = new HashMap<Integer, ItemStack>();
		purchaseButtons = new HashMap<Integer, ItemStack>();
		maxPageMap = new HashMap<EffectCategory, Integer>();

		int slot = 47;
		for (EffectCategory ec : EffectCategory.values()) {
			menuButtons.put(slot, ec.getMenuItem());
			int maxpage = (ec.getEffectNum() - 1) / 45 + 1;
			maxPageMap.put(ec, maxpage);
			slot++;
		}

		prevButton = head.getMobHead("left");
		Util.setDisplayName(prevButton, "前のページ");
		menuButtons.put(prevButtonSlot, prevButton);

		nextButton = head.getMobHead("right");
		Util.setDisplayName(nextButton, "次のページ");
		menuButtons.put(nextButtonSlot, nextButton);

		ItemStack yes = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		Util.setDisplayName(yes, "はい");
		purchaseButtons.put(yesButtonSlot, yes);
		ItemStack no = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		Util.setDisplayName(no, "いいえ");
		purchaseButtons.put(noButtonSlot, no);
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

	public Inventory getPurchaseInventory(Player player, int effect_id) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		GuiStatusManager Sm = gp.getManager(GuiStatusManager.class);
		Sm.setCurrentPage(this, effect_id);

		EffectCategory ec = (EffectCategory) Sm.getCurrentObject(this);

		String e_name = EffectCategory.getName(effect_id);
		Inventory inv = Bukkit.getServer()
				.createInventory(null, this.getInventorySize(),
						e_name + ChatColor.RESET + this.getInventoryName(player) + "を購入します");

		purchaseButtons.forEach((s, i) -> {
			inv.setItem(s, i);
		});
		ItemStack info = ec.getMenuItem();
		inv.setItem(0, info);
		ItemStack effect_info = ec.getSellectButton(effect_id, false);
		inv.setItem(31, effect_info);

		return inv;
	}

	protected abstract ActiveSkillType getActiveSkillType();

	private void setEffectMenuButtons(Inventory inv, SkillEffectManager Em, int page, EffectCategory ec) {
		int current_id = Em.getId(this.getActiveSkillType());
		Em.getEffectFlagMap().forEach(
				(id, unlock_flag) -> {
					EffectCategory idc = EffectCategory.getCategory(id);
					int slot = idc.getSlot(id);
					int spage = (int) (slot / 45) + 1;
					if (idc == ec && spage == page) {
						try {
							ItemStack is = idc.getSellectButton(id, unlock_flag);
							List<String> lore = is.getItemMeta().getLore();
							boolean breakable_flag = EffectCategory.getRunnerClass(id).newInstance()
									.isImproved(this.getActiveSkillType());
							lore.add(ChatColor.GRAY + "----------------");
							if (current_id == id) {
								lore.add(ChatColor.YELLOW + "選択中");
							} else if (!breakable_flag) {
								lore.add(ChatColor.RED + "このスキルでは使えません");
							} else if (unlock_flag) {
								lore.add(ChatColor.AQUA + "選択可能");
							} else if (Em.canBuyEffect(id, ec)) {
								lore.add(ChatColor.YELLOW + Em.getCurrentPointString(idc));
								lore.add(ChatColor.GREEN + "クリックで購入します");
							} else {
								lore.add(ChatColor.YELLOW + Em.getCurrentPointString(idc));
								lore.add(ChatColor.RED + "ポイントが足りないため，購入できません");
							}
							Util.setLore(is, lore);
							inv.setItem(slot, is);
						} catch (Exception e) {
							Bukkit.getLogger().warning("予期せぬ例外が発生:EffectSellectMenuManager.setEffectMenuButtons()");
							e.printStackTrace();
						}

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
		EffectCategory ec = (EffectCategory) Sm.getCurrentObject(this);
		Inventory topInventory = player.getOpenInventory().getTopInventory();

		if (topInventory.getItem(slot) == null) {
			return false;
		}

		if (topInventory.getTitle().contains("購入")) {
			//購入ページ処理
			int effect_id = page;
			switch (slot) {
			case yesButtonSlot:
				Em.unlockonShop(effect_id, ec);
				player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.5F);
				player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 0.7F);
				player.openInventory(this.getInventory(player, slot, ec.getPage(effect_id), ec));
				break;
			case noButtonSlot:
				player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0F, 1.5F);
				player.openInventory(this.getInventory(player, slot, ec.getPage(effect_id), ec));
				break;
			default:
				break;
			}
		} else {
			//通常セレクトﾍﾟｰｼﾞ処理
			if (slot >= 45) {
				switch (slot) {
				case prevButtonSlot:
					if (page > 1) {
						player.openInventory(this.getInventory(player, slot, page - 1, ec));
						player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0F, 1.5F);
					} else {
						player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0F, 1.5F);
					}
					break;
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
					EffectCategory next_ec = EffectCategory.getCategorybyID(slot - 47);
					player.openInventory(this.getInventory(player, slot, 1, next_ec));
					player.playSound(player.getLocation(), this.getSoundName(), this.getVolume(), this.getPitch());
					break;
				case nextButtonSlot:
					if (page < maxPageMap.get(ec)) {
						player.openInventory(this.getInventory(player, slot, page + 1, ec));
						player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0F, 1.5F);
					} else {
						player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0F, 1.5F);
					}
					break;
				}
			} else {
				int effect_id = ec.getEffectID(slot + ((page - 1) * 45));
				boolean unlock_flag = Em.getEffectFlagMap().get(effect_id);
				boolean breakable_flag;
				try {
					EffectRunner runner = EffectCategory.getRunnerClass(effect_id).newInstance();
					breakable_flag = runner.isImproved(this.getActiveSkillType());
					if (unlock_flag) {
						//選択中にセット
						if (breakable_flag && Em.getId(this.getActiveSkillType()) != effect_id) {
							Em.setId(this.getActiveSkillType(), effect_id);
							player.openInventory(this.getInventory(player, slot, page, ec));
							player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1.0F, 1.5F);
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0F, 1.5F);
						}
					} else if (breakable_flag) {
						if (Em.canBuyEffect(effect_id, ec)) {
							//購入メニューに遷移
							player.openInventory(this.getPurchaseInventory(player, effect_id));
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 0.7F, 1.4F);
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0F, 1.5F);
						}
					} else {
						player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0F, 1.5F);
					}
				} catch (InstantiationException | IllegalAccessException e) {
					Bukkit.getLogger().warning("予期せぬ例外が発生:EffectSellectMenuManager.invoke()");
					e.printStackTrace();
				}
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
