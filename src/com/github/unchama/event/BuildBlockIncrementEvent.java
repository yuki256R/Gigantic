package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;


/**
 * @author karayuu
 */
public class BuildBlockIncrementEvent extends CustomEvent{
    private GiganticPlayer gp;
    private double increase;
    private double before_all, after_all;

    public BuildBlockIncrementEvent(GiganticPlayer gp, double increase, double after_all) {
        this.gp = gp;
        this.increase = increase;
        this.before_all = after_all-increase;
        this.after_all = after_all;
    }

    /**
     * 増加量を取得
     */
    public double getIncrease() {
        return increase;
    }

    /**
     * 増加前の建築量を取得
     */
    public double getBefore_all() {
        return before_all;
    }

    /**
     * 増加後の建築量を取得
     */
    public double getAfter_all() {
        return after_all;
    }

    /**
     * GiganticPlayerを返します
     */
    public GiganticPlayer getGiganticPlayer() {
        return gp;
    }
}
