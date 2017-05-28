package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;

import java.math.BigDecimal;

/**
 * Created by karayuu on 2017/05/19.
 */
public class BuildBlockIncrementEvent extends CustomEvent{
    private GiganticPlayer gp;
    private BigDecimal increase;
    private BigDecimal before_all, after_all;

    public BuildBlockIncrementEvent(GiganticPlayer gp, BigDecimal increase, BigDecimal after_all) {
        this.gp = gp;
        this.increase = increase;
        this.before_all = after_all.subtract(increase);
        this.after_all = after_all;
    }

    /**
     * 増加量を取得
     */
    public double getIncrease() {
        return increase.doubleValue();
    }

    /**
     * 増加前の建築量を取得
     */
    public double getBefore_all() {
        return before_all.doubleValue();
    }

    /**
     * 増加後の建築量を取得
     */
    public double getAfter_all() {
        return after_all.doubleValue();
    }

    /**
     * GiganticPlayerを返します
     */
    public GiganticPlayer getGiganticPlayer() {
        return gp;
    }
}
