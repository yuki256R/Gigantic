package com.github.unchama.sql.point;

import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.point.GiganticPointManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;

/**
 * Created by Mon_chi on 2017/06/22.
 */
public class GiganticPointTableManager extends PointTableManager {

    public GiganticPointTableManager(Sql sql) {
        super(sql, GiganticPointManager.class);
    }

    @Override
    protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
        this.checkStatement();
        int point = tm.getPremiumPoint(gp);
        String command = "update " + db + "." + table + " set point = " + point;
        command += " where uuid = '" + gp.uuid.toString() + "'";

        try {
            stmt.executeUpdate(command);
        } catch (SQLException e) {
            plugin.getLogger().warning(
                    "Failed to takeover " + table + " Point of :" + gp.name);
            e.printStackTrace();
        }
    }
}
