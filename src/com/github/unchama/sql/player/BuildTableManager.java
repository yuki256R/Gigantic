package com.github.unchama.sql.player;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildTableManager extends PlayerFromSeichiTableManager{
    public BuildTableManager(Sql sql) {
        super(sql);
    }

    @Override
    protected String addColumnCommand() {
        String command = "";
        command += "add column if not exists totalbuildnum double default 0,";
        return command;
    }

    @Override
    public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
        BuildManager m = gp.getManager(BuildManager.class);
        m.setTotalbuildnum(new BigDecimal(rs.getDouble("totalbuildnum")));
    }

    @Override
    protected String saveCommand(GiganticPlayer gp) {
        BuildManager m = gp.getManager(BuildManager.class);
        String command = "";
        command += "totalbuildnum = '" + m.getTotalbuildnum() + "',";
        return command;
    }

    @Override
    protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
        BuildManager m = gp.getManager(BuildManager.class);
        m.setTotalbuildnum(new BigDecimal(tm.getTotalBuildNum(gp)));
    }

    @Override
    protected void firstjoinPlayer(GiganticPlayer gp) {
        BuildManager m = gp.getManager(BuildManager.class);
        m.setTotalbuildnum(BigDecimal.ZERO);
    }
}
