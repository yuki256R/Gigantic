package com.github.unchama.sql.vote;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Mon_chi on 2017/06/18.
 */
public class UnchamaPointTableManager extends PlayerFromSeichiTableManager {

    public UnchamaPointTableManager(Sql sql) {
        super(sql);
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

    }

    @Override
    protected String saveCommand(GiganticPlayer gp) {
        return "";
    }

    @Override
    protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
        this.checkStatement();
        int point = tm.getVoteEffect(gp);
        String command = "update " + db + "." + table + " set point = '" + point
                + "'";
        command += " where uuid = '" + gp.uuid.toString() + "'";

        try {
            stmt.executeUpdate(command);
        } catch (SQLException e) {
            plugin.getLogger().warning(
                    "Failed to takeover " + table + " UnchamaPoint of :" + gp.name);
            e.printStackTrace();
        }

    }

    @Override
    protected void firstjoinPlayer(GiganticPlayer gp) {

    }
}
