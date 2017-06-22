/**
 *
 */
package com.github.unchama.player.seichiskill.giganticeffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.seichiskill.EffectCategory;
import com.github.unchama.player.seichiskill.effect.NormalEffectRunner;
import com.github.unchama.player.seichiskill.moduler.effect.EffectRunner;
import com.github.unchama.util.Util;
import com.github.unchama.yml.CustomHeadManager;

/**
 * @author tar0ss
 *
 */
public enum GiganticEffectType {
	MAGIC(1, NormalEffectRunner.class, ChatColor.RED + "マジック", "鶏が出る手品", 10, "clown"), ;

	private static final GiganticEffectType[] etList = GiganticEffectType.values();
	private static final HashMap<Integer,GiganticEffectType> idMap = new HashMap<Integer,GiganticEffectType>(){
		{
			for(GiganticEffectType et : etList){
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
	private GiganticEffectType(int id, Class<? extends EffectRunner> e_Class, String name, String lore,
			int useDonatePoint, String headname) {
		this.id = EffectCategory.GIGANTIC.getEffectID(id);
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
		GiganticEffectType et = idMap.get(effect_id);
		ItemStack is = Gigantic.yml.getManager(CustomHeadManager.class).getMobHead(et.getHeadname());
		Util.setDisplayName(is, et.getName());
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + et.getLore());
		lore.add(ChatColor.DARK_GREEN + "このエフェクトは寄付によって");
		lore.add(ChatColor.DARK_GREEN + "得られたポイント(GiganticPoint)で");
		lore.add(ChatColor.DARK_GREEN + "購入することができます．");
		lore.add(ChatColor.GREEN + "必要GP:" + et.getUseDonatePoint());
		Util.setLore(is, lore);
		return is;
	}
}
