package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * @author karayuu
 */
public class FootBlockPlaceEvent extends CustomEvent {
    private Player player;
    private Block block;

    public FootBlockPlaceEvent(Player player, Block block) {
        this.player = player;
        this.block = block;

    }

    /**
     * プレイヤーを取得
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * ブロックを取得
     * @return block
     */
    public Block getBlock() {
        return block;
    }

}
