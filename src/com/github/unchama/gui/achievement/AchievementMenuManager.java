package com.github.unchama.gui.achievement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.achievement.AchievementCategory;
import com.github.unchama.achievement.AchievementEnum;
import com.github.unchama.achievement.AnotherName;
import com.github.unchama.achievement.AnotherNameParts;
import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.achievement.AchievementManager;
import com.github.unchama.player.gui.GuiStatusManager;
import com.github.unchama.player.point.UnchamaPointManager;
import com.github.unchama.util.TextUtil;
import com.github.unchama.util.Util;

public final class AchievementMenuManager extends GuiMenuManager {
	// 前のページへボタン
	protected ItemStack prevButton;
	protected static final int prevButtonSlot = 45;

	// 次のページへボタン
	protected ItemStack nextButton;
	protected static final int nextButtonSlot = 53;

	public AchievementMenuManager() {
		super();
		prevButton = head.getMobHead("left");
		Util.setDisplayName(prevButton, "前のページ");
		nextButton = head.getMobHead("right");
		Util.setDisplayName(nextButton, "次のページ");
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		Inventory inv = this.getEmptyInventory(player);

		inv.setItem(prevButtonSlot, prevButton);
		inv.setItem(nextButtonSlot, nextButton);

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		AchievementManager aM = gp.getManager(AchievementManager.class);
		GuiStatusManager sM = gp.getManager(GuiStatusManager.class);
		UnchamaPointManager pM = gp.getManager(UnchamaPointManager.class);
		String identifier = (String) sM.getCurrentObject(this);
		int cat = 0;
		String catS = sM.getSelectedCategory(this);
		if (catS != null && catS != "") {
			cat = Integer.valueOf(catS);
		} else {
			sM.setSelectedCategory(this, Integer.toString(0));
		}
		int page = sM.getCurrentPage(this);
		for (int i = 0; i < inv.getSize() - 9; i++) {
			ItemStack is = null;
			List<String> lore = new ArrayList<String>();
			if (i < 9) {
				switch (i) {
				case 0:
					is = head.getPlayerHead(player.getName());
					Util.setDisplayName(is,
							"" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + player.getName() + "の実績情報");
					lore.add(ChatColor.GREEN + "実績解除状況:" + aM.getUnlockedAchivementNum() + "/"
							+ AchievementEnum.getAchivementNum());
					lore.add(ChatColor.GREEN + "二つ名パーツ獲得状況");
					lore.add(ChatColor.GREEN + "前パーツ：" + aM.getUnlockedAnotherNameNum(AnotherNameParts.TOP) + "/"
							+ AchievementEnum.getAnotherNameNum(AnotherNameParts.TOP));
					lore.add(ChatColor.GREEN + "中パーツ：" + aM.getUnlockedAnotherNameNum(AnotherNameParts.MIDDLE) + "/"
							+ AchievementEnum.getAnotherNameNum(AnotherNameParts.MIDDLE));
					lore.add(ChatColor.GREEN + "後パーツ：" + aM.getUnlockedAnotherNameNum(AnotherNameParts.BOTTOM) + "/"
							+ AchievementEnum.getAnotherNameNum(AnotherNameParts.BOTTOM));
					Util.setLore(is, lore);
					break;
				case 2:
					is = new ItemStack(Material.BOOK_AND_QUILL);
					Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "実績");
					lore.add(ChatColor.GREEN + "実績を確認します．");
					Util.setLore(is, lore);
					break;
				case 4:
					is = new ItemStack(Material.NAME_TAG);
					Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "二つ名");
					lore.add(ChatColor.GREEN + "二つ名の設定・確認を");
					lore.add(ChatColor.GREEN + "行います．");
					Util.setLore(is, lore);
					break;
				}
			} else if (identifier == "achievement") {
				AchievementCategory[] catList = AchievementCategory.getCategorys();
				if (i >= 9 && i < 18) {
					if (i - 9 < catList.length) {
						is = new ItemStack(Material.WOOL, 1, (short) (i - 9));
						Util.setDisplayName(is,
								"" + ChatColor.DARK_AQUA + ChatColor.BOLD + "【実績】 " + catList[i - 9].getName());
					}
				} else {
					AchievementCategory aC = catList[cat];
					List<GiganticAchievement> aList = AchievementCategory.getAchivList(aC);
					int d = i - 18 + (27 * page);
					if (d < aList.size()) {
						GiganticAchievement ga = aList.get(d);
						AnotherName aN = ga.getAnotherName();
						if (aM.getFlag(ga.getID())) {
							is = head.getMobHead("pickel_chalice");
							Util.setDisplayName(is, "" + ChatColor.GREEN + ChatColor.BOLD + aN.getName());
							lore.add("" + ChatColor.WHITE + ChatColor.BOLD + ChatColor.UNDERLINE + "獲得条件");
							lore.add(ChatColor.WHITE + ga.getUnlockInfo());
							lore.add(ChatColor.GRAY + "-----------------");
							lore.add(ChatColor.GRAY + "前パーツ：" + aN.getTopName());
							lore.add(ChatColor.GRAY + "中パーツ：" + aN.getMiddleName());
							lore.add(ChatColor.GRAY + "後パーツ：" + aN.getBottomName());
							Util.setLore(is, lore);
						} else {
							is = head.getMobHead("n_question");
							Util.setDisplayName(is, "" + ChatColor.RED + ChatColor.BOLD + aN.getName());
							lore.add("" + ChatColor.WHITE + ChatColor.BOLD + ChatColor.UNDERLINE + "獲得条件");
							lore.add(ChatColor.WHITE + ga.getLockInfo());
							Util.setLore(is, lore);
						}
					}
				}
			} else if (identifier == "anothername") {
				if (i >= 9 && i < 18) {
					switch (i) {
					case 9:
						if (aM.isLevelDisplay()) {
							is = new ItemStack(Material.BUCKET);
						} else {
							is = new ItemStack(Material.MILK_BUCKET);
						}
						Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "現在の二つ名");
						lore.add("" + ChatColor.WHITE + ChatColor.BOLD + ChatColor.UNDERLINE + "["
								+ aM.getAnotherName() + "]");
						Util.setLore(is, lore);
						break;
					case 10:
						is = new ItemStack(Material.EMERALD);
						Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "ポイント変換");
						lore.add(ChatColor.GRAY + "JMS投票で手に入るポイントを");
						lore.add(ChatColor.GRAY + "実績ポイントと交換できます．");
						if(pM.getPoint() < 10){
							lore.add(ChatColor.RED + "投票ポイントが足りません");
						}else{
							lore.add(ChatColor.GREEN + "クリックすると1回交換されます");
							lore.add(ChatColor.GREEN + "投票ポイント:" + pM.getPoint());
							lore.add(ChatColor.GREEN + "実績ポイント:" + aM.getAchievementPoint());
						}
						lore.add(ChatColor.DARK_GREEN + "----交換レート----");
						lore.add("" + ChatColor.DARK_GREEN + ChatColor.BOLD + "投票pt 10pt -> 実績pt 3pt");
						Util.setLore(is, lore);
						break;
					case 12:
						is = head.getMobHead("purple_cmd");
						Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "前パーツ選択");
						break;
					case 13:
						is = head.getMobHead("green_cmd");
						Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "中パーツ選択");
						break;
					case 14:
						is = head.getMobHead("cmd");
						Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "後パーツ選択");
						break;
					case 16:
						is = new ItemStack(Material.GRASS);
						Util.setDisplayName(is, "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE
								+ "二つ名をリセット");
						lore.add(ChatColor.GREEN + "クリックすると");
						lore.add(ChatColor.GREEN + "二つ名がリセットされます．");
						lore.add(ChatColor.GRAY + "------------------");
						lore.add(ChatColor.GRAY + "変換前:" + ChatColor.BOLD + aM.getAnotherName());
						lore.add(ChatColor.GRAY + "             ↓      ");
						lore.add(ChatColor.GRAY + "変換後:" + ChatColor.BOLD + aM.getLevelName());
						Util.setLore(is, lore);
						break;
					default:
						break;
					}
				} else if (cat == 3 || cat == 4 || cat == 5) {
					AnotherNameParts parts = AnotherNameParts.TOP;
					switch (cat) {
					case 3:
						parts = AnotherNameParts.TOP;
						break;
					case 4:
						parts = AnotherNameParts.MIDDLE;
						break;
					case 5:
						parts = AnotherNameParts.BOTTOM;
						break;
					}
					List<GiganticAchievement> aList = AchievementEnum.getAchievementList(parts);
					int d = i - 18 + (27 * page);
					if (d < aList.size()) {
						GiganticAchievement ga = aList.get(d);
						AnotherName aN = ga.getAnotherName();
						if (aM.getFlag(ga.getID())) {
							if (aM.getAnotherNamePartsID(parts) == ga.getID()) {
								is = new ItemStack(Material.EYE_OF_ENDER);
							} else {
								is = new ItemStack(Material.ENDER_PEARL);
							}
							Util.setDisplayName(is, "" + ChatColor.AQUA + ChatColor.BOLD + "実績No." + ga.getID() + "");
							switch (parts) {
							case BOTTOM:
								lore.add(ChatColor.WHITE + "後パーツ：" + ChatColor.BOLD + aN.getBottomName());
								break;
							case MIDDLE:
								lore.add(ChatColor.WHITE + "中パーツ：" + ChatColor.BOLD + aN.getMiddleName());
								break;
							case TOP:
								lore.add(ChatColor.WHITE + "前パーツ：" + ChatColor.BOLD + aN.getTopName());
								break;
							}
							lore.add(ChatColor.GRAY + "------------------");
							String cname = aM.getChengedAnotherName(ga, parts);
							if (TextUtil.getLength(cname) < 20) {
								lore.add(ChatColor.GRAY + "変換前:" + ChatColor.BOLD + aM.getAnotherName());
								lore.add(ChatColor.GRAY + "             ↓      ");
								lore.add(ChatColor.GRAY + "変換後:" + ChatColor.BOLD + cname);
							} else {
								lore.add(ChatColor.RED + "文字数が多すぎます．");
							}
							Util.setLore(is, lore);
						} else {
							if (ga.isPurchasable()) {
								is = head.getMobHead("blue_present");
								Util.setDisplayName(is, "" + ChatColor.AQUA + ChatColor.BOLD + "実績No." + ga.getID() + "");
								lore.add("" + ChatColor.WHITE + ChatColor.BOLD + "必要実績ポイント: " + ga.getUsePoint());
								if(aM.getAchievementPoint() < ga.getUsePoint()){
									lore.add(ChatColor.DARK_RED + "実績ポイントが不足しています．");
								}else{
									lore.add(ChatColor.BLUE + "クリックして購入");
								}
								switch (parts) {
								case BOTTOM:
									lore.add(ChatColor.GRAY + "後パーツ：" + aN.getBottomName());
									break;
								case MIDDLE:
									lore.add(ChatColor.GRAY + "中パーツ：" + aN.getMiddleName());
									break;
								case TOP:
									lore.add(ChatColor.GRAY + "前パーツ：" + aN.getTopName());
									break;
								}
							} else {
								is = head.getMobHead("n_question");
								Util.setDisplayName(is, "" + ChatColor.RED + ChatColor.BOLD + "実績No." + ga.getID() + "");
								lore.add(ChatColor.DARK_RED + "購入できない実績です．");
							}
							Util.setLore(is, lore);
						}
					}

				}
			}
			inv.setItem(i, is);
		}
		inv.setMaxStackSize(Integer.MAX_VALUE);
		return inv;
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		idmap.put(2, "achievement");
		idmap.put(4, "anothername");
		for (int i = 9; i < this.getInventorySize() - 9; i++) {
			idmap.put(i, Integer.toString(i));
		}
		idmap.put(prevButtonSlot, "back");
		idmap.put(nextButtonSlot, "go");
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		AchievementManager aM = gp.getManager(AchievementManager.class);
		GuiStatusManager sM = gp.getManager(GuiStatusManager.class);
		int page = sM.getCurrentPage(this);
		String c = (String) sM.getCurrentObject(this);
		int cat = Integer.valueOf(sM.getSelectedCategory(this));
		int d;
		switch (identifier) {
		case "achievement":
			sM.setCurrentObject(this, identifier);
			sM.setSelectedCategory(this, Integer.toString(0));
			sM.setCurrentPage(this, 0);
			this.update(player);
			return true;
		case "anothername":
			sM.setCurrentObject(this, identifier);
			sM.setSelectedCategory(this, Integer.toString(0));
			sM.setCurrentPage(this, 0);
			this.update(player);
			return true;
		case "go":
			page++;
			d = 27 * page;
			switch (c) {
			case "achievement":
				AchievementCategory[] catList = AchievementCategory.getCategorys();
				AchievementCategory aC = catList[cat];
				List<GiganticAchievement> aList = AchievementCategory.getAchivList(aC);
				if (d < aList.size()) {
					sM.setCurrentPage(this, page);
					player.playSound(player.getLocation(),Sound.BLOCK_CHEST_OPEN, 1.0F, 1.4F);
					player.openInventory(this.getInventory(player, 0));
					return true;
				}
				return false;
			case "anothername":
				AnotherNameParts parts = AnotherNameParts.TOP;
				switch (cat) {
				case 3:
					parts = AnotherNameParts.TOP;
					break;
				case 4:
					parts = AnotherNameParts.MIDDLE;
					break;
				case 5:
					parts = AnotherNameParts.BOTTOM;
					break;
				}
				List<GiganticAchievement> apList = AchievementEnum.getAchievementList(parts);
				if (d < apList.size()) {
					sM.setCurrentPage(this, page);
					player.playSound(player.getLocation(),Sound.BLOCK_CHEST_OPEN, 1.0F, 1.4F);
					player.openInventory(this.getInventory(player, 0));
					return true;
				}
				return false;
			}
			return false;
		case "back":
			if (page == 0) {
				return false;
			} else {
				page--;
				sM.setCurrentPage(this, page);
				player.playSound(player.getLocation(),Sound.BLOCK_CHEST_OPEN, 1.0F, 1.4F);
				player.openInventory(this.getInventory(player, 0));
				return true;
			}
		default:
			int s = Integer.valueOf(identifier);
			page = sM.getCurrentPage(this);
			Inventory inv = player.getOpenInventory().getTopInventory();
			if (inv.getItem(s) == null)
				return false;
			if (s >= 9 && s < 18) {
				switch (c) {
				case "achievement":
					sM.setSelectedCategory(this, Integer.toString(s - 9));
					sM.setCurrentPage(this, 0);
					this.update(player);
					return true;
				case "anothername":
					if (s - 9 == 7) {
						aM.reset();
					} else if (s - 9 == 1) {
						aM.exchange();
					} else {
						sM.setSelectedCategory(this, Integer.toString(s - 9));
						sM.setCurrentPage(this, 0);
					}
					this.update(player);
					return true;
				}
			} else {
				switch (c) {
				case "achievement":
					/*設定ミスによりスルー
					page = sm.getCurrentPage(this);
					AchievementCategory[] catList = AchievementCategory.getCategorys();
					AchievementCategory aC = catList[page];
					List<GiganticAchievement> aList = AchievementCategory.getAchivList(aC);
					GiganticAchievement ga = aList.get(s - 18);
					if (aM.getFlag(ga.getID())) {
						aM.setAnotherName(ga.getID());
					}
					this.update(player);
					return true;
					*/
					return true;
				case "anothername":
					AnotherNameParts parts = AnotherNameParts.TOP;
					switch (cat) {
					case 3:
						parts = AnotherNameParts.TOP;
						break;
					case 4:
						parts = AnotherNameParts.MIDDLE;
						break;
					case 5:
						parts = AnotherNameParts.BOTTOM;
						break;
					}
					List<GiganticAchievement> aList = AchievementEnum.getAchievementList(parts);
					d = s - 18 + (27 * page);
					GiganticAchievement ga = aList.get(d);
					if (aM.getFlag(ga.getID())) {
						String cname = aM.getChengedAnotherName(ga, parts);
						if (TextUtil.getLength(cname) < 20) {
							aM.setAnotherNamePartsID(parts, ga.getID());
							player.playSound(player.getLocation(),Sound.ITEM_ARMOR_EQUIP_IRON, 1.0F, 1.4F);
							player.openInventory(this.getInventory(player, 0));
							return true;
						} else {
							return false;
						}
					}else if(ga.isPurchasable()){
						if(aM.getAchievementPoint() >= ga.getUsePoint()){
							aM.setFlag(ga.getID());
							player.playSound(player.getLocation(),Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.7F);
							player.openInventory(this.getInventory(player, 0));
							return true;
						}else{
							return false;
						}
					}
					this.update(player);
					return true;
				}
			}

			return true;
		}
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void setKeyItem() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getClickType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getInventorySize() {
		return 9 * 6;
	}

	@Override
	public String getInventoryName(Player player) {
		return "" + ChatColor.BOLD + ChatColor.UNDERLINE + ChatColor.DARK_AQUA + "実績・二つ名";
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
		return Sound.BLOCK_IRON_TRAPDOOR_OPEN;
	}

	@Override
	public float getVolume() {
		return 1.0F;
	}

	@Override
	public float getPitch() {
		return 1.4F;
	}

}
