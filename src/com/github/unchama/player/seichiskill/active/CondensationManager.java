package com.github.unchama.player.seichiskill.active;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.mineblock.SkillBreakBlockManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.SkillEffectManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.sql.player.CondensationTableManager;
import com.github.unchama.util.SeichiSkillAutoAllocation;
import com.github.unchama.util.breakblock.BreakUtil;

/**
 * @author tar0ss
 *
 */
public class CondensationManager extends ActiveSkillManager {

	CondensationTableManager tm;

	public CondensationManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(CondensationTableManager.class);
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
	public void toggle() {
		this.setToggle(!toggle);
		gp.getManager(RuinFieldManager.class).runTask();
	}


	@Override
	public boolean run(Player player, ItemStack tool, Block block) {
		// 凝固する液体のリストデータ
		List<Block> liquidlist = new ArrayList<Block>();
		// プレイヤーが凝固する範囲情報を取得
		BreakRange range = this.getRange();
		// プレイヤーの向いている方角の凝固ブロック座標リストを取得
		List<Coordinate> breakcoord = range.getBreakCoordList(player);

		// プレイヤーのいる座標を取得する．
		Location loc = player.getLocation().getBlock().getLocation();

		// 凝固するとプレイヤーが埋まってしまう座標は除外リストに入れる，
		List<Location> exLocation = new ArrayList<Location>(Arrays.asList(loc,
				loc.add(0, 1, 0)));

		// まず凝固するブロックの総数を計算
		breakcoord.forEach(c -> {
			Block rb = block.getRelative(c.getX(), c.getY(), c.getZ());
			Material m = rb.getType();
			if (isLiquid(m)) {
				// worldguardを確認Skilledflagを確認
				if (Wg.canBuild(player, rb.getLocation())
						&& !rb.hasMetadata("Skilled")
						&& !exLocation.contains(rb.getLocation())) {
					liquidlist.add(rb);
				}
			}
		});

		if (liquidlist.isEmpty())
			return true;

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
					liquidlist.size()));
			// ツールの耐久が足りない時
			if (tool.getType().getMaxDurability() <= (durability + useDurability)) {
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
		double usemana = this.getMana(liquidlist.size());

		if (!Mm.hasMana(usemana)) {
			player.sendMessage(this.getJPName() + ChatColor.RED
					+ ":発動に必要なマナが足りません");
			return false;
		}
		MineBlockManager mb = gp.getManager(MineBlockManager.class);
		SkillBreakBlockManager bbm = gp.getManager(SkillBreakBlockManager.class);
		// condens直前の処理
		liquidlist.forEach(b -> {
			Material m = b.getType();
			if (isLiquid(m)) {
				mb.increase(m);
			}
			});
		//スキル別の破壊量に追加
		bbm.increase(ActiveSkillType.CONDENSATION, (double) liquidlist.size());

		// 最初のブロックのみコアプロテクトに保存する．
		ActiveSkillManager.logPlacement(player, liquidlist.get(0));


		//エフェクトマネージャでブロックを処理
		SkillEffectManager effm = gp.getManager(SkillEffectManager.class);

		effm.createRunner(st).condensationEffect(gp,block,liquidlist, range);

		Mm.decrease(usemana);
		tool.setDurability((short) (durability + useDurability));
		return true;
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	@Deprecated
	@Override
	protected boolean canBelowBreak(Player player, Block block, Block rb) {
		int playerlocy = player.getLocation().getBlockY() - 1;
		int rblocy = rb.getY();

		// 自分の高さ以上のブロックのみ破壊する
		if (playerlocy < rblocy || player.isSneaking()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected ItemStack getItemStackonLocked() {
		return new ItemStack(Material.STAINED_GLASS, 1, (short) 14);
	}

	@Override
	public String getJPName() {
		return "" + ChatColor.RESET + ChatColor.RED + ChatColor.BOLD
				+ "コンデンセーション" + ChatColor.RESET;
	}

	@Override
	public ItemStack getMenuItemStack() {
		return new ItemStack(Material.REDSTONE_ORE);
	}

	@Override
	public int getUnlockLevel() {
		return 100;
	}

	@Override
	public long getUnlockAP() {
		return 100;
	}

	@Override
	public double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 0.14285714)) - 1;
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
		return 1200;
	}

	@Override
	public int getMaxHeight() {
		return 50;
	}

	@Override
	public int getMaxWidth() {
		return 20;
	}

	@Override
	public int getMaxDepth() {
		return 40;
	}

	@Override
	public void rangeReset() {
		Volume v = getRange().getVolume();
		Volume dv = getDefaultVolume();
		v.setDepth(dv.getDepth());
		v.setWidth(dv.getWidth());
		v.setHeight(dv.getHeight());
		zeroPointReset();
	}

	@Override
	public void zeroPointReset() {
		Coordinate zero = getRange().getZeropoint();
		Volume v = getRange().getVolume();
		zero.setY(v.getHeight() - 1);
		zero.setX((v.getWidth() - 1) / 2);
		zero.setZ((v.getDepth() - 1) / 2);
		getRange().refresh();
	}

	@Override
	public long AutoAllocation(long leftPoint, boolean isFirst) {
		SeichiLevelManager seichiLevelManager = gp
				.getManager(SeichiLevelManager.class);
		long allocationAP = 0;
		ActiveSkillManager nextSkill = (ActiveSkillManager) gp
				.getManager(ActiveSkillType.RUINFIELD.getSkillClass());
		if (isFirst || !nextSkill.isunlocked()) {
			int level = seichiLevelManager.getLevel();
			// 解放条件を満たしているか
			if (level < getUnlockLevel() || leftPoint - getUnlockAP() < 0) {
				return leftPoint;
			}
			leftPoint -= getUnlockAP();

			// このスキルで使用可能なスキルポイント
			allocationAP = SeichiSkillAutoAllocation.getAllocationAP(level,
					leftPoint, nextSkill);
		} else {
			allocationAP = leftPoint;
		}

		List<Volume> incVolumes = new LinkedList<Volume>();
		incVolumes.add(new Volume(0, 0, 1));
		incVolumes.add(new Volume(0, 1, 0));
		incVolumes.add(new Volume(1, 0, 0));

		leftPoint -= SeichiSkillAutoAllocation.VolumeAllocation(this,
				incVolumes, allocationAP);

		return leftPoint;
	}

	@Override
	public long getUsedAp() {
		Volume v = this.getRange().getVolume();
		return this.getSpendAP(v.getVolume() - getDefaultVolume().getVolume());
	}

	@Override
	public Volume getDefaultVolume() {
		return new Volume(7, 7, 7);
	}

	@Override
	public ItemStack getToggleOnItemStack() {
		return new ItemStack(Material.REDSTONE);
	}

}
