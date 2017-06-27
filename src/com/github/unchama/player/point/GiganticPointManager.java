package com.github.unchama.player.point;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.point.GiganticPointTableManager;
import com.github.unchama.sql.point.PointTableManager;

/**
 * Created by Mon_chi on 2017/06/22.
 */
public class GiganticPointManager extends PointManager {

    public GiganticPointManager(GiganticPlayer gp) {
        super(gp, GiganticPointTableManager.class);
    }
}
