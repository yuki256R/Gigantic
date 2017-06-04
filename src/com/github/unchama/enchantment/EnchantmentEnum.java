package com.github.unchama.enchantment;

import com.github.unchama.enchantment.enchantments.TestEnchantment;
import io.netty.util.internal.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Mon_chi on 2017/06/04.
 */
public enum EnchantmentEnum {

    TEST("Test", new TestEnchantment()),
    ;

    private static Map<String, GiganticEnchantment> enchantmentMap = new HashMap<>();
    private String name;
    private GiganticEnchantment enchantment;

    EnchantmentEnum(String name, GiganticEnchantment enchantment) {
        this.name = name;
        this.enchantment = enchantment;
    }

    public String getName() {
        return name;
    }

    public GiganticEnchantment getEnchantment() {
        return enchantment;
    }

    public static void register(String name, GiganticEnchantment enchantment){
        enchantmentMap.put(name, enchantment);
    }

    public static void registerAll() {
        for (EnchantmentEnum element : EnchantmentEnum.values()) {
            register(element.getName(), element.getEnchantment());
        }
    }

    public static Optional<GiganticEnchantment> getEnchantmentByDisplayName(String name) {
        return Optional.ofNullable(enchantmentMap.get(name));
    }

    public static EnchantmentEnum getEnchantmentByName(String name) {
        return EnchantmentEnum.valueOf(name);
    }
}
