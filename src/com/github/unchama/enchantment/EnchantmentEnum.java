package com.github.unchama.enchantment;

import com.github.unchama.enchantment.enchantments.*;
import com.github.unchama.util.MaterialUtil;
import org.bukkit.Material;

import java.util.*;

/**
 * Created by Mon_chi on 2017/06/04.
 */
public enum EnchantmentEnum {

    //コメントアウトは保留案件

    //II
    CHOBA("跳馬", new ChobaEnchantment(), MaterialUtil.getHelmets()),
    TOSSHIN("突進", new TosshinEnchantment(), MaterialUtil.getHelmets()),
    FUROFUSHI("不老不死", new FuroFushiEnchantment(), MaterialUtil.getLeggings()),
    KOTUEI("骨影", new KotueiEnchantment(), MaterialUtil.getPickaxes()),
    YUWAKU("誘惑", new YuwakuEnchantment(), MaterialUtil.getSwords()),
    HANGEKI("反撃", new HangekiEnchantment(), Material.BOW),
    ITAZURA("悪戯", new ItazuraEnchantment(), MaterialUtil.getAxes()),
    //NIDANJUMP("二段ジャンプ", new NidanJumpEnchantment(), MaterialUtil.getBoots()),
    SYUNPO("瞬歩", new SyunpoEnchantment(), MaterialUtil.getShovels()),
    ANTIKNOWKBACK("ノックバック耐性", new AntiKnockbackEnchantment(), MaterialUtil.getBoots()),

    //III
    HAHUU("覇風", new HahuuEnchantment(), MaterialUtil.getPickaxes(), 1),
    //DENSEN("伝染", new DensenEnchantment(), MaterialUtil.getAxes()),
    HYOKA("氷華", new HyokaEnchantment(), MaterialUtil.getShovels(), 1),
    WAIKYOKU("歪曲", new WaikyokuEnchantment(), MaterialUtil.getSwords()),
    //WALLJUMP("壁ジャンプ", new WallJumpEnchantment()),
    DOTON("土遁", new DotonEnchantment(), MaterialUtil.getHelmets()),
    RAITEI("雷霆", new RaiteiEnchantment(), MaterialUtil.getLeggings()),
    AMATERASU("天照", new AmaterasuEnchant(), Material.ELYTRA),
    HAKUGEI("白鯨", new HakugeiEnchantment(), Material.SHIELD)

    ;

    private static Map<String, EnchantmentEnum> enchantmentMap = new HashMap<>();
    //アイテムのLoreに表示される名前
    private String name;
    //GiganticEnchantmentを継承したやつ
    private GiganticEnchantment enchantment;
    //実行するアイテムのMaterial
    private List<Material> itemTypes;
    //クールダウン秒数
    private int coolDown;

    EnchantmentEnum(String name, GiganticEnchantment enchantment, Material itemType) {
        this(name, enchantment, new Material[]{itemType}, 0);
    }

    EnchantmentEnum(String name, GiganticEnchantment enchantment, Material[] itemTypes) {
        this(name, enchantment, itemTypes, 0);
    }

    EnchantmentEnum(String name, GiganticEnchantment enchantment, Material[] itemTypes, int coolDown) {
        this.name = name;
        this.enchantment = enchantment;
        this.itemTypes = Arrays.asList(itemTypes);
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

    public boolean isAllowedItem(Material type) {
        return itemTypes.contains(type);
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
