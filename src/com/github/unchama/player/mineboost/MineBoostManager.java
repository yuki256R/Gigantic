package com.github.unchama.player.mineboost;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlock;
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
		this.updataNumberOfPeople();
	}

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

		debug.sendMessage(p, DebugEnum.MINEBOOST, "boostlevel=");

		for (BoostType bt : boostMap.keySet()) {
			MineBoost mb = boostMap.get(bt);
			boostlevel += mb.getAmplifier();
			debug.sendMessage(p, DebugEnum.MINEBOOST, "   " + bt.name() + ":"
					+ mb.getAmplifier());
		}

		if (boostlevel > 200) {
			boostlevel = 200;
		}

		if (boostlevel <= 0) {
		} else {
			boostlevel--;
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,
					Integer.MAX_VALUE, boostlevel, false, false), true);
			debug.sendMessage(p, DebugEnum.MINEBOOST, "addPotionEffect:"
					+ (boostlevel + 1) + " for player:" + p.getName());
		}
	}

	public void refresh(){
		SideBarManager sm = gp.getManager(SideBarManager.class);
		sm.updateInfo(Information.MINING_SPEED, boostlevel + 1);
		sm.refresh();
	}
	public void updataNumberOfPeople() {
		short playernum = (short) plugin.getServer().getOnlinePlayers().size();
		short amplifier = (short) (playernum * config.getNumOfPeopleRate());
		boostMap.put(BoostType.NUMBER_OF_PEOPLE, new MineBoost(amplifier));
		add();
	}

	public void updataMinuteMine() {
		float minenum = 0;
		for (MineBlock mb : gp.getManager(MineBlockManager.class).datamap
				.values()) {
			minenum += mb.getDifOnAMinute();
		}
		short amplifier = (short) (minenum * config.getMinuteMineRate());
		boostMap.put(BoostType.MINUTE_MINE, new MineBoost(amplifier));
		add();
	}

}
