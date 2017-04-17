package com.github.unchama.player.build;

import java.util.LinkedHashMap;

import org.bukkit.Bukkit;

import com.github.unchama.event.BuildLevelUpEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

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
		this.calcLevel();
	}
	
	/**buildlevelmapをセットします。Enable時に1度だけ実行してください
	 * 
	 */
	public static void setBuildLevelMap(){
		buildlevelmap = new LinkedHashMap<Integer, BuildLevelData>();
		for(int level = 1; level<= 100; level++){
			buildlevelmap.put(level, new BuildLevelData(level));
		}
	}

	/**レベルアップ可能かを調べるメソッドです
	 * @param buildnum:建築量
	 * @param buildlevel:建築レベル
	 * @return
	 */
	public boolean canLevelup(){
		double buildnum = gp.getManager(BuildManager.class).getTotalbuildnum();
		return (buildlevelmap.get(buildlevel).getNeed_buildblock() <= buildnum) ? true : false;
	}
	
	/**初期処理でプレイヤーのレベルを取得します
	 * 
	 */
	private void calcLevel(){
		this.buildlevel = 1;
		while(canLevelup()){
			buildlevel++;
		}
	}
	
	/**レベルが上がるまで、レベルデータを更新します
	 * 
	 * @return 1でも上がったらtrue
	 */
	public boolean updateLevel(){
		boolean changeflag = false;
		while(this.canLevelup()){
			Bukkit.getServer().getPluginManager().callEvent(new BuildLevelUpEvent(gp, buildlevel + 1));
			buildlevel++;
			changeflag = true;
		}
		return changeflag;
	}
	
	/**レベルアップまでに必要な建築量を取得します
	 * 
	 * @return
	 */
	public double getRemainingBuildBlock(){
		double buildnum = gp.getManager(BuildManager.class).getTotalbuildnum();
		return this.buildlevel < 100 ? (double)buildlevelmap.get(this.buildlevel - 1).getNext_buildblock() - buildnum: 0.0;
	}
	
	/**現在のプレイヤーの建築レベルを取得します
	 * 
	 * @return
	 */
	public int getBuildLevel(){
		return this.buildlevel;
	}
}
