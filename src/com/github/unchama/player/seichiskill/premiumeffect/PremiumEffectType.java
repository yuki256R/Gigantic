/**
 *
 */
package com.github.unchama.player.seichiskill.premiumeffect;

import org.bukkit.ChatColor;

/**
 * @author tar0ss
 *
 */
public enum PremiumEffectType {
	MAGIC(ChatColor.RED + "マジック","鶏が出る手品",10,"clown"),
	;

	//エフェクト名
	private final String name;
	//説明文
	private final String lore;
	//使用する寄付ポイント
	private final int useDonatePoint;
	//メニューで使用するMobHead
	private final String headname;
	/**
	 * @param name
	 * @param lore
	 * @param useDonatePoint
	 * @param headname
	 */
	private PremiumEffectType(String name, String lore, int useDonatePoint, String headname) {
		this.name = name;
		this.lore = lore;
		this.useDonatePoint = useDonatePoint;
		this.headname = headname;
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return lore
	 */
	public String getLore() {
		return lore;
	}
	/**
	 * @return useDonatePoint
	 */
	public int getUseDonatePoint() {
		return useDonatePoint;
	}
	/**
	 * @return headname
	 */
	public String getHeadname() {
		return headname;
	}


}



