package com.github.unchama.gacha.moduler;

public enum Rarity {
	GIGANTIC(4),BIG(3),WIN(2),LOSE(1),OTHER(0)
	;

	private int id;

	Rarity(int id){
		this.id = id;
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
}
