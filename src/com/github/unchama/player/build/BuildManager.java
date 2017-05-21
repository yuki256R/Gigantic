package com.github.unchama.player.build;

import com.github.unchama.event.BuildBlockIncrementEvent;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.BuildTableManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import org.bukkit.Bukkit;

import java.math.BigDecimal;


public class BuildManager extends DataManager implements UsingSql{
    //トータル設置ブロック数
    private int totalbuildnum;
    private int build_num_1min;

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
            Bukkit.getPluginManager().callEvent(new BuildBlockIncrementEvent(gp,1,this.totalbuildnum));
            debug.sendMessage(PlayerManager.getPlayer(gp),DebugEnum.BUILD,"イベント呼び出し。increase:1"
                    + ",after_all" + this.totalbuildnum);
        }else{
        	debug.sendMessage(PlayerManager.getPlayer(gp),DebugEnum.BUILD,
                    "1minでの建築量が上限(" + config.getBuildNum1minLimit() + ")を超えました。");
        }
    }

    /**1分間の設置量を設定
     *
     * @param buildnum_1min (BigDecimal)
     */
    public void setBuild_Num_1min(int buildnum_1min){
    	this.build_num_1min = buildnum_1min;
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
    public int getTotalbuildnum(){
        return this.totalbuildnum;
    }

    /**総建築量を設定
     *
     *@param totalbuildnum
     */
    public void setTotalbuildnum(int totalbuildnum){
        this.totalbuildnum = totalbuildnum;
    }

    /**
     * 1分間の設置量に加算
     * @param addnum
     */
    public void addBuild_num_1min(int addnum) {
        int build_num_1min = this.build_num_1min;
        if (build_num_1min + addnum <= config.getBuildNum1minLimit()) {
            this.totalbuildnum += addnum;
            this.build_num_1min += addnum;
            Bukkit.getPluginManager().callEvent(new BuildBlockIncrementEvent(gp,addnum,this.totalbuildnum));
            debug.sendMessage(PlayerManager.getPlayer(gp),DebugEnum.BUILD,"イベント呼び出し。increase:"
                    + addnum + ",afterall:" + this.totalbuildnum);

        } else {
            int increase = (build_num_1min + addnum - config.getBuildNum1minLimit());
            this.totalbuildnum += increase;
            this.build_num_1min += addnum;
            Bukkit.getPluginManager().callEvent(new BuildBlockIncrementEvent(gp,
                    addnum - (config.getBuildNum1minLimit() - build_num_1min),this.totalbuildnum));
            debug.sendMessage(PlayerManager.getPlayer(gp),DebugEnum.BUILD,"イベント呼び出し。increase:"
                    + (addnum - (config.getBuildNum1minLimit() - this.build_num_1min)) + ",after_all:"
                    + this.totalbuildnum);
        }


    }
}
