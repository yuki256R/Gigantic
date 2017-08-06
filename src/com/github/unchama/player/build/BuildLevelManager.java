package com.github.unchama.player.build;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.bukkit.Bukkit;

import com.github.unchama.event.BuildLevelUpEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

/**
 * @author karayuu
 */
public class BuildLevelManager extends DataManager{

	//建築レベル
	private int buildlevel;
	// 各レベルのデータ値を格納します．
	public static LinkedHashMap<Integer, BuildLevelData> buildlevelmap = new LinkedHashMap<Integer, BuildLevelData>(){
		{
			for(int level = 1; level<= 100; level++){
				put(level, new BuildLevelData(level));
			}
		}
	};

	public BuildLevelManager(GiganticPlayer gp) {
		super(gp);
	}

	public void onAvailable() {
		this.calcLevel();
	}

	/**レベルアップ可能かを調べるメソッドです
	 * @return
	 */
	public boolean canLevelup() {
		BigDecimal buildnum = gp.getManager(BuildManager.class).getTotalbuildnum();

		if (!buildlevelmap.containsKey(buildlevel + 1)) {
		    return false;
        }

        return buildlevelmap.get(buildlevel + 1).getNeed_buildnum() <= buildnum.doubleValue();
	}

	/**初期処理でプレイヤーのレベルを取得します
	 *
	 */
	public void calcLevel() {
		while(canLevelup()) {
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

    /**
     * レベルが上がるまで、レベルデータを更新します
     */
    public void checkLevel() {
        while (this.canLevelup()) {
            Bukkit.getServer().getPluginManager().callEvent(new BuildLevelUpEvent(gp, buildlevel + 1));
            buildlevel++;
        }
    }

	/**レベルアップまでに必要な建築量を取得します
	 *
	 * @return
	 */
	public BigDecimal getRemainingBuildBlock(){
		BigDecimal buildnum = gp.getManager(BuildManager.class).getTotalbuildnum();
		return this.buildlevel < 100 ? new BigDecimal(buildlevelmap.get(this.buildlevel + 1).getNeed_buildnum()).subtract(buildnum): BigDecimal.ZERO;
	}

	/**現在のプレイヤーの建築レベルを取得します
	 *
	 * @return
	 */
	public int getBuildLevel(){
		return this.buildlevel;
	}

	/**プレイヤーのレベルを一時的に設定します．
	 * このレベルはプレイヤーがログアウトすると戻ります．
	 *
	 * @param level
	 */
	public void setLevel(int level) {
		BuildManager m = gp.getManager(BuildManager.class);
		BigDecimal debugNum = m.getDebugBuildNum();

		double after = buildlevelmap.get(level).getNeed_buildnum();
		double before = m.getTotalbuildnum().subtract(debugNum).doubleValue();
		//所望レベルまでの必要整地量を計算
		double dif = after - before;
		m.setTotalbuildnum(new BigDecimal(after));
		m.setDebugBuildNum(new BigDecimal(dif));
		this.buildlevel = level;
	}
}
