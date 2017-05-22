package com.github.unchama.growthtool;

import org.bukkit.entity.Player;

import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.growthtool.moduler.GrowthToolManager;

public class Mebius extends GrowthToolManager {
	public Mebius(GrowthToolType type) {
		super(type);
	}

	@Override
	protected boolean isEquip(Player player) {
		return false;
	}

	@Override
	public boolean rename(Player player, String name) {
		return false;
	}
}
