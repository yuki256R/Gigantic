/**
 *
 */
package com.github.unchama.player.seichiskill.premiumeffect;

import org.bukkit.ChatColor;

import com.github.unchama.player.seichiskill.EffectCategory;

/**
 * @author tar0ss
 *
 */
public enum PremiumEffectType {
	MAGIC(1, ChatColor.RED + "マジック", "鶏が出る手品", 10, "clown"), ;

	//識別番号
	private final int id;
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
	private PremiumEffectType(int id, String name, String lore, int useDonatePoint, String headname) {
		this.id = EffectCategory.NORMAL.getEffectID(id);
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

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

}
