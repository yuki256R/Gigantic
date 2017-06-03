package com.github.unchama.gacha.moduler;

import org.bukkit.Sound;
/**
 * @author tar0ss
 *
 */
public enum Rarity {
	GIGANTIC(4,"ギガンティック大当たり",0.0001, Sound.ENTITY_ENDERDRAGON_DEATH),//
	BIG(3,"大当たり",0.001, Sound.ENTITY_WITHER_SPAWN),//
	WIN(2,"当たり",0.01, null),//
	LOSE(1,"はずれ",0.05, null),//
	OTHER(0,"おまけ",0.05, null),//
	TICKET(-1),//
	APPLE(-2)
	;

	private int id;
	private String rarityname;
	private double probability;
	private Sound sound;

	Rarity(int id){
		this(id,"",-1,null);
	}
	Rarity(int id ,String rarityname,double probability, Sound sound_){
		this.id = id;
		this.rarityname = rarityname;
		this.probability = probability;
		this.sound = sound_;
	}


	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	public static Rarity getRarity(int id){
		for(Rarity r : values()){
			if(r.getId() == id){
				return r;
			}
		}
		return null;
	}


	public String getRarityName() {
		return this.rarityname;
	}
	public double getProbability() {
		return this.probability;
	}
	public Sound getSound() {
		return this.sound;
	}
}
