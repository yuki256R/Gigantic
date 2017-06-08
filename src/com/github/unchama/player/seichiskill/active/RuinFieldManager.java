package com.github.unchama.player.seichiskill.active;

import java.util.ArrayList;
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
import org.bukkit.scheduler.BukkitTask;

import com.github.unchama.listener.GeneralBreakListener;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gravity.GravityManager;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.moduler.Finalizable;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.sql.player.RuinFieldTableManager;
import com.github.unchama.task.RuinFieldTaskRunnable;
import com.github.unchama.util.Util;
import com.github.unchama.util.breakblock.BreakUtil;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class RuinFieldManager extends ActiveSkillManager implements Finalizable {
	RuinFieldTableManager tm;
	BukkitTask task;

	public RuinFieldManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(RuinFieldTableManager.class);
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
		if (task != null) {
			task.cancel();
		}
		if (toggle) {
			task = new RuinFieldTaskRunnable(gp).runTaskTimerAsynchronously(
					plugin, 1, 10);
		}
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	@Override
	public void fin() {
		if (task != null) {
			task.cancel();
		}
	}

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
		return new ItemStack(Material.STAINED_GLASS, 1, (short) 10);
	}

	@Override
	public String getJPName() {
		return "" + ChatColor.RESET + ChatColor.DARK_PURPLE + ChatColor.BOLD
				+ "ルインフィールド" + ChatColor.RESET;
	}

	@Override
	public ItemStack getMenuItemStack() {
		return new ItemStack(Material.DIAMOND_ORE);
	}

	@Override
	public int getUnlockLevel() {
		return 150;
	}

	@Override
	public long getUnlockAP() {
		return 500;
	}

	@Override
	public double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 0.125)) - 1;
	}

	@Deprecated
	@Override
	public int getCoolTime(int breaknum) {
		return 0;
	}

	@Override
	public long getSpendAP(int breaknum) {
		return (long) breaknum * 4;
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
	public void rangeReset(){
		Coordinate zero = getRange().getZeropoint();
		Volume v = getRange().getVolume();
		Volume dv = getDefaultVolume();
		v.setDepth(dv.getDepth());
		v.setWidth(dv.getWidth());
		v.setHeight(dv.getHeight());
		zero.setY(dv.getHeight() - 1);
		zero.setX((dv.getWidth() - 1) / 2);
		zero.setZ((dv.getDepth() - 1) / 2);
		getRange().refresh();
	}

	@Override
	public void zeroPointReset(){
		Coordinate zero = getRange().getZeropoint();
		Volume v = getRange().getVolume();
		zero.setY(1);
		zero.setX((v.getWidth() - 1) / 2);
		zero.setZ(0);
		getRange().refresh();
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
	public boolean run(Player player, ItemStack tool, Block block) {

		// エフェクト用に壊されるブロック全てのリストデータ
		List<Block> breaklist = new ArrayList<Block>();

		// 壊される液体のリストデータ
		List<Block> liquidlist = new ArrayList<Block>();

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

		if (breaklist.isEmpty()) {
			return true;
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
			gm.calc(1, breaklist);

			/*
			 * // 重力値が０より大きければ終了 if (gravity > 0) {
			 * player.sendMessage(this.getJPName() + ChatColor.RED + ":重力値(" +
			 * gravity + ")により破壊できません"); return false; }
			 */
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
		ActiveSkillManager.logRemoval(player, breaklist.get(0));

		// breakの処理
		liquidlist.forEach(b -> {
			b.setType(Material.AIR);
		});
		breaklist.forEach(b -> {
			if (ActiveSkillManager.canBreak(b.getType())) {
				// 通常エフェクトの表示
				/*
				 * if (!b.equals(block)) w.playEffect(b.getLocation(),
				 * Effect.STEP_SOUND, b.getType());
				 */
				// ブロックを削除
				b.setType(Material.AIR);
			}
		});

		// break後の処理
		liquidlist.forEach(b -> {
			b.removeMetadata("Skilled", plugin);
		});
		breaklist.forEach(b -> {
			b.removeMetadata("Skilled", plugin);
		});

		Mm.decrease(usemana);
		tool.setDurability((short) (durability + useDurability));
		return true;
	}

	@Override
	public ItemStack getToggleOnItemStack() {
		return new ItemStack(Material.DIAMOND);
	}

}
