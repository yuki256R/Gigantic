package com.github.unchama.sql.point;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.point.PointManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

/**
 * Created by Mon_chi on 2017/06/22.
 */
public abstract class PointTableManager extends PlayerFromSeichiTableManager {

    private Class<? extends PointManager> clazz;

    public PointTableManager(Sql sql, Class<? extends PointManager> clazz) {
        super(sql);
        this.clazz = clazz;
    }

    @Override
    protected String addColumnCommand() {
        String command = "";
        // point add
        command += "add column if not exists point int default 0,";
        return command;
    }

    @Override
    public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
        PointManager manager =  gp.getManager(clazz);
        manager.init(rs.getInt("point"));
    }

    @Override
    protected String saveCommand(GiganticPlayer gp,boolean loginflag) {
        return "";
    }

    @Override
    protected void firstjoinPlayer(GiganticPlayer gp) {
        PointManager manager =  gp.getManager(clazz);
        manager.init(0);
    }

    @Override
    public Boolean save(GiganticPlayer gp, boolean loginflag) {
        PointManager manager = gp.getManager(clazz);
        String command = "select * from " + db + "." + table + " where uuid = '" + gp.uuid + "'";
        try {
            ResultSet rs = stmt.executeQuery(command);
            rs.next();
            int difference = rs.getInt("point") - manager.getDefaultPoint();
            int currentPoint = manager.getPoint() + difference;
            rs.updateInt("point", manager.getPoint() + difference);
            rs.updateBoolean("loginflag", loginflag);
            rs.updateRow();
            if (loginflag)
                manager.init(currentPoint);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void addPointToSQL(String uuid, int addPoint) {
        this.checkStatement();
        String command = "select * from " + db + "." + table + " where uuid = '" + uuid + "'";
        int currentPoint;
        try {
            ResultSet rs = stmt.executeQuery(command);
            if (rs.isLast()) {
                return;
            }
            rs.next();
            currentPoint = rs.getInt("point");
            rs.updateInt("point", currentPoint + addPoint);
            rs.updateRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
