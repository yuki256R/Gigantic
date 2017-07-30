package com.github.unchama.player.point;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.point.PointTableManager;

/**
 * Created by Mon_chi on 2017/06/22.
 */
public class PointManager extends DataManager implements UsingSql {

    private PointTableManager tableManager;

    private int defaultPoint;
    private int currentPoint;

    public PointManager(GiganticPlayer gp, Class<? extends PointTableManager> tableManager) {
        super(gp);
        this.tableManager = sql.getManager(tableManager);
    }

    @Override
    public void save(Boolean loginflag) {
        tableManager.save(gp, loginflag);
    }

    public void init(int point) {
        this.defaultPoint = point;
        this.currentPoint = point;
    }

    public int getPoint() {
        return currentPoint;
    }

    public void addPoint(int point) {
        this.currentPoint += point;
    }

    public void substractPoint(int point) {
        this.currentPoint -= point;
    }

    public int getDefaultPoint() {
        return defaultPoint;
    }
}
