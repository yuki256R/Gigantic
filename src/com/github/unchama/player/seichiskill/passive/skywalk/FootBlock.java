package com.github.unchama.player.seichiskill.passive.skywalk;

import com.github.unchama.player.seichiskill.moduler.Coordinate;
import org.bukkit.Location;
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

    public void generateBlock(Material material) {
        //ワールド取得
        World world = player.getWorld();
        //開始ブロックを取得(プレイヤーの1マス下)
        int x = this.coordinate.getX();
        int y = this.coordinate.getY();
        int z = this.coordinate.getZ();

        //プレイヤーの向いてる方角検知用
        Location loc = player.getLocation();
        float yaw = loc.getYaw();

        //180度で北。360度で南。90度で西。270度で東。
        Direction direction = null;

        if (135 <= yaw && yaw < 225) {
            direction = Direction.NORTH;
        } else if (225 <= yaw && yaw < 360) {
            direction = Direction.EAST;
        } else if (360 <= yaw && yaw < 45) {
            direction = Direction.SOUTH;
        } else if (45 <= yaw && yaw < 135) {
            direction = Direction.WEST;
        }
        this.generate(world, direction, x, y, z, material);
    }

    private void generate(World world, Direction direction, int x, int y, int z, Material material) {

        //落下防止のため、始めにプレイヤーの下にブロックを置く
        world.getBlockAt(x, y, z).setType(material);

        switch (direction) {
            case NORTH:
                //北向き
                for (int i = 0; i <= this.half_width; i++) {
                    for (int j = 0; j <= this.length + 1; j++) {
                        if (world.getBlockAt(x - i, y, z + j).getType().equals(Material.AIR)) {
                            world.getBlockAt(x - i, y, z + j).setType(material);
                        }
                    }
                }
            default:
                return;
        }
    }

    public void remove() {
        this.generateBlock(Material.AIR);
    }

    public enum Direction {
        SOUTH, NORTH, EAST, WEST
    }
}
