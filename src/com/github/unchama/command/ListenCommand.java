package com.github.unchama.command;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.donate.DonateData;
import com.github.unchama.player.donate.DonateDataManager;
import com.github.unchama.sql.donate.DonateTableManager;

import com.github.unchama.sql.vote.UnchamaPointTableManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mon_chi on 2017/06/08.
 */
public class ListenCommand implements TabExecutor {

    private DonateTableManager donateTableManager;
    private UnchamaPointTableManager unchamaPointTableManager;

    public ListenCommand() {
        this.donateTableManager = Gigantic.sql.getManager(DonateTableManager.class);
        this.unchamaPointTableManager = Gigantic.sql.getManager(UnchamaPointTableManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        else if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/listen <donate/vote>");
        }
        else {
            if (args[0].equalsIgnoreCase("donate")) {
                if (args.length < 4) {
                    sender.sendMessage(ChatColor.RED + "/listen donate <uuid> <金額> <ポイント>");
                } else if (!StringUtils.isNumeric(args[2]) || !StringUtils.isNumeric(args[3])) {
                    sender.sendMessage(ChatColor.RED + "金額とポイントは数字で指定してください");
                } else {
                    Player player = Bukkit.getPlayer(UUID.fromString(args[1]));
                    int money = Integer.parseInt(args[2]);
                    int point = Integer.parseInt(args[3]);
                    if (player != null) {
                        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
                        if (gp != null)
                            put(gp, money, point);
                        else
                            putToSQL(args[1], money, point);
                    } else {
                        putToSQL(args[1], money, point);
                    }
                    sender.sendMessage("寄付データを登録しました! UUID: " + args[1] + ", 金額: " + money + ", ポイント: " + point);
                }
            } else if (args[0].equalsIgnoreCase("vote")) {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "/listen vote <name>");
                } else {
                    String uuid = Bukkit.getServer().getOfflinePlayer(args[1]).getUniqueId().toString();
                    unchamaPointTableManager.addPoint(uuid, 1);
                    sender.sendMessage("投票を受け付けました! UUID: " + args[1]);
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    private void put(GiganticPlayer gp, int money, int point) {
        DonateDataManager manager = gp.getManager(DonateDataManager.class);
        manager.putDonateData(new DonateData(LocalDateTime.now(), money, point));
    }

    private void putToSQL(String uuid, int money, int point) {
        donateTableManager.saveDonateData(uuid, new DonateData(LocalDateTime.now(), money, point));
    }
}
