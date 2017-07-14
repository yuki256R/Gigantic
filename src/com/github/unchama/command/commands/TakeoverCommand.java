package com.github.unchama.command.commands;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.donate.DonateData;
import com.github.unchama.player.donate.DonateDataManager;
import com.github.unchama.seichi.sql.SeichiDonateTableManager;
import com.github.unchama.sql.donate.DonateTableManager;
import com.google.common.collect.Multimap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mon_chi on 2017/06/16.
 */
public class TakeoverCommand implements TabExecutor {

    private DonateTableManager tableManager;

    public TakeoverCommand() {
        this.tableManager = Gigantic.sql.getManager(DonateTableManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1)
            return false;

        if (args[0].equalsIgnoreCase("donate")) {
            if (Gigantic.seichisql == null) {
                sender.sendMessage(ChatColor.RED + "SeichiAssistのSQLにアクセスできません。olddatabaseフラグを確認してください。");
                return true;
            }
            Bukkit.getServer().getScheduler().runTaskAsynchronously(Gigantic.plugin, () -> {
                sender.sendMessage(ChatColor.GREEN + "寄付データの引継ぎ中です...");
                SeichiDonateTableManager seichiManager = Gigantic.seichisql.getManager(SeichiDonateTableManager.class);
                Multimap<String, DonateData> map = seichiManager.getAllDonateData();
                map.asMap().forEach((uuid, listData) -> {
                    Player player = Bukkit.getPlayer(UUID.fromString(uuid));
                    if (player != null) {
                        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
                        if (gp != null)
                            put(gp, listData);
                        else
                            putToSQL(uuid, listData);
                    }
                    else {
                        putToSQL(uuid, listData);
                    }
                });
                sender.sendMessage(ChatColor.GREEN + "全ての寄付データの引継ぎが完了しました!");
            });
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    private void put(GiganticPlayer gp, Collection<DonateData> listData) {
        DonateDataManager manager = gp.getManager(DonateDataManager.class);
        for (DonateData data : listData) {
            manager.putDonateData(data);
        }
    }

    private void putToSQL(String uuid, Collection<DonateData> listData) {
        for (DonateData data : listData) {
            tableManager.saveDonateData(uuid, data);
        }
    }
}
