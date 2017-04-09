package com.github.unchama.player.build;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.BuildTableManager;
import com.github.unchama.yml.ConfigManager;


public class BuildManager extends DataManager implements UsingSql{
    //トータル設置ブロック数
    private double totalbuildnum;
    private int build_num_1min;

    BuildTableManager tm = sql.getManager(BuildTableManager.class);
    ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

    public BuildManager(GiganticPlayer gp) {
        super(gp);
    }

    @Override
    public void save(Boolean loginflag) {
        tm.save(gp, loginflag);
    }


    /**総建築量に追加処理(1分ごとに呼び出すこと)
     * ただしconfigから取得した1分間の最大増加量を超えた場合最大増加量以上は増やさない
     * 実行時点で1minはリセットされる
     *
     */
    public void calcBuildNum(){
        if(this.build_num_1min <= config.getBuildNum1minLimit()){
            this.totalbuildnum += this.build_num_1min;
        }else{
            this.totalbuildnum += config.getBuildNum1minLimit();
        }
        this.build_num_1min = 0;
    }

    /**1分間の設置量を増加
     *
     * @param buildnum_1min
     */
    public void addBuild_Num_1min(int buildnum_1min){
        this.build_num_1min += buildnum_1min;
    }

    /**1分間の設置量を取得
     *
     * @return
     */
    public int getBuild_num_1min(){
        return this.build_num_1min;
    }

    /**総建築量を取得
     *
     * @return
     */
    public double getTotalbuildnum(){
        return this.totalbuildnum;
    }

    /**総建築量を設定
     *
     *@param totalbuildnum
     */
    public void setTotalbuildnum(double totalbuildnum){
        this.totalbuildnum = totalbuildnum;
    }
}
