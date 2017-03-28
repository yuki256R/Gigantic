package com.github.unchama.player.skill.moduler;

import java.util.ArrayList;
import java.util.List;

import net.coreprotect.CoreProtectAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.util.Util;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public abstract class SkillManager extends DataManager implements UsingSql,
		Initializable {
	protected WorldGuardPlugin Wg;
	protected CoreProtectAPI Cp;
	protected ManaManager Mm;
	protected GuiMenu guimenu = Gigantic.guimenu;

	private Boolean toggle;
	private Boolean unlocked;
	private Boolean cooldown;

	public SkillManager(GiganticPlayer gp) {
		super(gp);
		this.Wg = Util.getWorldGuard();
		this.Cp = Util.getCoreProtect();
		this.toggle = false;
		this.unlocked = false;
		this.cooldown = false;
	}

	@Override
	public void init() {
		this.Mm = gp.getManager(ManaManager.class);
	}

	/**
	 * 与えられたツールでスキルを発動します．
	 *
	 * @param player
	 * @param tool
	 * @param block
	 * @return 可否
	 */
	public abstract boolean run(Player player, ItemStack tool, Block block);

	/**
	 * Typemenuをクリックした時の処理を記述します
	 *
	 */
	public void onClickTypeMenu(Player player) {
		SkillType st = SkillType.getSkillTypebySkillClass(this.getClass());
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		int sl = sm.getLevel();
		// アンロックレベルに達していない時終了
		if (sl < this.getUnlockLevel()) {
			return;
		}
		// アンロックしているとき
		GuiMenuManager om = (GuiMenuManager) guimenu.getManager(st
				.getMenuClass());
		if (this.isunlocked()) {
			// 開く音を再生
			player.playSound(player.getLocation(), om.getSoundName(),
					om.getVolume(), om.getPitch());
			player.openInventory(om.getInventory(player, 0));
		} else if (sm.hasAP(this.getUnlockAP())) {
			// アンロックしていないかつアンロック条件を満たす時
			// アンロック処理
			this.unlocked(true);
			player.sendMessage(ChatColor.RESET + "" + ChatColor.GREEN + "APを"
					+ this.getUnlockAP() + "消費して" + this.getJPName()
					+ ChatColor.RESET + "" + ChatColor.GREEN + "を解除しました！");
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,
					(float) 1.2, (float) 0.6);
			player.openInventory(om.getInventory(player, 0));
		}
	}

	/**
	 * スキルタイプを選択するメニューで使われるitemstackを取得します
	 *
	 * @return
	 */
	public ItemStack getSkillTypeInfo() {
		ItemStack is;
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		int sl = sm.getLevel();
		if (sl < this.getUnlockLevel()) {
			// アンロック可能レベルより低い時
			is = this.getItemStackonLocked();
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(this.getJPName());
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.DARK_GRAY + "整地レベルが" + this.getUnlockLevel()
					+ "以上で解禁できます．");
			im.setLore(lore);
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
			is.setItemMeta(im);
		} else if (this.isunlocked()) {
			// アンロックされているとき
			is = new ItemStack(this.getMenuMaterial());
			ItemMeta im = is.getItemMeta();
			im.addEnchant(Enchantment.DIG_SPEED, 100, false);
			im.setDisplayName(this.getJPName());
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
			is.setItemMeta(im);
		} else {
			// されていない時
			is = new ItemStack(this.getMenuMaterial());
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(this.getJPName());
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.DARK_GRAY + "APを" + this.getUnlockAP()
					+ "消費して解禁します．");
			if (sm.hasAP(this.getUnlockAP())) {
				lore.add(ChatColor.GREEN + "クリックして解禁");
			} else {
				lore.add(ChatColor.RED + "APが足りません");
			}
			im.setLore(lore);
			im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
			is.setItemMeta(im);
		}
		return is;
	}

	/**
	 * ロックされている時に表示するItemStackを取得します．
	 *
	 * @return
	 */
	protected abstract ItemStack getItemStackonLocked();

	/**
	 * 解禁フラグを設定します
	 *
	 * @param flag
	 */
	public void unlocked(boolean flag) {
		this.unlocked = flag;
	}

	public boolean isunlocked() {
		return this.unlocked;
	}

	/**
	 * オンにします．
	 *
	 */
	public void on() {
		this.toggle = true;
	}

	/**
	 * オフにします．
	 *
	 */
	public void off() {
		this.toggle = false;
	}

	/**
	 * トグルします．
	 *
	 */
	public void toggle() {
		this.toggle = !this.toggle;
	}

	/**
	 * オンかオフかを取得します．
	 *
	 */
	public boolean getToggle() {
		return this.toggle;
	}

	/**
	 * クールダウン中の時Trueを返します
	 *
	 * @return
	 */
	public boolean isCoolDown() {
		return this.cooldown;
	}

	/**
	 * 与えられた数分のクールタイムを生成します．
	 *
	 * @param num
	 *            破壊数
	 */
	public void runCoolDownTask(int num) {
		int cooltime = this.getCooldown(num);
		if (cooltime > 5) {
			cooldown = true;
			Bukkit.getScheduler().runTaskLaterAsynchronously(plugin,
					new Runnable() {
						@Override
						public void run() {
							Bukkit.getScheduler().runTask(plugin,
									new Runnable() {

										@Override
										public void run() {
											cooldown = false;
										}

									});

						}
					}, cooltime);
		}
	}

	/**
	 * このスキルの日本語名を取得します．
	 *
	 * @return
	 */
	public abstract String getJPName();

	/**
	 * メニューで使われる代表となるマテリアル名を取得します．
	 *
	 * @return
	 */
	public abstract Material getMenuMaterial();

	/**
	 * このスキルの解放可能レベルを取得します
	 *
	 * @return
	 */
	public abstract int getUnlockLevel();

	/**
	 * このスキルの解放に必要なAPを取得します．
	 *
	 * @return
	 */
	public abstract long getUnlockAP();

	/**
	 * 破壊したブロック数から消費するマナを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public abstract double getMana(int breaknum);

	/**
	 * 破壊したブロック数から発生するクールタイム(tick)を取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public abstract int getCooldown(int breaknum);

	/**
	 * 1ブロック範囲を増やすのに必要なAPを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public abstract long getSpendAP(int breaknum);

	/**
	 * 1回の発動で破壊できる最大ブロック数を取得します．
	 *
	 * @return
	 */
	public abstract int getMaxBreakNum();

	/**
	 * 1回の発動で破壊できる最大範囲（高さ）を取得します
	 *
	 * @return
	 */
	public abstract int getMaxHeight();

	/**
	 * 1回の発動で破壊できる最大範囲（幅）を取得します
	 *
	 * @return
	 */
	public abstract int getMaxWidth();

	/**
	 * 1回の発動で破壊できる最大範囲（奥行）を取得します
	 *
	 * @return
	 */
	public abstract int getMaxDepth();

	/**
	 * 1回の発動で破壊できる最大範囲3つの合計の最大値を取得します
	 *
	 * @return
	 */
	public abstract int getMaxTotalSize();

	/**
	 * 破壊できるMaterialの時trueを返します．
	 *
	 * @param blockのmaterial名
	 * @return 可否
	 */
	public static boolean canBreak(Material m) {
		switch (m) {
		case STONE:
		case NETHERRACK:
		case NETHER_BRICK:
		case DIRT:
		case GRAVEL:
		case LOG:
		case LOG_2:
		case GRASS:
		case COAL_ORE:
		case IRON_ORE:
		case GOLD_ORE:
		case DIAMOND_ORE:
		case LAPIS_ORE:
		case EMERALD_ORE:
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
		case SAND:
		case SANDSTONE:
		case QUARTZ_ORE:
		case END_BRICKS:
		case ENDER_STONE:
		case ICE:
		case PACKED_ICE:
		case OBSIDIAN:
		case MAGMA:
		case SOUL_SAND:
		case LEAVES:
		case LEAVES_2:
		case CLAY:
		case STAINED_CLAY:
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
		case HARD_CLAY:
		case MONSTER_EGGS:
		case WEB:
		case WOOD:
		case FENCE:
		case DARK_OAK_FENCE:
		case RAILS:
		case MYCEL:
		case SNOW_BLOCK:
		case HUGE_MUSHROOM_1:
		case HUGE_MUSHROOM_2:
		case BONE_BLOCK:
		case PURPUR_BLOCK:
		case PURPUR_PILLAR:
		case SEA_LANTERN:
		case PRISMARINE:
		case SMOOTH_BRICK:
		case GLOWSTONE:
			return true;
		default:
			return false;
		}
	}

	/**
	 * スキル破壊できるツールの時trueとなります．
	 *
	 * @param tool
	 * @return 可否
	 */
	public static boolean canBreak(ItemStack tool) {
		switch (tool.getType()) {
		case DIAMOND_PICKAXE:
		case WOOD_PICKAXE:
		case IRON_PICKAXE:
		case GOLD_PICKAXE:
		case DIAMOND_AXE:
		case IRON_AXE:
		case GOLD_AXE:
		case DIAMOND_SPADE:
		case WOOD_SPADE:
		case IRON_SPADE:
		case GOLD_SPADE:
		case SHEARS:
			return true;
		default:
			return false;
		}
	}

}
