package com.github.unchama.player.seichiskill.passive.mineboost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.seichiskill.passive.PassiveSkillTypeMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.seichiskill.moduler.PassiveSkillManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class MineBoostManager extends PassiveSkillManager{
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	private GuiMenu guimenu = Gigantic.guimenu;

	private HashMap<BoostType, MineBoost> boostMap = new HashMap<BoostType, MineBoost>();

	private int boostlevel;
	private boolean toggle;

	private static int maxboostlevel = 122;

	public MineBoostManager(GiganticPlayer gp) {
		super(gp);
		toggle = true;
	}


	public void onAvailable() {
		this.updata(BoostType.NUMBER_OF_PEOPLE);
	}

	/**
	 * プレイヤーにポーションエフェクトを付与します．
	 *
	 */
	private void add() {
		Player p = plugin.getServer().getPlayer(gp.uuid);

		if (p == null) {
			return;
		}
		p.removePotionEffect(PotionEffectType.FAST_DIGGING);

		if (boostMap.isEmpty()) {
			return;
		}

		boostlevel = 0;

		debug.sendMessage(p, DebugEnum.MINEBOOST, "上昇値=");

		for (BoostType bt : boostMap.keySet()) {
			MineBoost mb = boostMap.get(bt);
			boostlevel += mb.getAmplifier();
			debug.sendMessage(p, DebugEnum.MINEBOOST, "   " + bt.getReason() + ": "
					+ mb.getAmplifier());
		}

		if (boostlevel > maxboostlevel) {
			boostlevel = maxboostlevel;
		} else if (!toggle) {
			boostlevel = 0;
		}

		if (boostlevel <= 0) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,
					0, 0, false, false), true);
			debug.sendMessage(p, DebugEnum.MINEBOOST, "MineBoost更新: "
					+ "0   プレイヤー名:" + p.getName());
		} else {
			boostlevel--;
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,
					Integer.MAX_VALUE, boostlevel, false, false), true);
			debug.sendMessage(p, DebugEnum.MINEBOOST, "MineBoost更新: "
					+ (boostlevel + 1) + " プレイヤー名:" + p.getName());
		}
	}

	/**
	 * サイドバーを更新します．
	 *
	 */
	public void refresh() {
		SideBarManager sm = gp.getManager(SideBarManager.class);
		sm.updateInfo(Information.MINING_SPEED, boostlevel + 1);
		sm.refresh();
	}

	/**
	 * 与えられたブーストタイプの更新を行います．
	 *
	 *
	 * @param  BoostType
	 * @return 成否
	 */
	public boolean updata(BoostType boosttype) {
		double causenum;
		short amplifier;
		switch (boosttype) {
		case MINUTE_MINE:
			causenum = gp.getManager(MineBlockManager.class).getAll(TimeType.A_MINUTE);
			amplifier = (short) (causenum * config.getMinuteMineRate());
			updata(boosttype, amplifier);
			return true;
		case NUMBER_OF_PEOPLE:
			causenum = (short) plugin.getServer().getOnlinePlayers().size();
			amplifier = (short) (causenum * config.getNumOfPeopleRate());
			updata(boosttype, amplifier);
			return true;
		case DRAGON_NIGHT_TIME:
		case VOTED_BONUS:
		case OTHERWISE:
		default:
			return false;
		}

	}

	/**
	 * ブーストタイプを与えられた値で更新します．
	 *
	 * @param boosttype
	 * @param amplifier
	 */
	public void updata(BoostType boosttype, short amplifier) {
		updata(boosttype, amplifier, 0);
	}

	/**ブーストタイプを与えられた値で更新し，与えられたtick数で効果を消します．
	 *
	 * @param boosttype
	 * @param amplifier
	 * @param tick
	 */
	public void updata(BoostType boosttype, short amplifier, long tick) {
		boostMap.put(boosttype, new MineBoost(amplifier));
		add();
		if (tick == 0)
			return;
		Bukkit.getScheduler().runTaskLaterAsynchronously(plugin,
				new Runnable() {
					@Override
					public void run() {
						Bukkit.getScheduler().runTask(plugin, new Runnable() {
							@Override
							public void run() {
								boostMap.remove(boosttype);
								add();
								refresh();
							}
						});
					}

				}, tick);
	}

	public boolean getToggle() {
		return this.toggle;
	}

	@Override
	public ItemStack getSkillTypeInfo() {
		ItemStack is;
		if (this.getToggle()) {
			is = new ItemStack(Material.CLAY_BRICK);
		} else {
			is = new ItemStack(Material.CLAY);
		}

		ItemMeta meta = is.getItemMeta();

		meta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD
				+ "マインブースト");
		List<String> lore = new ArrayList<String>();
		lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
				+ "様々な要因から採掘速度上昇効果を");
		lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
				+ "得られます．");
		if (this.getToggle()) {
			meta.addEnchant(Enchantment.DIG_SPEED, 100, false);
			lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "トグル："
					+ ChatColor.RESET + ChatColor.GREEN + "  ON");
		} else {
			lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "トグル："
					+ ChatColor.RESET + ChatColor.RED + "  OFF");
		}
		if (this.getBoostLv() == maxboostlevel) {
			lore.add("" + ChatColor.RESET + ChatColor.GOLD
					+ "現在の上昇量:" + (this.getBoostLv() + 1) + "(※最大値です)");
		} else {
			lore.add("" + ChatColor.RESET + ChatColor.GOLD
					+ "現在の上昇量:" + (this.getBoostLv() + 1));
		}
		lore.add("" + ChatColor.RESET + ChatColor.YELLOW + ChatColor.UNDERLINE
				+ "上昇量の内訳");
		for (BoostType bt : boostMap.keySet()) {
			MineBoost mb = boostMap.get(bt);
			boostlevel += mb.getAmplifier();
			lore.add("" + ChatColor.RESET + ChatColor.RED
					+ "+" + mb.getAmplifier() + " " + bt.getReason() + "から");
		}
		lore.add("" + ChatColor.RESET + ChatColor.GREEN + ChatColor.UNDERLINE
				+ "クリックでトグルを切り替えます");

		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}

	@Override
	public void onClickTypeMenu(Player player) {
		this.toggle();
		guimenu.getManager(PassiveSkillTypeMenuManager.class).open(player, 0, true);
	}

	public void toggle() {
		this.setToggle(!toggle);
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
		add();
		refresh();
	}

	public int getBoostLv() {
		return this.boostlevel;
	}

}
