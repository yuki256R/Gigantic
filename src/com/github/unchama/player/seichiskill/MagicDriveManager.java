package com.github.unchama.player.seichiskill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.SkillManager;
import com.github.unchama.sql.MagicDriveTableManager;

public class MagicDriveManager extends SkillManager{

	MagicDriveTableManager tm;

	public MagicDriveManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(MagicDriveTableManager.class);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}


	@Override
	protected ItemStack getItemStackonLocked() {
		return new ItemStack(Material.STAINED_GLASS, 1, (short) 11);
	}

	@Override
	public String getJPName() {
		return "" + ChatColor.RESET + ChatColor.BLUE + ChatColor.BOLD
				+ "マジックドライブ" + ChatColor.RESET;
	}

	@Override
	public Material getMenuMaterial() {
		return Material.LAPIS_ORE;
	}

	@Override
	public int getUnlockLevel() {
		return 50;
	}

	@Override
	public long getUnlockAP() {
		return 20;
	}

	@Override
	public double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 0.1666667)) - 1;
	}

	@Override
	public int getCoolTime(int breaknum) {
		return (int) ((Math.pow(breaknum, 0.23255814)) - 1) * 20;
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
		return 55;
	}

	@Override
	public int getMaxWidth() {
		return 35;
	}

	@Override
	public int getMaxDepth() {
		return 20;
	}

	@Override
	protected boolean canBelowBreak(Player player, Block block, Block rb) {
		return true;
	}

}
