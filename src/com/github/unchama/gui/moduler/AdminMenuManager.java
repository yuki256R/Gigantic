package com.github.unchama.gui.moduler;

import org.bukkit.Sound;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gigantic.Gigantic;

/**
 * @author tar0ss
 *
 */
public abstract class AdminMenuManager extends GuiMenuManager {
	protected Gacha gacha = Gigantic.gacha;

	@Override
	protected void setKeyItem() {
	}

	@Override
	public String getClickType() {
		return "";
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_CHEST_LOCKED;
	}

	@Override
	public float getVolume() {
		return 1F;
	}

	@Override
	public float getPitch() {
		return 0.6F;
	}

}
