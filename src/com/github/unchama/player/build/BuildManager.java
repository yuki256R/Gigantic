package com.github.unchama.player.build;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.BuildTableManager;
import com.github.unchama.yml.DebugManager.DebugEnum;


public class BuildManager extends DataManager implements UsingSql{
    //トータル設置ブロック数
    private double totalbuildnum;
    private double build_num_1min;

    BuildTableManager tm = sql.getManager(BuildTableManager.class);

    public BuildManager(GiganticPlayer gp) {
        super(gp);
    }

    @Override
    public void save(Boolean loginflag) {
        tm.save(gp, loginflag);
    }


    /**総建築量に1ブロック分追加処理 + 総建築量更新処理
     * Step1.BlockPlaceEventでは1minを増加させる
     * Step2.BuildTaskの方で1minを1分ごとにリセット
     *
     */
    public void calcBuildNum(){
    	this.build_num_1min += 1;
        if(this.build_num_1min <= config.getBuildNum1minLimit()){
        	this.totalbuildnum += 1;
        }else{
        	debug.sendMessage(PlayerManager.getPlayer(gp),DebugEnum.BUILD,"1minでの建築量が上限(" + config.getBuildNum1minLimit() + ")を超えました。");
        }
    }

    /**1分間の設置量を設定
     *
     * @param buildnum_1min
     */
    public void setBuild_Num_1min(double buildnum_1min){
    	this.build_num_1min = buildnum_1min;
    }

    /**1分間の設置量を取得
     *
     * @return
     */
    public double getBuild_num_1min(){
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

    /**
     * 1分間の設置量に加算
     * @param addnum
     */
    public void addBuild_num_1min(double addnum) {
        this.build_num_1min += addnum;
    }
}
