package com.github.unchama.player.mineblock;

import java.util.HashMap;

import com.github.unchama.gigantic.Gigantic;

public class MineBlock {
	public enum TimeType {
		UNLIMITED, A_MINUTE, HALF_HOUR;
	}

	public static Gigantic plugin = Gigantic.plugin;

	private HashMap<TimeType, Double> nmap;

	public MineBlock() {
		this(0.0);
	}

	public MineBlock(double n) {
		nmap = new HashMap<TimeType, Double>();
		for (TimeType tt : TimeType.values()) {
			nmap.put(tt, new Double(0));
		}
	}

	public void increase(double increase) {
		nmap.forEach((tt, n) -> {
			n += increase;
		});
	}

	public double getNum(TimeType tt) {
		return nmap.get(tt);
	}

	public void reset(TimeType tt) {
		nmap.put(tt, new Double(0));
	}
}
