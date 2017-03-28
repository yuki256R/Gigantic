package com.github.unchama.player.skill;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.skill.moduler.BreakRange;
import com.github.unchama.player.skill.moduler.Coordinate;
import com.github.unchama.player.skill.moduler.SkillManager;
import com.github.unchama.player.skill.moduler.SkillType;
import com.github.unchama.sql.ExplosionTableManager;
import com.github.unchama.util.breakblock.BreakUtil;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * 近距離スキル
 *
 * @author tar0ss
 *
 */
public class ExplosionManager extends SkillManager {
	public SkillType st = SkillType.EXPLOSION;
	ExplosionTableManager tm;

	private BreakRange range;

	public ExplosionManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(ExplosionTableManager.class);
	}
	@Override
	public void save(Boolean loginflag) {
		tm.save(gp,loginflag);
	}

	/**
	 * @return range
	 */
	public BreakRange getRange() {
		return range;
	}

	/**
	 * @param range
	 *            セットする range
	 */
	public void setRange(BreakRange range) {
		this.range = range;
	}

	@Override
	protected ItemStack getItemStackonLocked() {
		return new ItemStack(Material.STAINED_GLASS, 1, (short) 7);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, ItemStack tool, Block block) {
		// まず破壊するブロックの総数を計算
		// エフェクト用に壊されるブロック全てのリストデータ
		List<Block> breaklist = new ArrayList<Block>();

		// 壊される溶岩のリストデータ
		List<Block> lavalist = new ArrayList<Block>();

		List<Coordinate> breakcoord = range.getBreakCoordList(player);

		breakcoord.forEach(c -> {
			Block rb = block.getRelative(c.getX(), c.getY(), c.getZ());
			Material m = rb.getType();
			// マテリアルを確認
				if (SkillManager.canBreak(m) || m.equals(Material.STATIONARY_LAVA)) {
					// worldguardを確認
					if (Wg.canBuild(player, rb.getLocation())) {
						switch (m) {
						case STATIONARY_LAVA:
							lavalist.add(rb);
						default:
							breaklist.add(rb);
						}
						rb.setMetadata("Skilled", new FixedMetadataValue(
								plugin, true));
					}
				}
			});

		// 最初のブロックのみコアプロテクトに保存する．
		Boolean success = Cp.logRemoval(player.getName(), block.getLocation(),
				block.getState().getType(), block.getState().getData()
						.getData());
		if (!success) {
			debug.warning(DebugEnum.SKILL, "CoreProtectで破壊ログを保存できませんでした．");
			debug.warning(DebugEnum.SKILL, "Player名：" + player.getName());
			debug.warning(DebugEnum.SKILL, "BlockMaterial:"
					+ block.getType().toString());
			debug.warning(DebugEnum.SKILL, "Location："
					+ block.getLocation().toString());
		}

		// 総数が1であれば発動失敗として終了．
		if (breaklist.size() == 1) {
			return false;
		}

		// ツールの耐久を確認

		short durability = tool.getDurability();
		boolean unbreakable = tool.getItemMeta().spigot().isUnbreakable();

		if (!unbreakable) {
			durability += (short) (BreakUtil.calcDurability(
					tool.getEnchantmentLevel(Enchantment.DURABILITY),
					breaklist.size()));
			if (tool.getType().getMaxDurability() <= durability) {
				player.sendMessage(this.getJPName() + ChatColor.RED
						+ ":発動に必要なツールの耐久値が足りません");
				return false;
			}
		}

		// マナを確認
		double usemana = this.getMana(breaklist.size());

		if (!Mm.hasMana(usemana)) {
			player.sendMessage(this.getJPName() + ChatColor.RED
					+ ":発動に必要なマナが足りません");
			return false;
		}

		// break;
		for(Block b : breaklist){
			b.setType(Material.AIR);
			b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND,b.getType());
		}

		Mm.decrease(usemana);
		tool.setDurability(durability);

		this.runCoolDownTask(breaklist.size());
		return true;
	}

	@Override
	public String getJPName() {
		return ChatColor.YELLOW + "" + ChatColor.BOLD + "エクスプロージョン"
				+ ChatColor.RESET;
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
		return breaknum / (Math.pow(breaknum, 1 / 5)) - 1;
	}

	@Override
	public int getCooldown(int breaknum) {
		return (int) ((Math.pow(breaknum, 1 / 4)) - 1) * 20;
	}

	@Override
	public long getSpendAP(int breaknum) {
		return (long)breaknum * 1;
	}

	@Override
	public int getMaxBreakNum() {
		return 4000;
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
	public int getMaxTotalSize() {
		return 110;
	}



}
