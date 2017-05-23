package com.github.unchama.player.build;

import com.github.unchama.event.BuildBlockIncrementEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.BuildTableManager;
import org.bukkit.Bukkit;

import java.math.BigDecimal;


public class BuildManager extends DataManager implements UsingSql{
    //トータル設置ブロック数
    //private int totalbuildnum;
    private BigDecimal totalbuildnum;
    //private int build_num_1min;
    private BigDecimal build_num_1min = BigDecimal.ZERO;

    BuildTableManager tm = sql.getManager(BuildTableManager.class);

    public BuildManager(GiganticPlayer gp) {
        super(gp);
    }

    @Override
    public void save(Boolean loginflag) {
        tm.save(gp, loginflag);
    }

    /**1分間の設置量を設定
     *
     * @param buildnum_1min (BigDecimal)
     */
    public void setBuild_Num_1min(BigDecimal buildnum_1min){
    	this.build_num_1min = buildnum_1min;
    }

    /**1分間の設置量を取得
     *
     * @return (BigDecimal)
     */
    public BigDecimal getBuild_num_1min(){
        return this.build_num_1min;
    }

    /**総建築量を取得
     *
     * @return (BigDecimal)
     */
    public BigDecimal getTotalbuildnum(){
        return this.totalbuildnum;
    }

    /**総建築量を設定
     *
     *@param totalbuildnum (BigDecimal)
     */
    public void setTotalbuildnum(BigDecimal totalbuildnum){
        this.totalbuildnum = totalbuildnum;
    }

    /**
     * 1分間の設置量に加算
     * @param addnum (BigDecimal)
     */
    public void addBuild_num_1min(BigDecimal addnum) {
        /*
        int build_num_1min = this.build_num_1min;
        if (build_num_1min + addnum <= config.getBuildNum1minLimit()) {
            this.totalbuildnum += addnum;
            this.build_num_1min += addnum;

            if (build_num_1min != this.build_num_1min) {
                Bukkit.getPluginManager().callEvent(new BuildBlockIncrementEvent(gp,addnum,this.totalbuildnum));
                debug.sendMessage(PlayerManager.getPlayer(gp),DebugEnum.BUILD,"イベント呼び出し。increase:"
                        + addnum + ",afterall:" + this.totalbuildnum);
            }

        } else {
            //例えば、1分建築量が50で追加分が100だったら、100までは追加、つまり Limit - buildnum1min で追加。それ以外は破棄
            int increase = Integer.max(0,config.getBuildNum1minLimit() - this.build_num_1min);

            this.totalbuildnum += increase;
            this.build_num_1min += addnum;

            if (!(increase == 0)) {
                Bukkit.getPluginManager().callEvent(new BuildBlockIncrementEvent(gp,
                        increase,this.totalbuildnum));
                debug.sendMessage(PlayerManager.getPlayer(gp),DebugEnum.BUILD,"イベント呼び出し。increase:"
                        + increase + ",after_all:" + this.totalbuildnum);
            }

        }
        */
        BigDecimal build_num_1min = this.build_num_1min;
        BigDecimal check = build_num_1min.add(addnum);

        if (check.doubleValue() <= config.getBuildNum1minLimit().doubleValue()) {
            this.build_num_1min = this.build_num_1min.add(addnum);
            this.totalbuildnum = this.totalbuildnum.add(addnum);

            if (build_num_1min.doubleValue() != this.build_num_1min.doubleValue()) {
                Bukkit.getPluginManager().callEvent(new BuildBlockIncrementEvent(gp,addnum,this.totalbuildnum));
            }

        } else {
            //例えば、1分建築量が50で追加分が100だったら、100までは追加、つまり Limit - buildnum1min で追加。それ以外は破棄
            BigDecimal inc_ = config.getBuildNum1minLimit().subtract(this.build_num_1min);
            BigDecimal inc = BigDecimal.ZERO;
            if (inc_.doubleValue() >= 0) {
                inc = inc_;
            }

            this.totalbuildnum = this.totalbuildnum.add(inc);
            this.build_num_1min = this.build_num_1min.add(addnum);

            if (inc.compareTo(BigDecimal.ZERO) != 0) {
                Bukkit.getPluginManager().callEvent(new BuildBlockIncrementEvent(gp,
                        inc,this.totalbuildnum));
            }
        }
    }
}
