package com.github.unchama.player.mineboost;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class MineBoostManager extends DataManager implements Initializable {
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	public HashMap<BoostType, MineBoost> boostMap = new HashMap<BoostType, MineBoost>();

	public int boostlevel;

	public MineBoostManager(GiganticPlayer gp) {
		super(gp);

	}

	@Override
	public void init() {
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

		if (boostlevel > 122) {
			boostlevel = 122;
		}

		if (boostlevel <= 0) {
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
	 * @param MINUTE_MINE
	 *            or NUMBER_OF_PEOPLE
	 * @return 成否
	 */
	public boolean updata(BoostType boosttype) {
		double causenum;
		short amplifier;
		switch (boosttype) {
		case MINUTE_MINE:
			causenum = gp.getManager(MineBlockManager.class).all
					.getNum(TimeType.A_MINUTE);
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
		updata(boosttype,amplifier,0);
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

}
