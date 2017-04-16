package com.github.unchama.player.build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildLevelData {
	//lv
	public static final List<Integer> levellist = new ArrayList<Integer>(Arrays.asList(
			0, 50, 100, 200, 300, 
			450, 600, 900, 1200, 1600, //Lv.10
			2000, 2500, 3000, 3600, 4300,
			5100, 6000, 7000, 8200, 9400, //Lv.20
			10800, 12200, 13800, 15400, 17200,
			19000, 21000, 23000, 25250, 27500, //Lv.30
			30000, 32500, 35500, 38500, 42000,
			45500, 49500, 54000, 59000, 64000, //Lv.40
			70000, 76000, 83000, 90000, 98000,
			106000, 115000, 124000, 133000, 143000, //Lv.50
			153000, 163000, 174000, 185000, 196000,
			208000, 220000, 232000, 245000, 258000, //Lv.60
			271000, 285000, 299000, 313000, 328000,
			343000, 358000, 374000, 390000, 406000, //Lv.70
			423000, 440000, 457000, 475000, 493000,
			511000, 530000, 549000, 568000, 588000, //Lv.80
			608000, 628000, 648000, 668000, 688000,
			708000, 728000, 748000, 768000, 788000, //Lv.90
			808000, 828000, 848000, 868000, 888000,
			908000, 928000, 948000, 968000, 1000000, //Lv.100
			5000000));
	
	//レベル
	private int buildlevel;
	//このレベルになるのに必要な建築量
	private int need_buildblock;
	//次のレベルになるのに必要な建築量
	private int next_buildblock;
	
	BuildLevelData(int level){
		this.buildlevel = level;
		this.need_buildblock = this.calcNeed_buildblock(level);
		this.next_buildblock = this.calcNext_buildblock(level);
	}
	
	/**このレベルになるのに必要な建築量を計算します
	 * 
	 * @param
	 * @return
	 */
	private int calcNeed_buildblock(int level){
		return levellist.get(level);
	}
	
	/**次のレベルになるのに必要な建築量を計算します
	 * 
	 * @param
	 * @return
	 */
	private int calcNext_buildblock(int level){
		return levellist.get(level + 1);
	}
	
	/**建築レベルを取得します
	 * 
	 * @return
	 */
	public int getBuildlevel(){
		return this.buildlevel;
	}
	
	/**このレベルで必要な建築量(need_buildblock)を取得します
	 * 
	 * @return
	 */
	public int getNeed_buildblock(){
		return this.need_buildblock;
	}
	
	/**次のレベルに必要な建築量(next_buildblock)を取得します
	 * 
	 * @return
	 */
	public int getNext_buildblock(){
		return this.next_buildblock;
	}
}
