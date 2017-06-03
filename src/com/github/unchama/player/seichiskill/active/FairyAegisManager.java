package com.github.unchama.player.seichiskill.active;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.listener.GeneralBreakListener;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.player.toolpouch.ToolPouchManager;
import com.github.unchama.sql.player.FairyAegisTableManager;
import com.github.unchama.task.FairyAegisTaskRunnable;
import com.github.unchama.util.Util;
import com.github.unchama.util.breakblock.BreakUtil;
import com.github.unchama.yml.CustomHeadManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class FairyAegisManager extends ActiveSkillManager {

	protected CustomHeadManager head = Gigantic.yml.getManager(CustomHeadManager.class);
	private static Information info = Information.FA_FAIRY;
	public static Random rnd = new Random();
	FairyAegisTableManager tm;

	// プレイヤーがAPを消費して拡張する破壊ブロック数
	private int breakNum;
	// プレイヤーが保有する妖精さんの数
	private int fairy;
	// プレイヤーが使用している妖精さんの数
	private int worker;

	public FairyAegisManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(FairyAegisTableManager.class);
		breakNum = 0;
		fairy = 0;
		worker = 0;
	}

	@Override
	public void init() {
		this.Mm = gp.getManager(ManaManager.class);
		this.Sm = gp.getManager(SideBarManager.class);
		this.Pm = gp.getManager(ToolPouchManager.class);
		this.Lm = gp.getManager(SeichiLevelManager.class);
		this.refresh();
	}

	@Override
	public void setToggle(boolean toggle) {
		Player player = PlayerManager.getPlayer(gp);
		if (player != null) {
			if (!this.isunlocked()) {
				player.sendMessage(this.getJPName() + ":" + ChatColor.RED
						+ "アンロックされていません");
				this.toggle = false;
				return;
			}
			this.toggle = toggle;
			if (toggle) {
				player.sendMessage(this.getJPName() + ":" + ChatColor.GREEN
						+ "ON");
				Sm.updateInfo(info, this.getBreakingFairy());
			} else {
				player.sendMessage(this.getJPName() + ":" + ChatColor.RED
						+ "OFF");
				if (this.getWorker() == 0) {
					Sm.updateInfo(info, "%DELETE%");
				}
			}
			player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK,
					(float) 0.7, (float) 2.2);
		} else {
			Bukkit.getServer()
					.getLogger()
					.warning(
							"<" + this.getClass().getSimpleName()
									+ ".toggle()> " + gp.name
									+ "のplayerクラスが見つかりません．");
		}
	}

	/**
	 * fairyを更新します．
	 *
	 */
	public void refresh() {
		fairy = 25;
		MineBlockManager mm = gp.getManager(MineBlockManager.class);
		double all = mm.getAll(TimeType.UNLIMITED);
		// 整地量1億につき1匹追加
		fairy += (int) (all / 100000000);
	}

	/**
	 * @return breakNum
	 */
	public int getBreakNum() {
		return breakNum;
	}

	/**
	 * @param breakNum
	 *            セットする breakNum
	 */
	public void setBreakNum(int breakNum) {
		this.breakNum = breakNum;
	}

	/**
	 * 解禁直後のデフォルト値を取得します．
	 *
	 * @return
	 */
	public int getDefaultBreakNum() {
		return 1000;
	}

	@Override
	public BreakRange getRange() {
		return null;
	}

	@Deprecated
	@Override
	public void setRange(BreakRange range) {

	}

	@Deprecated
	@Override
	public boolean isCoolDown() {
		return false;
	}

	@Deprecated
	@Override
	public void setCoolDown(boolean flag) {

	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	@Deprecated
	@Override
	public Volume getDefaultVolume() {
		return null;
	}

	@Deprecated
	@Override
	protected boolean canBelowBreak(Player player, Block block, Block rb) {
		return true;
	}

	@Deprecated
	@Override
	public boolean run(Player player, ItemStack tool, Block block) {
		return false;
	}

	@Override
	protected ItemStack getItemStackonLocked() {
		return new ItemStack(Material.STAINED_GLASS, 1, (short) 5);
	}

	@Override
	public String getJPName() {
		return "" + ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD
				+ "フェアリーエイジス" + ChatColor.RESET;
	}

	@Override
	public ItemStack getMenuItemStack() {
		return new ItemStack(Material.EMERALD_ORE);
	}

	@Override
	public int getUnlockLevel() {
		return 250;
	}

	@Override
	public long getUnlockAP() {
		return 1000;
	}

	@Override
	public double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 0.05)) - 1;
	}

	@Deprecated
	@Override
	public int getCoolTime(int breaknum) {
		return 0;
	}

	@Override
	public long getSpendAP(int breaknum) {
		return (long) breaknum * 2;
	}

	@Override
	public int getMaxBreakNum() {
		return 4000;
	}

	@Deprecated
	@Override
	public int getMaxHeight() {
		return 0;
	}

	@Deprecated
	@Override
	public int getMaxWidth() {
		return 0;
	}

	@Deprecated
	@Override
	public int getMaxDepth() {
		return 0;
	}

	@Override
	public long getUsedAp() {
		return this.getSpendAP(this.getBreakNum() - this.getDefaultBreakNum());
	}

	/**
	 * 与えられた数で割った答えに該当するheadを取得します．
	 *
	 * @param 1 or 10 or 100 or 1000
	 * @return
	 */
	public ItemStack getBreakNumHead(int i, int b) {
		switch (i) {
		case 10:
			b %= 100;
			b /= 10;
			break;
		case 100:
			b %= 1000;
			b /= 100;
			break;
		case 1000:
			b %= 10000;
			b /= 1000;
			break;
		default:
			b = -1;
		}
		if (b >= 0 && b < 10) {
			switch (b) {
			case 1:
				return (head.getMobHead("one"));
			case 2:
				return (head.getMobHead("two"));
			case 3:
				return (head.getMobHead("three"));
			case 4:
				return (head.getMobHead("four"));
			case 5:
				return (head.getMobHead("five"));
			case 6:
				return (head.getMobHead("six"));
			case 7:
				return (head.getMobHead("seven"));
			case 8:
				return (head.getMobHead("eight"));
			case 9:
				return (head.getMobHead("nine"));
			default:
				return (head.getMobHead("zero"));
			}
		} else {
			return new ItemStack(Material.GRASS);
		}
	}

	public boolean run(Player player, ItemStack tool, ActiveSkillManager skill,
			Block block, short pre_useDurability, double pre_usemana,
			boolean soundflag) {

		// トグルがオフなら終了
		if (!getToggle()) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルのトグルがオフなため発動できません");
			return false;
		}

		// エフェクト用に壊されるブロック全てのリストデータ
		List<Block> breaklist = new ArrayList<Block>();

		// 壊される液体のリストデータ
		List<Block> liquidlist = new ArrayList<Block>();

		// 壊されるブロックデータをÝ軸でまとめたもの
		HashMap<Integer, List<Block>> breakMap = new HashMap<Integer, List<Block>>();

		List<Coordinate> toplist = skill.getRange().getTopCoordList(player);

		toplist.forEach(c -> {
			Block rb = block.getRelative(c.getX(), c.getY(), c.getZ())
					.getRelative(BlockFace.UP);
			while (rb.getY() < 256) {
				Material m = rb.getType();
				if (ActiveSkillManager.canBreak(m)) {
					// worldguardを確認Skilledflagを確認
					if (Wg.canBuild(player, rb.getLocation())
							&& !rb.hasMetadata("Skilled")) {
						if (ActiveSkillManager.isLiquid(m)) {
							liquidlist.add(rb);
						} else {
							breaklist.add(rb);
						}
						Integer y = rb.getY();
						if (breakMap.containsKey(y)) {
							breakMap.get(y).add(rb);
						} else {
							breakMap.put(y, new ArrayList<Block>());
							breakMap.get(y).add(rb);
						}
					}
				}
				rb = rb.getRelative(BlockFace.UP);
			}
		});

		if (breaklist.isEmpty()) {
			return false;
		}

		int breakNum = breaklist.size() + liquidlist.size();
		if (breakNum > this.getBreakNum()) {
			debug.sendMessage(player, DebugEnum.SKILL, "追加破壊ブロック数が規定値を超えました："
					+ breakNum);
			return false;
		}

		// 妖精さんの数を確認
		if (this.getWorker() >= fairy) {
			player.sendMessage(this.getJPName() + ChatColor.RED
					+ ":妖精さんは全員出ています．");
			return false;
		}

		// ツールの耐久を確認
		short durability = tool.getDurability();
		boolean unbreakable = tool.getItemMeta().spigot().isUnbreakable();
		// 使用する耐久値
		short useDurability = 0;

		if (!unbreakable) {
			if (durability > tool.getType().getMaxDurability()) {
				player.sendMessage(this.getJPName() + ChatColor.RED
						+ ":ツールの耐久値が不正です．");
				return false;
			}
			useDurability = (short) (BreakUtil.calcDurability(
					tool.getEnchantmentLevel(Enchantment.DURABILITY),
					breaklist.size() + liquidlist.size()));
			// ツールの耐久が足りない時
			if (tool.getType().getMaxDurability() <= (durability
					+ useDurability + pre_useDurability)) {
				// 入れ替え可能
				if (Pm.replace(player, useDurability, tool)) {
					durability = tool.getDurability();
					unbreakable = tool.getItemMeta().spigot().isUnbreakable();
					if (unbreakable)
						useDurability = 0;
				} else {
					player.sendMessage(this.getJPName() + ChatColor.RED
							+ ":発動に必要なツールの耐久値が足りません");
					return false;
				}
			}
		}
		// マナを確認
		double usemana = this.getMana(breaklist.size() + liquidlist.size() * 2);

		if (!Mm.hasMana(usemana + pre_usemana)) {
			player.sendMessage(this.getJPName() + ChatColor.RED
					+ ":発動に必要なマナが足りません");
			return false;
		}

		MineBlockManager mb = gp.getManager(MineBlockManager.class);
		// break直前の処理
		List<ItemStack> droplist = new ArrayList<ItemStack>();
		breaklist
				.forEach((b) -> {
					// ドロップアイテムをリストに追加
					droplist.addAll(BreakUtil.getDrops(b, tool));
					// MineBlockに追加
					mb.increase(b.getType(), 1);
					debug.sendMessage(player, DebugEnum.SKILL, b.getType()
							.name()
							+ " is increment("
							+ 1
							+ ")for player:"
							+ player.getName());
					// スキルで使用するブロックに設定
					b.setMetadata("Skilled", new FixedMetadataValue(plugin,
							true));
					// アイテムが出現するのを検知させる
					Location droploc = GeneralBreakListener.getDropLocation(b);
					GeneralBreakListener.breakmap.put(droploc,
							player.getUniqueId());
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						@Override
						public void run() {
							GeneralBreakListener.breakmap.remove(droploc);
						}
					}, 1);
				});

		liquidlist.forEach(b -> {
			// スキルで使用するブロックに設定
				b.setMetadata("Skilled", new FixedMetadataValue(plugin, true));
			});

		// MineStackに追加
		MineStackManager m = gp.getManager(MineStackManager.class);
		droplist.forEach((dropitem) -> {
			if (m.add(dropitem)) {
				debug.sendMessage(player, DebugEnum.SKILL,
						"your item is added in minestack");
			} else {
				Util.giveItem(player, dropitem, false);
				debug.sendMessage(player, DebugEnum.SKILL,
						"your item is added in inventory");
			}
		});

		// 最初のブロックのみコアプロテクトに保存する．
		// ActiveSkillManager.logRemoval(player, block);

		liquidlist.forEach(b -> {
			double r = rnd.nextDouble();
			if (r < 0.5) {
				b.setType(Material.EMERALD_ORE);
			} else {
				b.setType(Material.MOSSY_COBBLESTONE);
			}
		});
		breaklist.forEach(b -> {
			double r = rnd.nextDouble();
			if (r < 0.5) {
				b.setType(Material.EMERALD_ORE);
			} else {
				b.setType(Material.MOSSY_COBBLESTONE);
			}
		});

		skilledblocklist.addAll(liquidlist);
		skilledblocklist.addAll(breaklist);

		Mm.decrease(usemana);
		tool.setDurability((short) (durability + useDurability));
		this.addWorker();
		new FairyAegisTaskRunnable(gp, block, tool, this, skill, breakMap,
				soundflag).runTaskTimer(plugin, 20, 5);
		return true;
	}

	/**
	 * 働き蜂を増やします
	 *
	 */
	private void addWorker() {
		this.worker++;
		if (this.worker > fairy) {
			this.worker = fairy;
		}
	}

	/**
	 * 働き蜂を休憩させます
	 *
	 */
	public void takeBreak() {
		this.worker--;
		if (this.worker < 0) {
			this.worker = 0;
		}
	}

	@Override
	public ItemStack getToggleOnItemStack() {
		return new ItemStack(Material.EMERALD);
	}

	/**
	 * 妖精さんの数を取得します
	 *
	 * @return
	 */
	public int getFairy() {
		return this.fairy;
	}

	/**
	 * 使用されている妖精さんの数を取得します．
	 *
	 * @return
	 */
	public int getWorker() {
		return this.worker;
	}

	/**
	 * 休憩中の妖精さんの数を取得します．
	 *
	 * @return
	 */
	public int getBreakingFairy() {
		return this.fairy - this.worker;
	}

}
