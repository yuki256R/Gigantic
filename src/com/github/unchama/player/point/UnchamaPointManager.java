package com.github.unchama.player.point;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.point.UnchamaPointTableManager;

/**
 * Created by Mon_chi on 2017/06/22.
 */
public class UnchamaPointManager extends PointManager implements UsingSql {

    public UnchamaPointManager(GiganticPlayer gp) {
        super(gp, UnchamaPointTableManager.class);
    }
}
