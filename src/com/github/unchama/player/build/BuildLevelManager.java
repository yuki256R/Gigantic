package com.github.unchama.player.build;

import java.util.LinkedHashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;

public class BuildLevelManager extends DataManager implements Initializable{

	//建築レベル
	private int buildlevel;
	// 各レベルのデータ値を格納します．
	public static LinkedHashMap<Integer, BuildLevelData> buildlevelmap;
	
	public BuildLevelManager(GiganticPlayer gp){
		super(gp);
	}
	@Override
	public void init() {
		
	}
	
	/**buildlevelmapをセットします。Enable時に1度だけ実行してください
	 * 
	 */
	public static void setBuildLevelMap(){
		buildlevelmap = new LinkedHashMap<Integer, BuildLevelData>();
		for(int level = 0; level<= 100; level++){
			buildlevelmap.put(level, new BuildLevelData(level));
		}
	}

	/**レベルアップ可能かを調べるメソッドです
	 * 
	 * 
	 */
	public boolean calcLevelup(){
		double buildnum = gp.getManager(BuildManager.class).getTotalbuildnum();
		
	}
}
