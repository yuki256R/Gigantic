package com.github.unchama.util;

import org.bukkit.Material;

/**
 * @author Mon_chi
 */
public class MaterialUtil {

    public static Material[] getPickaxes() {
        return new Material[]{Material.DIAMOND_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE};
    }

    public static Material[] getAxes() {
        return new Material[]{Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOOD_AXE};
    }

    public static Material[] getShovels() {
        return new Material[]{Material.DIAMOND_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.STONE_SPADE, Material.WOOD_SPADE};
    }

    public static Material[] getSwords() {
        return new Material[]{Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD};
    }

    public static Material[] getHelmets() {
        return new Material[]{Material.DIAMOND_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET};
    }

    public static Material[] getLeggings() {
        return new Material[]{Material.DIAMOND_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS};
    }
}
