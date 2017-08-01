package com.github.unchama.item.moduler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapelessRecipe;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.item.GiganticItem;
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
	MANA_MEDIUM(3, "マナ回復(中)", 1200),
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
		run(p, this);
	}

	public static void run(Player p,ManaEffect effect){
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);
		ManaManager mM = gp.getManager(ManaManager.class);
		if(effect.equals(MANA_FULL)){
			mM.fullMana();
		}else{
			mM.increase((double)effect.getRecoverNum());
		}
		p.playSound(p.getLocation(), Sound.ENTITY_WITCH_DRINK, 1.0F, 1.2F);
	}

	public static void init(){
		addShapelessRecipe(GiganticItem.MANA_SMALL,4,GiganticItem.MANA_MEDIUM);
		addShapelessRecipe(GiganticItem.MANA_MEDIUM,9,GiganticItem.MANA_LARGE);
		addShapelessRecipe(GiganticItem.MANA_LARGE,9,GiganticItem.MANA_HUGE);
	}

	private static void addShapelessRecipe(GiganticItem ingredient, int num, GiganticItem gi) {
		ShapelessRecipe Recipe = new ShapelessRecipe(gi.getItemStack());
		Recipe.addIngredient(num,ingredient.getItemStack().getData());
		Bukkit.getServer().addRecipe(Recipe);
	}
}
