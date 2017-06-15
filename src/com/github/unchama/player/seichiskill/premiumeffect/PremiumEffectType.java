/**
 *
 */
package com.github.unchama.player.seichiskill.premiumeffect;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.seichiskill.EffectCategory;
import com.github.unchama.player.seichiskill.effect.NormalEffectRunner;
import com.github.unchama.player.seichiskill.moduler.EffectRunner;
import com.github.unchama.util.Util;
import com.github.unchama.yml.CustomHeadManager;

/**
 * @author tar0ss
 *
 */
public enum PremiumEffectType {
	MAGIC(1, NormalEffectRunner.class, ChatColor.RED + "マジック", "鶏が出る手品", 10, "clown"), ;

	private static final PremiumEffectType[] etList = PremiumEffectType.values();
	private static final HashMap<Integer,PremiumEffectType> idMap = new HashMap<Integer,PremiumEffectType>(){
		{
			for(PremiumEffectType et : etList){
				put(et.getId(),et);
			}
		}
	};
	//識別番号
	private final int id;
	//クラス
	private final Class<? extends EffectRunner> e_Class;
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
	private PremiumEffectType(int id, Class<? extends EffectRunner> e_Class, String name, String lore,
			int useDonatePoint, String headname) {
		this.id = EffectCategory.NORMAL.getEffectID(id);
		this.e_Class = e_Class;
		this.name = name;
		this.lore = lore;
		this.useDonatePoint = useDonatePoint;
		this.headname = headname;
	}

	public Class<? extends EffectRunner> getRunnerClass() {
		return e_Class;
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


	public static Class<? extends EffectRunner> getRunnerClass(int effect_id) {
		return idMap.get(effect_id).getRunnerClass();
	}

	public static String getNamebyID(int effect_id) {
		return idMap.get(effect_id).getName();
	}

	public static ItemStack getSellectButton(int effect_id) {
		PremiumEffectType et = idMap.get(effect_id);
		ItemStack is = Gigantic.yml.getManager(CustomHeadManager.class).getMobHead(et.getHeadname());
		Util.setDisplayName(is, et.getName());
		Util.setLore(is, et.getLore());
		return is;
	}
}
