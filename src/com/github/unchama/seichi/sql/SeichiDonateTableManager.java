package com.github.unchama.seichi.sql;

import com.github.unchama.donate.DonateData;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mon_chi on 2017/06/15.
 */
public class SeichiDonateTableManager extends SeichiTableManager {

    public SeichiDonateTableManager(SeichiAssistSql sql) {
        super(sql);
    }

    public Multimap<String, DonateData> getAllDonateData() {
        Multimap<String, DonateData> map = ArrayListMultimap.create();
        String command = "select playeruuid, getpoint, date from " + db + "." + table;
        this.checkStatement();

        try {
            rs = stmt.executeQuery(command);
            while (rs.next()) {
                int point = rs.getInt("getpoint");
                if (point <= 0)
                    continue;

                String uuid = rs.getString("playeruuid");
                int money = point * 100;
                LocalDateTime time = rs.getTimestamp("date").toLocalDateTime();
                map.put(uuid, new DonateData(time, money, point));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;

    }
}
