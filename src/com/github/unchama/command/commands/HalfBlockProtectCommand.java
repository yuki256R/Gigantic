package com.github.unchama.command.commands;


import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.protect.HalfBlockProtectData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Y5ハーフブロック破壊可能フラグ変更コマンド
 * 権限を有する人のみ使用可能
 *
 * @author karayuu
 */
public class HalfBlockProtectCommand implements TabExecutor {

    @Override
    public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //プレイヤーからの送信ではない時終了
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED + "このコマンドはゲーム内から実行してください.");
            return false;
        }

        if (strings.length == 0) {
            // /halfguard 実行時
            GiganticPlayer gp = PlayerManager.getGiganticPlayer((Player) commandSender);
            if (gp == null) {
                throw new NullPointerException("/HalfGuardコマンドData取得処理でnull.開発者に報告してください.");
            }
            HalfBlockProtectData data= gp.getManager(HalfBlockProtectData.class);

            data.toggleHalfBreakFlag();
            commandSender.sendMessage("現在ハーフブロックは" + getStats(data.canBreakHalfBlock()) + ChatColor.RESET  + "です.");
            return true;
        }
        return false;
    }

    private String getStats (Boolean canBreak) {
        if (canBreak) {
            return ChatColor.GREEN + "破壊可能";
        } else {
            return ChatColor.RED + "破壊不可能";
        }
    }
}
