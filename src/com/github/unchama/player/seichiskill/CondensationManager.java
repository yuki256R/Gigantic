package com.github.unchama.player.seichiskill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.SkillManager;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.sql.CondensationTableManager;

public class CondensationManager extends SkillManager {
	CondensationTableManager tm;
	BukkitTask task;

	public CondensationManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(CondensationTableManager.class);
	}

	@Override
	public void toggle() {
		this.toggle = !this.toggle;
	}


	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
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
		return new ItemStack(Material.STAINED_GLASS, 1, (short) 14);
	}

	@Override
	public String getJPName() {
		return "" + ChatColor.RESET + ChatColor.RED + ChatColor.BOLD
				+ "コンデンセーション" + ChatColor.RESET;
	}

	@Override
	public Material getMenuMaterial() {
		return Material.REDSTONE_ORE;
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
	public long getUsedAp() {
		Volume v = this.getRange().getVolume();
		return this.getSpendAP(v.getVolume() - getDefaultVolume().getVolume());
	}

	@Override
	public Volume getDefaultVolume() {
		return new Volume(7, 7, 7);
	}
}
