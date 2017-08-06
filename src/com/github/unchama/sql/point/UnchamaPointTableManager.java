package com.github.unchama.sql.point;

import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.point.UnchamaPointManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;

/**
 * Created by Mon_chi on 2017/06/18.
 */
public class UnchamaPointTableManager extends PointTableManager {

    public UnchamaPointTableManager(Sql sql) {
        super(sql, UnchamaPointManager.class);
    }

    @Override
    protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
        this.checkStatement();
        int point = tm.getVoteEffect(gp);
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
