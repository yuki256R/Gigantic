package com.github.unchama.util;

import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by karayuu on 2017/05/30.
 */
public class ManaPotion {

    /**
     * ガチャりんごマナ回復効果の一覧
     */
    public enum ManaEffect {
        /** マナ完全回復 */
        MANA_FULL(1, "全回復"),
        /** マナ回復(小) */
        MANA_SMALL(2, "300回復"),
        /** マナ回復(中) */
        MANA_MEDIUM(3, "1500回復"),
        /** マナ回復(大) */
        MANA_LARGE(4, "10000回復"),
        /** マナ回復(極) */
        MANA_TINY(5, "100000回復"),
        ;

        /** 識別番号 */
        private int id;
        /** 説明 */
        private String description;

        /** コンストラクタ */
        ManaEffect (int id, String description) {
            this.id = id;
            this.description = description;
        }

        /**
         * 識別番号を返します
         * @return
         */
        private int getId() {
            return this.id;
        }

        /**
         * 説明を返します
         * @return
         */
        private String getDescription() {
            return this.description;
        }

    }

    /**
     * アイテムにManaPotion NBTが含まれるか
     * @param
     * @return 成否
     */
    public static boolean GachaAppleNBTContains(ItemStack itemstack, ManaEffect manaeffect) {
        NBTItem nbti = new NBTItem(itemstack);
        int id = nbti.getInteger("ManaPotion");

        if (id == manaeffect.getId()) return true;

        return false;
    }

    /**
     * DEBUG用ガチャりんごを返します
     * @return itemstack
     */
    public static ItemStack getDebugGachaApple(ManaEffect type, int amount) {
        ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, amount);
        ItemMeta applemeta = apple.getItemMeta();
        applemeta.setDisplayName(ChatColor.AQUA + "ガチャりんご (マナ" + type.getDescription()+ ")");
        applemeta.setLore(Arrays.asList("デバッグ専用"));
        apple.setItemMeta(applemeta);

        NBTItem applenbt = new NBTItem(apple);
        applenbt.setInteger("ManaPotion", type.getId());

        return applenbt.getItem();
    }

}
