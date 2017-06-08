package com.github.unchama.sql.donate;

import com.github.unchama.donate.DonateData;
import com.github.unchama.donate.DonateDataManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.TableManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Mon_chi on 2017/06/07.
 */
public class DonateTableManager extends TableManager{

    public DonateTableManager(Sql sql) {
        super(sql);
    }

    @Override
    protected Boolean createTable() {
        String command;
        //テーブル作成
        command = "CREATE TABLE IF NOT EXISTS " + db + "." + table;
        //IDカラム(ユニーク)
        command += "(id int auto_increment unique)";
        //送信
        if (!sendCommand(command)) {
            plugin.getLogger().warning("Failed to Create " + table + " Table");
            return false;
        }

        //カラム追加
        command = "alter table " + db + "." + table + " ";
        //UUIDカラム
        command += "add column if not exists uuid varchar(128),";
        //日時カラム
        command += "add column if not exists time timestamp,";
        //金額カラム
        command += "add column if not exists money int unsigned default 0,";
        //ポイントカラム
        command += "add column if not exists point int unsigned default 0,";
        //インデックスを張る
        command += "add index if not exists uuid_index(uuid)";
        //送信
        if (!sendCommand(command)) {
            plugin.getLogger().warning(
                    "Failed to add Column in " + table + " Table");
            return false;
        }
        return true;
    }

    public void multiload(HashMap<UUID, GiganticPlayer> tmpmap) {
        if (tmpmap.isEmpty()) {
            return;
        }

        this.checkStatement();

        String command = "select * from " + db + "." + table + " where uuid in (";
        for (UUID uuid : tmpmap.keySet()) {
            command += "'" + uuid.toString().toLowerCase() + "',";
        }
        command = command.substring(0, command.length() - 1);
        command += ")";

        try {
            rs = stmt.executeQuery(command);
            while (rs.next()) {
                // uuidを取得
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                DonateDataManager manager = tmpmap.get(uuid).getManager(DonateDataManager.class);
               //データクラス
                DonateData data = new DonateData(rs.getInt("money"), rs.getInt("point"));
               //データを登録
               manager.loadDonateData(rs.getTimestamp("time").toLocalDateTime(), data);
            }
            rs.close();
        } catch (SQLException e) {
            plugin.getLogger().warning(
                    "Failed to multiload in " + table + " Table");
            e.printStackTrace();
        }
    }

    public void saveDonateData(String uuid, DonateData data) {
        String command = "insert into " + db + "." + table + " (uuid, money, point) VALUES ('" + uuid + "', " + data.money + ", " + data.point + ")";

        try {
            stmt.executeUpdate(command);
        } catch (SQLException e) {
            plugin.getLogger().warning(
                    "Failed to update " + table + " Donate Data! User: " + uuid + ", Money: " + data.money + ", Point: " + data.point);
            e.printStackTrace();
        }
    }
}
