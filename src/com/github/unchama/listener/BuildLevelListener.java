package com.github.unchama.listener;

import com.github.unchama.event.BuildLevelUpEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by karayuu on 2017/05/29.
 */
public class BuildLevelListener implements Listener {
    private Gigantic plugin = Gigantic.plugin;

    /**
     * レベルアップ時のメッセージ表示
     */
    @EventHandler
    public void sendMessage (BuildLevelUpEvent event) {
        GiganticPlayer gp = event.getGiganticPlayer();
        int level = event.getLevel();
        Player player = plugin.getServer().getPlayer(gp.uuid);

        player.sendMessage(ChatColor.RED + "レベルアップ(建築レベル) Lv." + (level - 1) + " → Lv." + level);
    }
}
