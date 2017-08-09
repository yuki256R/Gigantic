package com.github.unchama.sql.point;

import com.github.unchama.player.point.GiganticPointManager;
import com.github.unchama.sql.Sql;

/**
 * Created by Mon_chi on 2017/06/22.
 */
public class GiganticPointTableManager extends PointTableManager {

    public GiganticPointTableManager(Sql sql) {
        super(sql, GiganticPointManager.class);
    }
}
