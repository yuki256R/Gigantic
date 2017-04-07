package com.github.unchama.player.seichiskill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.SkillManager;
import com.github.unchama.sql.ExplosionTableManager;

/**
 * 近距離スキル
 *
 * @author tar0ss
 *
 */
public class ExplosionManager extends SkillManager {
	ExplosionTableManager tm;



	public ExplosionManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(ExplosionTableManager.class);

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
		//int blocky = block.getY();
		int rblocy = rb.getY();
		//int zeroy = this.getRange().getZeropoint().getY();
		//int voly = this.getRange().getVolume().getHeight() - 1;

		if (playerlocy < rblocy || player.isSneaking()) {
			return true;
		} else {
			return false;
		}
		/*

		// プレイヤーの足元以下のブロックを起点に破壊していた場合はtrue
		if (playerlocy >= blocky) {
			return true;
			// 破壊する高さが2以下の場合はプレイヤーより上のブロックのみ破壊する
		} else if (voly <= 1) {
			if (playerlocy < rblocy) {
				return true;
			} else {
				return false;
			}
			// 破壊する高さが起点の高さと同じ場合は無関係に破壊する
		} else if (zeroy == voly) {
			return true;
			// それ以外の場合は自分の高さ以上のブロックのみ破壊する
		} else if (playerlocy < rblocy) {
			return true;
		} else {
			return false;
		}
		*/
	}

	@Override
	public String getJPName() {
		return "" + ChatColor.RESET + ChatColor.YELLOW + ChatColor.BOLD
				+ "エクスプロージョン" + ChatColor.RESET;
	}

	@Override
	public Material getMenuMaterial() {
		return Material.COAL_ORE;
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

}
