package com.github.unchama.enchantment;

import com.github.unchama.enchantment.enchantments.HahuuEnchantment;
import com.github.unchama.enchantment.enchantments.TestEnchantment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Mon_chi on 2017/06/04.
 */
public enum EnchantmentEnum {

    TEST("てすとのえんちゃんと", new TestEnchantment()),
    HAHUU("覇風", new HahuuEnchantment(), 3),
    ;

    private static Map<String, EnchantmentEnum> enchantmentMap = new HashMap<>();
    //アイテムのLoreに表示される名前
    private String name;
    //GiganticEnchantmentを継承したやつ
    private GiganticEnchantment enchantment;
    //クールダウン秒数
    private int coolDown;

    EnchantmentEnum(String name, GiganticEnchantment enchantment) {
        this(name, enchantment, 0);
    }

    EnchantmentEnum(String name, GiganticEnchantment enchantment, int coolDown) {
        this.name = name;
        this.enchantment = enchantment;
        this.coolDown = coolDown;
    }

    public String getName() {
        return name;
    }

    public GiganticEnchantment getEnchantment() {
        return enchantment;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public static void register(String name, EnchantmentEnum enchantment){
        enchantmentMap.put(name, enchantment);
    }

    public static void registerAll() {
        for (EnchantmentEnum element : EnchantmentEnum.values()) {
            register(element.getName(), element);
        }
    }


    /**
     * Loreの表示名からGiganticEnchantmentを取得
     * @param name 表示名
     * @return GiganticEnchantment(Nullable)
     */
    public static Optional<EnchantmentEnum> getEnchantmentByDisplayName(String name) {
        return Optional.ofNullable(enchantmentMap.get(name));
    }

    /**
     * Enumの名前からEnchantmentEnumを取得
     * @param name Enumの名前
     * @return EnchantmentEnum(Nullable)
     */
    public static Optional<EnchantmentEnum> getEnchantmentByName(String name) {
        for (EnchantmentEnum element : EnchantmentEnum.values()) {
            if (element.name().equalsIgnoreCase(name))
                return Optional.of(element);
        }
        return Optional.empty();
    }

    /**
     * 存在するEnchantmentを取得
     * @return Enumで定義されたEnchantmentの名前のリスト
     */
    public static String[] getNameList() {
        EnchantmentEnum[] values = EnchantmentEnum.values();
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i].name();
        }
        return result;
    }
}
