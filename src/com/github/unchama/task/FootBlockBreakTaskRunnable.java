package com.github.unchama.task;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.seichiskill.passive.skywalk.FootBlock;
import com.github.unchama.player.seichiskill.passive.skywalk.SkyWalkData;
import com.github.unchama.player.seichiskill.passive.skywalk.SkyWalkManager;
import com.github.unchama.player.seichiskill.passive.skywalk.SkyWalkUtil;
import com.github.unchama.yml.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author karayuu
 */
public class FootBlockBreakTaskRunnable extends BukkitRunnable {

    Gigantic plugin = Gigantic.plugin;
    ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
    private List<Player> playerlist;
    private int size;
    private int count;

    public FootBlockBreakTaskRunnable(List<Player> playerlist) {
        this.playerlist = new ArrayList<>(playerlist);
        this.size = this.playerlist.size();
        this.count = -1;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                count++;
                if (count >= size || playerlist.isEmpty()) {
                    cancel();
                    return;
                } else {
                    Player player = Bukkit.getServer().getPlayer(playerlist.get(count).getUniqueId());
                    if (player != null) {
                        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
                        GiganticStatus gs = PlayerManager.getStatus(gp);
                        if (gs.equals(GiganticStatus.AVAILABLE)) {
                            SkyWalkData data = gp.getManager(SkyWalkData.class);
                            List<FootBlock> breaklist = data.getFootplacelist();
                            for (FootBlock breakblock : breaklist) {
                                breakblock.remove();
                            }
                        }
                    }
                }
            }
        });
    }
}
