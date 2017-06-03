package com.github.unchama.player.build;

/**
 * @author karayuu
 */
public enum FurnessType {
    /**
     * MineStack一括クラフトシステム・精錬系クラフトで使用する列挙型
     * @author karayuu
     */

    NONE,
    LAVA_BUCKET("溶岩バケツ"),
	COAL("石炭"),
	NETHER_BRICK("ネザーレンガ"),//これ燃料じゃないけど利便性のため追加してます。
	;
    
    private final String JPname;
    
    private FurnessType(String jpname) {
    	this.JPname = jpname;
	}
    
    private FurnessType() {
    	this.JPname = null;
	}
    
    public String getJPname() {
		return this.JPname;
	}
}
