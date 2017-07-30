package com.github.unchama.item.moduler;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;

/**
 * @author karayuu
 */

/**
 * ガチャりんごマナ回復効果の一覧
 */
public enum ManaEffect {
	/** マナ完全回復 */
	MANA_FULL(1, "マナ完全回復", -1),
	/** マナ回復(小) */
	MANA_SMALL(2, "マナ回復(小)", 300),
	/** マナ回復(中) */
	MANA_MEDIUM(3, "マナ回復(中)", 1500),
	/** マナ回復(大) */
	MANA_LARGE(4, "マナ回復(大)", 10000),
	/** マナ回復(極) */
	MANA_HUGE(5, "マナ回復(極)", 100000), ;

	/**書き換え禁止******************************/
	private static final ChatColor MANACOLOR = ChatColor.AQUA;
	/******************************************/

	/** 識別番号 */
	private final int id;
	/** エンチャント名*/
	private final String name;
	/** マナ回復量*/
	private final long recoverNum;

	/** コンストラクタ */
	ManaEffect(int id, String name, long recoverNum) {
		this.id = id;
		this.name = name;
		this.recoverNum = recoverNum;
	}

	/**
	 * 識別番号を返します
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return MANACOLOR + name;
	}

	/**
	 * @return recoverNum
	 */
	public long getRecoverNum() {
		return recoverNum;
	}

	public void run(Player p) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);
		ManaManager mM = gp.getManager(ManaManager.class);
		if(this.equals(MANA_FULL)){
			mM.fullMana();
		}else{
			mM.increase((double)this.getRecoverNum());
		}
		p.playSound(p.getLocation(), Sound.ENTITY_WITCH_DRINK, 1.0F, 1.2F);
	}
}
