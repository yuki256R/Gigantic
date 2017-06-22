package com.github.unchama.player.seichiskill.active;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.github.unchama.listener.GeneralBreakListener;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gravity.GravityManager;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.SkillEffectManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.sql.player.ExplosionTableManager;
import com.github.unchama.task.CoolDownTaskRunnable;
import com.github.unchama.util.SeichiSkillAutoAllocation;
import com.github.unchama.util.Util;
import com.github.unchama.util.breakblock.BreakUtil;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * 近距離スキル
 *
 * @author tar0ss
 *
 */
public class ExplosionManager extends ActiveSkillManager {
	ExplosionTableManager tm;

	public ExplosionManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(ExplosionTableManager.class);

	}

	@Override
	public boolean run(Player player, ItemStack tool, Block block) {

		// エフェクト用に壊されるブロック全てのリストデータ
		List<Block> breaklist = new ArrayList<Block>();

		// 壊される液体のリストデータ
		List<Block> liquidlist = new ArrayList<Block>();


		//液体と固体合わせた全てのリストデータ
		List<Block> alllist = new ArrayList<Block>();

		// プレイヤーの向いている方角の破壊ブロック座標リストを取得
		List<Coordinate> breakcoord = this.getRange().getBreakCoordList(player);

		// まず破壊するブロックの総数を計算
		breakcoord.forEach(c -> {
			Block rb = block.getRelative(c.getX(), c.getY(), c.getZ());
			Material m = rb.getType();
			// マテリアルを確認
				if (ActiveSkillManager.canBreak(m)) {
					// worldguardを確認Skilledflagを確認
					if (Wg.canBuild(player, rb.getLocation())
							&& !rb.hasMetadata("Skilled")) {
						if (canBelowBreak(player, block, rb)) {
							if (ActiveSkillManager.isLiquid(m)) {
								liquidlist.add(rb);
							} else {
								breaklist.add(rb);
							}
						}
					}
				}
			});


		alllist.addAll(breaklist);
		alllist.addAll(liquidlist);


		if (breaklist.isEmpty()) {
			player.sendMessage(this.getJPName() + ChatColor.RED
					+ ":発動できるブロックがありません．自分より下のブロックはしゃがみながら破壊できます．");
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
		double usemana = this.getMana(breaklist.size() + liquidlist.size() * 2);

		if (!Mm.hasMana(usemana)) {
			player.sendMessage(this.getJPName() + ChatColor.RED
					+ ":発動に必要なマナが足りません");
			return false;
		}

		FairyAegisManager fm = gp.getManager(FairyAegisManager.class);
		if (!fm.run(player, tool, this, block, useDurability, usemana, true)) {
			// 重力値を計算
			GravityManager gm = gp.getManager(GravityManager.class);
			short gravity = gm.calc(1, alllist);

			// 重力値が０より大きければ終了
			if (gravity > 0) {
				player.sendMessage(this.getJPName() + ChatColor.RED + ":重力値("
						+ gravity + ")により破壊できません");
				return false;
			}
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
		ActiveSkillManager.logRemoval(player, block);

		//エフェクトマネージャでブロックを処理
		SkillEffectManager effm = gp.getManager(SkillEffectManager.class);

		effm.createRunner(st).explosionEffect(breaklist, liquidlist, alllist, this.getRange());

		int cooltime = this.getCoolTime(breaklist.size());

		Mm.decrease(usemana);
		tool.setDurability((short) (durability + useDurability));
		if (cooltime > 5)
			new CoolDownTaskRunnable(gp, cooltime, st)
					.runTaskTimerAsynchronously(plugin, 0, 1);
		return true;
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	@Override
	protected ItemStack getItemStackonLocked() {
		return new ItemStack(Material.STAINED_GLASS, 1, (short) 7);
	}

	@Override
	protected boolean canBelowBreak(Player player, Block block, Block rb) {
		int playerlocy = player.getLocation().getBlockY() - 1;
		// int blocky = block.getY();
		int rblocy = rb.getY();
		int zeroy = this.getRange().getZeropoint().getY();
		int voly = this.getRange().getVolume().getHeight() - 1;

		// 破壊する高さが起点の高さと同じ場合は無関係に破壊する
		if (zeroy == voly) {
			return true;
			// それ以外の場合は自分の高さ以上のブロックのみ破壊する
		} else if (playerlocy < rblocy || player.isSneaking()) {
			return true;
		} else {
			return false;
		}
		/*
		 *
		 * // プレイヤーの足元以下のブロックを起点に破壊していた場合はtrue if (playerlocy >= blocky) {
		 * return true; // 破壊する高さが2以下の場合はプレイヤーより上のブロックのみ破壊する } else if (voly <=
		 * 1) { if (playerlocy < rblocy) { return true; } else { return false; }
		 * // 破壊する高さが起点の高さと同じ場合は無関係に破壊する } else if (zeroy == voly) { return
		 * true; // それ以外の場合は自分の高さ以上のブロックのみ破壊する } else if (playerlocy < rblocy) {
		 * return true; } else { return false; }
		 */
	}

	@Override
	public String getJPName() {
		return "" + ChatColor.RESET + ChatColor.YELLOW + ChatColor.BOLD
				+ "エクスプロージョン" + ChatColor.RESET;
	}

	@Override
	public ItemStack getMenuItemStack() {
		return new ItemStack(Material.COAL_ORE);
	}

	@Override
	public int getUnlockLevel() {
		return 10;
	}

	@Override
	public long getUnlockAP() {
		return 10;
	}

	@Override
	public double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 0.2)) - 1;
	}

	@Override
	public int getCoolTime(int breaknum) {
		return (int) ((Math.pow(breaknum, 0.25)) - 1) * 20;
	}

	@Override
	public long getSpendAP(int breaknum) {
		return (long) breaknum * 1;
	}

	@Override
	public int getMaxBreakNum() {
		return 2000;
	}

	@Override
	public int getMaxHeight() {
		return 50;
	}

	@Override
	public int getMaxWidth() {
		return 15;
	}

	@Override
	public int getMaxDepth() {
		return 45;
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
		zero.setY(1);
		zero.setX((v.getWidth() - 1) / 2);
		zero.setZ(0);
		getRange().refresh();
	}

	@Override
	public long AutoAllocation(long leftPoint, boolean isFirst) {
		SeichiLevelManager seichiLevelManager = gp
				.getManager(SeichiLevelManager.class);
		long allocationAP = 0;
		ActiveSkillManager nextSkill = (ActiveSkillManager) gp
				.getManager(ActiveSkillType.MAGICDRIVE.getSkillClass());
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
		}else{
			allocationAP = leftPoint;
		}

		List<Volume> incVolumes = new LinkedList<Volume>();
		incVolumes.add(new Volume(0, 0, 1));
		incVolumes.add(new Volume(0, 1, 0));
		incVolumes.add(new Volume(2, 0, 0));
		incVolumes.add(new Volume(0, 0, 1));
		incVolumes.add(new Volume(0, 1, 0));

		leftPoint -= SeichiSkillAutoAllocation.VolumeAllocation(this, incVolumes, allocationAP);

		return leftPoint;
	};

	@Override
	public long getUsedAp() {
		Volume v = this.getRange().getVolume();
		return this.getSpendAP(v.getVolume() - getDefaultVolume().getVolume());
	}

	@Override
	public Volume getDefaultVolume() {
		return new Volume(1, 1, 1);
	}

	@Override
	public ItemStack getToggleOnItemStack() {
		return new ItemStack(Material.COAL);
	}

}
