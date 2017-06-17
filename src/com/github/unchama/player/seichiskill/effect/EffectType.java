package com.github.unchama.player.seichiskill.effect;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.seichiskill.EffectCategory;
import com.github.unchama.player.seichiskill.moduler.EffectRunner;
import com.github.unchama.util.Util;

/**
 * @author tar0ss
 *
 */
public enum EffectType {
	NORMAL(0,NormalEffectRunner.class,ChatColor.GREEN + "通常","エフェクトなし",0,Material.GRASS),
	DYNAMITE(1,DynamiteEffectRunner.class, ChatColor.RED + "ダイナマイト", "単純な爆発", 50, Material.TNT),
	BLIZZARD(2,NormalEffectRunner.class, ChatColor.AQUA + "ブリザード", "凍らせる", 70, Material.PACKED_ICE),
	METEO(3,NormalEffectRunner.class, ChatColor.DARK_RED + "メテオ", "隕石を落とす", 100, Material.FIREBALL), ;

	private static final EffectType[] etList = EffectType.values();
	private static final HashMap<Integer,EffectType> idMap = new HashMap<Integer,EffectType>(){
		{
			for(EffectType et : etList){
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
	//使用する投票ポイント
	private final int useVotePoint;
	//メニューで使用するマテリアル
	private final Material menuMaterial;



	/**
	 * @param name
	 * @param lore
	 * @param useVotePoint
	 * @param menuMaterial
	 */
	private EffectType(int id,Class<? extends EffectRunner> e_Class, String name, String lore, int useVotePoint, Material menuMaterial) {
		this.id = EffectCategory.NORMAL.getEffectID(id);
		this.e_Class = e_Class;
		this.name = name;
		this.lore = lore;
		this.useVotePoint = useVotePoint;
		this.menuMaterial = menuMaterial;
	}

	public Class<? extends EffectRunner> getRunnerClass(){
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
	 * @return useVotePoint
	 */
	public int getUseVotePoint() {
		return useVotePoint;
	}

	/**
	 * @return menuMaterial
	 */
	public Material getMenuMaterial() {
		return menuMaterial;
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
		EffectType et = idMap.get(effect_id);
		ItemStack is = new ItemStack(et.getMenuMaterial());
		Util.setDisplayName(is, et.getName());
		Util.setLore(is, et.getLore());
		return is;
	}

}
