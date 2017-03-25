package com.github.unchama.player.skill;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.skill.SkillManager.SkillType;
import com.github.unchama.player.skill.moduler.BreakRange;
import com.github.unchama.player.skill.moduler.Coordinate;
import com.github.unchama.player.skill.moduler.Skill;
import com.github.unchama.util.breakblock.BreakUtil;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * 近距離スキル
 *
 * @author tar0ss
 *
 */
public class Explosion extends Skill {
	public static SkillType st = SkillType.EXPLOSION;

	private Boolean cooldown;
	private BreakRange range;

	public Explosion(GiganticPlayer gp) {
		super(gp);
		cooldown = false;
	}

	/**
	 * @return range
	 */
	public BreakRange getRange() {
		return range;
	}

	/**
	 * @param range セットする range
	 */
	public void setRange(BreakRange range) {
		this.range = range;
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
				if (Skill.canBreak(m) || m.equals(Material.STATIONARY_LAVA)) {
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
					breaklist.size() + 10 * lavalist.size()));
			if(tool.getType().getMaxDurability() <= durability){
				player.sendMessage(Explosion.getJPName() + ChatColor.RED + ":発動に必要なツールの耐久値が足りません");
				return false;
			}
		}

		//マナを確認
		double usemana = Explosion.getMana(breaklist.size());

		if(!Mm.hasMana(usemana)){
			player.sendMessage(Explosion.getJPName() + ChatColor.RED + ":発動に必要なマナが足りません");
			return false;
		}


		//break;


		Mm.decrease(usemana);
		tool.setDurability(durability);

		int cooltime = Explosion.getCooldown(breaklist.size());
		if(cooltime > 5){
			cooldown = true;
			Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
				@Override
				public void run() {
					cooldown = false;
				}
			}, cooltime);
		}
		return true;
	}

	@Override
	public ItemStack getSkillTypeInfo() {
		ItemStack is = new ItemStack(Explosion.getMenuMaterial());
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(Explosion.getJPName());
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		is.setItemMeta(im);
		return is;
	}

	/**
	 * クールダウン中にtrueを返します．
	 *
	 * @return
	 */
	public boolean isCoolDown() {
		return this.cooldown;
	}

	/**
	 * このスキルの日本語名を取得します．
	 *
	 * @return
	 */
	public static String getJPName() {
		return ChatColor.YELLOW + "" + ChatColor.BOLD + "エクスプロージョン"
				+ ChatColor.RESET;
	}

	/**
	 * メニューで使われる代表となるマテリアル名を取得します．
	 *
	 * @return
	 */
	public static Material getMenuMaterial() {
		return Material.COAL_ORE;
	}

	/**
	 * このスキルの解放可能レベルを取得します
	 *
	 * @return
	 */
	public static int getUnlockLevel() {
		return 10;
	}

	/**
	 * このスキルの解放に必要なAPを取得します．
	 *
	 * @return
	 */
	public static double getUnlockAP() {
		return 10;
	}

	/**
	 * 破壊したブロック数から消費するマナを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 1 / 5)) - 1;
	}

	/**
	 * 破壊したブロック数から発生するクールタイム(tick)を取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static int getCooldown(int breaknum) {
		return (int)((Math.pow(breaknum, 1 / 4)) - 1)*20;
	}

	/**
	 * 1ブロック範囲を増やすのに必要なAPを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getSpendAP(int breaknum) {
		return 1;
	}

	/**
	 * 1回の発動で破壊できる最大ブロック数を取得します．
	 *
	 * @return
	 */
	public static int getMaxBreakNum() {
		return 4000;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（高さ）を取得します
	 *
	 * @return
	 */
	public static int getMaxHeight() {
		return 50;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（幅）を取得します
	 *
	 * @return
	 */
	public static int getMaxWidth() {
		return 15;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（奥行）を取得します
	 *
	 * @return
	 */
	public static int getMaxDepth() {
		return 45;
	}

	/**
	 * 1回の発動で破壊できる最大範囲3つの合計の最大値を取得します
	 *
	 * @return
	 */
	public static int getMaxTotalSize() {
		return 110;
	}



}
