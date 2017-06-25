package com.github.unchama.sql.point;

import com.github.unchama.player.point.UnchamaPointManager;
import com.github.unchama.sql.Sql;

/**
 * Created by Mon_chi on 2017/06/18.
 */
public class UnchamaPointTableManager extends PointTableManager {

    public UnchamaPointTableManager(Sql sql) {
        super(sql, UnchamaPointManager.class);
    }

}
