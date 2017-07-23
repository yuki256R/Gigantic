package com.github.unchama.player.seichiskill.passive.securebreak;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import net.coreprotect.CoreProtectAPI;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.seichiskill.passive.PassiveSkillTypeMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.PassiveSkillManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.toolpouch.ToolPouchManager;
import com.github.unchama.util.Util;
import com.github.unchama.util.breakblock.BreakUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * 破壊したブロックの周囲の液体を自動凝固するパッシブスキル
 *
 * @author tar0ss
 *
 */
public class SecureBreakManager extends PassiveSkillManager{
	private GuiMenu guimenu = Gigantic.guimenu;
	protected static WorldGuardPlugin Wg;
	protected static CoreProtectAPI Cp;
	protected ManaManager Mm;
	protected SideBarManager Sm;
	protected ToolPouchManager Pm;
	protected SeichiLevelManager Lm;

	private boolean toggle;

	public SecureBreakManager(GiganticPlayer gp) {
		super(gp);
		Wg = Util.getWorldGuard();
		Cp = Util.getCoreProtect();
		toggle = true;
	}


	public void onAvailable() {
		this.Mm = gp.getManager(ManaManager.class);
		this.Sm = gp.getManager(SideBarManager.class);
		this.Pm = gp.getManager(ToolPouchManager.class);
		this.Lm = gp.getManager(SeichiLevelManager.class);
	}

	/**
	 * 自動凝固処理
	 *
	 * @param player
	 * @param tool
	 * @param block
	 */
	public void run(Player player, ItemStack tool, Block block,
			ActiveSkillManager skill) {
		if (!this.getToggle())
			return;
		// 凝固する液体のリストデータ
		List<Block> liquidlist = new ArrayList<Block>();
		// プレイヤーが凝固する範囲情報を取得
		BreakRange range = skill.getRange();
		// プレイヤーの向いている方角の凝固ブロック座標リストを取得
		List<Coordinate> surroundcoord = range.getSurroundCoordList(player);

		// プレイヤーのいる座標を取得する．
		Location loc = player.getLocation().getBlock().getLocation();

		// 凝固するとプレイヤーが埋まってしまう座標は除外リストに入れる，
		List<Location> exLocation = new ArrayList<Location>(Arrays.asList(loc,
				loc.add(0, 1, 0)));

		// まず凝固するブロックの総数を計算
		surroundcoord.forEach(c -> {
			Block rb = block.getRelative(c.getX(), c.getY(), c.getZ());
			Material m = rb.getType();
			if (ActiveSkillManager.isLiquid(m)) {
				// worldguardを確認Skilledflagを確認
				if (Wg.canBuild(player, rb.getLocation())
						&& canBelowBreak(player, block, rb)
						&& !rb.hasMetadata("Skilled")
						&& !exLocation.contains(rb.getLocation())) {
					liquidlist.add(rb);
				}
			}
		});

		if (liquidlist.isEmpty())
			return;

		// ツールの耐久を確認

		short durability = tool.getDurability();
		boolean unbreakable = tool.getItemMeta().spigot().isUnbreakable();
		// 使用する耐久値
		short useDurability = 0;

		if (!unbreakable) {
			if (durability > tool.getType().getMaxDurability()) {
				player.sendMessage(this.getJPName() + ChatColor.RED
						+ ":ツールの耐久値が不正です．");
				return;
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
					return;
				}
			}
		}

		// マナを確認
		double usemana = this.getMana(liquidlist.size());

		if (!Mm.hasMana(usemana)) {
			player.sendMessage(this.getJPName() + ChatColor.RED
					+ ":発動に必要なマナが足りません");
			return;
		}
		MineBlockManager mb = gp.getManager(MineBlockManager.class);
		// condens直前の処理
		liquidlist.forEach(b -> {
			Material m = b.getType();
			if (ActiveSkillManager.isLiquid(m)) {
				mb.increase(m);
			}
			// スキルで使用するブロックに設定
				b.setMetadata("Skilled", new FixedMetadataValue(plugin, true));
			});

		// condensの処理
		liquidlist.forEach(b -> {
			switch (b.getType()) {
			case STATIONARY_WATER:
			case WATER:
				b.setType(Material.PACKED_ICE);
				break;
			case LAVA:
			case STATIONARY_LAVA:
				b.setType(Material.MAGMA);
				break;
			default:
				break;
			}
		});

		// 最初のブロックのみコアプロテクトに保存する．
		ActiveSkillManager.logPlacement(player, liquidlist.get(0));

		// condens後の処理
		liquidlist.forEach(b -> {
			b.removeMetadata("Skilled", plugin);
		});

		Mm.decrease(usemana);
		tool.setDurability((short) (durability + useDurability));
	}

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

	public double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 0.14285714)) - 1;
	}

	private String getJPName() {
		return "" + ChatColor.RESET + ChatColor.AQUA + ChatColor.BOLD
				+ "セキュアブレイク" + ChatColor.RESET;
	}

	@Override
	public ItemStack getSkillTypeInfo() {
		SeichiLevelManager m = gp.getManager(SeichiLevelManager.class);
		int level = m.getLevel();
		ItemStack is;
		ItemMeta meta;
		if (level < config.getSecureBreakUnlockLevel()) {
			is = new ItemStack(Material.QUARTZ_ORE);
			meta = is.getItemMeta();
			meta.setDisplayName(this.getJPName());
			List<String> lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "各破壊系スキルで破壊した範囲の");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "周囲の液体を凝固させます");
			lore.add("" + ChatColor.RESET + ChatColor.RED + ChatColor.UNDERLINE
					+ "レベル" + config.getSecureBreakUnlockLevel() + "で自動解放されます．");
			meta.setLore(lore);
			is.setItemMeta(meta);
		} else if (this.getToggle()) {
			is = new ItemStack(Material.QUARTZ);
			meta = is.getItemMeta();
			meta.addEnchant(Enchantment.DIG_SPEED, 100, false);
			meta.setDisplayName(this.getJPName());
			List<String> lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "各破壊系スキルで破壊した範囲の");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "周囲の液体を凝固させます");
			lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "トグル："
					+ ChatColor.RESET + ChatColor.GREEN + "  ON");
			lore.add("" + ChatColor.RESET + ChatColor.GREEN
					+ ChatColor.UNDERLINE + "クリックでトグルを切り替えます");
			meta.setLore(lore);
			is.setItemMeta(meta);
		} else {
			is = new ItemStack(Material.QUARTZ);
			meta = is.getItemMeta();
			meta.setDisplayName(this.getJPName());
			List<String> lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "各破壊系スキルで破壊した範囲の");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "周囲の液体を凝固させます");
			lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "トグル："
					+ ChatColor.RESET + ChatColor.RED + "  OFF");
			lore.add("" + ChatColor.RESET + ChatColor.GREEN
					+ ChatColor.UNDERLINE + "クリックでトグルを切り替えます");
			meta.setLore(lore);
			is.setItemMeta(meta);
		}
		return is;
	}

	public void toggle() {
		this.setToggle(!toggle);
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

	public boolean getToggle() {
		return this.toggle;
	}

	@Override
	public void onClickTypeMenu(Player player) {
		this.toggle();
		guimenu.getManager(PassiveSkillTypeMenuManager.class).open(player, 0,
				true);
	}

	@Nullable
	public void runF(Player player, Block block, ItemStack tool,
			ActiveSkillManager skill) {
		if (!this.getToggle())
			return;
		// 凝固する液体のリストデータ
		List<Block> liquidlist = new ArrayList<Block>();
		// プレイヤーが凝固する範囲情報を取得
		BreakRange range = skill.getRange();
		// プレイヤーの向いている方角の凝固ブロック座標リストを取得
		List<Coordinate> topsurroundcoord = range
				.getTopSurroundCoordList(player);

		// プレイヤーのいる座標を取得する．
		Location loc = player.getLocation().getBlock().getLocation();

		// 凝固するとプレイヤーが埋まってしまう座標は除外リストに入れる，
		List<Location> exLocation = new ArrayList<Location>(Arrays.asList(loc,
				loc.add(0, 1, 0)));

		// まず凝固するブロックの総数を計算
		topsurroundcoord.forEach(c -> {
			Block rb = block.getRelative(c.getX(), c.getY(), c.getZ())
					.getRelative(BlockFace.UP);
			while (rb.getY() < 256) {
				Material m = rb.getType();
				if (ActiveSkillManager.canBreak(m)) {
					// worldguardを確認Skilledflagを確認
				if (Wg.canBuild(player, rb.getLocation())
						&& !rb.hasMetadata("Skilled")
						&& !exLocation.contains(rb.getLocation())) {
					if (ActiveSkillManager.isLiquid(m)) {
						liquidlist.add(rb);
					}
				}
			}
			rb = rb.getRelative(BlockFace.UP);
		}
	})	;

		if (liquidlist.isEmpty()) {
			return;
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
				return;
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
					return;
				}
			}
		}

		// マナを確認
		double usemana = this.getMana(liquidlist.size());

		if (!Mm.hasMana(usemana)) {
			player.sendMessage(this.getJPName() + ChatColor.RED
					+ ":発動に必要なマナが足りません");
			return;
		}
		MineBlockManager mb = gp.getManager(MineBlockManager.class);
		// condens直前の処理
		liquidlist.forEach(b -> {
			Material m = b.getType();
			if (ActiveSkillManager.isLiquid(m)) {
				mb.increase(m);
			}
			// スキルで使用するブロックに設定
				b.setMetadata("Skilled", new FixedMetadataValue(plugin, true));
			});

		// condensの処理
		liquidlist.forEach(b -> {
			switch (b.getType()) {
			case STATIONARY_WATER:
			case WATER:
				b.setType(Material.PACKED_ICE);
				break;
			case LAVA:
			case STATIONARY_LAVA:
				b.setType(Material.MAGMA);
				break;
			default:
				break;
			}
		});

		// 最初のブロックのみコアプロテクトに保存する．
		ActiveSkillManager.logPlacement(player, liquidlist.get(0));

		// condens後の処理
		liquidlist.forEach(b -> {
			b.removeMetadata("Skilled", plugin);
		});

		Mm.decrease(usemana);
		tool.setDurability((short) (durability + useDurability));
		return;
	}
}
