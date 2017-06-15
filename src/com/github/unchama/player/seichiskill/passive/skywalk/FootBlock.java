package com.github.unchama.player.seichiskill.passive.skywalk;

import com.github.unchama.player.seichiskill.moduler.Coordinate;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * @author karayuu
 */
public class FootBlock {

    private Player player;
    private Coordinate coordinate;
    private int width;
    private int length;
    private int half_width;
    private Material material;

    FootBlock(Player player, Coordinate under_coordinate, int width, int length, Material material){
        /**
        * コンストラクタ。
        * widthは必ず奇数にすること。
        */

        if (length % 2 != 0) {
            throw new IllegalArgumentException("FootBlockの幅は奇数にしてください");
        }
        this.player = player;
        this.coordinate = new Coordinate(under_coordinate);
        this.width = width;
        this.length = length;
        this.half_width = (length - 1) / 2;
        this.material = material;
    }

    public Player getPlayer() {
        return player;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getHalf_width() {
        return half_width;
    }

    public Material getMaterial() {
        return material;
    }

    public void generateBlock() {
        //ワールド取得
        World world = player.getWorld();
        //開始ブロックを取得(プレイヤーの1マス下)
        int x = this.coordinate.getX();
        int y = this.coordinate.getY();
        int z = this.coordinate.getZ();

        if ()
    }

    private void generate(Direction direction) {
        switch (direction) {
            
        }
    }

    public enum Direction {
        SOUTH, NORTH, EAST, WEST
    }
}
