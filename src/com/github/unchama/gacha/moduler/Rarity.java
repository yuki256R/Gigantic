package com.github.unchama.gacha.moduler;

public enum Rarity {
	GIGANTIC(4,"ギガンティック大当たり",0.0001),BIG(3,"大当たり",0.001),WIN(2,"当たり",0.01),LOSE(1,"はずれ",0.05),OTHER(0,"おまけ",0.05),TICKET(-1),APPLE(-2)
	;

	private int id;
	private String rarityname;
	private double probability;

	Rarity(int id){
		this(id,"",-1);
	}
	Rarity(int id ,String rarityname,double probability){
		this.id = id;
		this.rarityname = rarityname;
		this.probability = probability;
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
}
