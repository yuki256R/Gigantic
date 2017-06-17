package com.github.unchama.player.seichiskill.passive.skywalk;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Finalizable;
import org.bukkit.Material;
import org.bukkit.block.Block;


import java.util.ArrayList;
import java.util.List;

/**
 * @author karayuu
 */
public class SkyWalkData extends DataManager implements Finalizable{
    public SkyWalkData(GiganticPlayer gp) {
        super(gp);
        this.footplacelist = new ArrayList<>();
    }
    private List<FootBlock> footplacelist;

    public void add(FootBlock footblock) {
        footplacelist.add(footblock);
    }

    public List<FootBlock> getFootplacelist() {
        return footplacelist;
    }

    public void clearFootplacelist() {
        this.footplacelist = new ArrayList<>();
    }
    @Override
    public void fin() {
        SkyWalkUtil.changeBlock(this.footplacelist, Material.AIR, PlayerManager.getPlayer(gp));
        this.clearFootplacelist();
    }
}
