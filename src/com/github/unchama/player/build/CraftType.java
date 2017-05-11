package com.github.unchama.player.build;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.minestack.StackType;

public enum CraftType {
	/**
     * MineStack一括クラフトシステムのクラフトメニューで使用する列挙型
	 * 新規でクラフトメニュー項目を追加したいならば、下に追加すること。
	 * ※スロット番号に45,53を指定しないこと。メニュー移動skullが使用しているため。
     * ※精錬タイプについて,未指定の場合NONEが指定されます。
	 * @author karayuu
	 */
	STEP1(1,0,Material.STEP,(short)0,1,StackType.STONE,10,"石",StackType.STEP,20,"石ハーフブロック",1),
	STEP2(1,1,Material.STEP,(short)0,2,StackType.STONE,100,"石",StackType.STEP,200,"石ハーフブロック",1),
	STEP3(1,2,Material.STEP,(short)0,3,StackType.STONE,1000,"石",StackType.STEP,2000,"石ハーフブロック",1),
	STEP4(1,3,Material.STEP,(short)0,4,StackType.STONE,10000,"石",StackType.STEP,20000,"石ハーフブロック",1),
	STEP5(1,4,Material.STEP,(short)0,5,StackType.STONE,100000,"石",StackType.STEP,200000,"石ハーフブロック",1),
	STONEBRICK1(1,9,Material.SMOOTH_BRICK,(short)0,1,StackType.STONE,10,"石",StackType.SMOOTH_BRICK,10,"石レンガ",1),
	STONEBRICK2(1,10,Material.SMOOTH_BRICK,(short)0,2,StackType.STONE,100,"石",StackType.SMOOTH_BRICK,100,"石レンガ",1),
	STONEBRICK3(1,11,Material.SMOOTH_BRICK,(short)0,3,StackType.STONE,1000,"石",StackType.SMOOTH_BRICK,1000,"石レンガ",1),
	STONEBRICK4(1,12,Material.SMOOTH_BRICK,(short)0,4,StackType.STONE,10000,"石",StackType.SMOOTH_BRICK,10000,"石レンガ",1),
	STONEBRICK5(1,13,Material.SMOOTH_BRICK,(short)0,5,StackType.STONE,100000,"石",StackType.SMOOTH_BRICK,100000,"石レンガ",1),
	GRANITE1(1,18,Material.STONE,(short)2,1,StackType.GRANITE,10,"花崗岩",StackType.POLISHED_GRANITE,10,"磨かれた花崗岩",2),
	GRANITE2(1,19,Material.STONE,(short)2,2,StackType.GRANITE,100,"花崗岩",StackType.POLISHED_GRANITE,100,"磨かれた花崗岩",2),
	GRANITE3(1,20,Material.STONE,(short)2,3,StackType.GRANITE,1000,"花崗岩",StackType.POLISHED_GRANITE,1000,"磨かれた花崗岩",2),
	GRANITE4(1,21,Material.STONE,(short)2,4,StackType.GRANITE,10000,"花崗岩",StackType.POLISHED_GRANITE,10000,"磨かれた花崗岩",2),
	DIORITE1(1,23,Material.STONE,(short)4,1,StackType.DIORITE,10,"閃緑岩",StackType.POLISHED_DIORITE,10,"磨かれた閃緑岩",2),
	DIORITE2(1,24,Material.STONE,(short)4,2,StackType.DIORITE,100,"閃緑岩",StackType.POLISHED_DIORITE,100,"磨かれた閃緑岩",2),
	DIORITE3(1,25,Material.STONE,(short)4,3,StackType.DIORITE,1000,"閃緑岩",StackType.POLISHED_DIORITE,1000,"磨かれた閃緑岩",2),
	DIORITE4(1,26,Material.STONE,(short)4,4,StackType.DIORITE,10000,"閃緑岩",StackType.POLISHED_DIORITE,10000,"磨かれた閃緑岩",2),
	ANDESITE1(1,27,Material.STONE,(short)6,1,StackType.ANDESITE,10,"安山岩",StackType.POLISHED_ANDESITE,10,"磨かれた安山岩",2),
	ANDESITE2(1,28,Material.STONE,(short)6,2,StackType.ANDESITE,100,"安山岩",StackType.POLISHED_ANDESITE,100,"磨かれた安山岩",2),
	ANDESITE3(1,29,Material.STONE,(short)6,3,StackType.ANDESITE,1000,"安山岩",StackType.POLISHED_ANDESITE,1000,"磨かれた安山岩",2),
	ANDESITE4(1,30,Material.STONE,(short)6,4,StackType.ANDESITE,10000,"安山岩",StackType.POLISHED_ANDESITE,10000,"磨かれた安山岩",2),
	NETHER_QUARTZ_BLOCK1(1,32,Material.QUARTZ_BLOCK,(short)0,1,StackType.QUARTZ,40,"ネザー水晶",StackType.QUARTZ_BLOCK,10,"ネザー水晶ブロック",2),
	NETHER_QUARTZ_BLOCK2(1,33,Material.QUARTZ_BLOCK,(short)0,2,StackType.QUARTZ,400,"ネザー水晶",StackType.QUARTZ_BLOCK,100,"ネザー水晶ブロック",2),
	NETHER_QUARTZ_BLOCK3(1,34,Material.QUARTZ_BLOCK,(short)0,3,StackType.QUARTZ,4000,"ネザー水晶",StackType.QUARTZ_BLOCK,1000,"ネザー水晶ブロック",2),
	NETHER_QUARTZ_BLOCK4(1,35,Material.QUARTZ_BLOCK,(short)0,4,StackType.QUARTZ,40000,"ネザー水晶",StackType.QUARTZ_BLOCK,10000,"ネザー水晶ブロック",2),
	BRICK_BLOCK1(1,36,Material.BRICK,(short)0,1,StackType.BRICK,40,"レンガ",StackType.BRICK_SLAB,10,"レンガブロック",2),
	BRICK_BLOCK2(1,37,Material.BRICK,(short)0,2,StackType.BRICK,400,"レンガ",StackType.BRICK_SLAB,100,"レンガブロック",2),
	BRICK_BLOCK3(1,38,Material.BRICK,(short)0,3,StackType.BRICK,4000,"レンガ",StackType.BRICK_SLAB,1000,"レンガブロック",2),
	BRICK_BLOCK4(1,39,Material.BRICK,(short)0,4,StackType.BRICK,40000,"レンガ",StackType.BRICK_SLAB,10000,"レンガブロック",2),
	NETHER_BRICK_BLOCK1(1,41,Material.NETHER_BRICK,(short)0,1,StackType.NETHER_BRICK,40,"ネザーレンガ",StackType.NETHER_BRICK_ITEM,10,"ネザーレンガブロック",2),
	NETHER_BRICK_BLOCK2(1,42,Material.NETHER_BRICK,(short)0,2,StackType.NETHER_BRICK,400,"ネザーレンガ",StackType.NETHER_BRICK_ITEM,100,"ネザーレンガブロック",2),
	NETHER_BRICK_BLOCK3(1,43,Material.NETHER_BRICK,(short)0,3,StackType.NETHER_BRICK,4000,"ネザーレンガ",StackType.NETHER_BRICK_ITEM,1000,"ネザーレンガブロック",2),
	NETHER_BRICK_BLOCK4(1,44,Material.NETHER_BRICK,(short)0,4,StackType.NETHER_BRICK,40000,"ネザーレンガ",StackType.NETHER_BRICK_ITEM,10000,"ネザーレンガブロック",2),
	
	//2ページ目
	SNOW_BLOCK1(2,0,Material.SNOW_BLOCK,(short)0,1,StackType.SNOW_BALL,40,"雪玉",StackType.SNOW_BLOCK,10,"雪(ブロック)",2),
	SNOW_BLOCK2(2,1,Material.SNOW_BLOCK,(short)0,2,StackType.SNOW_BALL,400,"雪玉",StackType.SNOW_BLOCK,100,"雪(ブロック)",2),
	SNOW_BLOCK3(2,2,Material.SNOW_BLOCK,(short)0,3,StackType.SNOW_BALL,4000,"雪玉",StackType.SNOW_BLOCK,1000,"雪(ブロック)",2),
	SNOW_BLOCK4(2,3,Material.SNOW_BLOCK,(short)0,4,StackType.SNOW_BALL,40000,"雪玉",StackType.SNOW_BLOCK,10000,"雪(ブロック)",2),
	RED_NETHER_BRICK1(2,5,Material.RED_NETHER_BRICK,(short)0,1,StackType.NETHER_STALK,20,"ネザーウォート",StackType.RED_NETHER_BRICK,20,"赤いネザーレンガ",2,FurnessType.NETHER_BRICK,20),
	RED_NETHER_BRICK2(2,6,Material.RED_NETHER_BRICK,(short)0,2,StackType.NETHER_STALK,200,"ネザーウォート",StackType.RED_NETHER_BRICK,200,"赤いネザーレンガ",2,FurnessType.NETHER_BRICK,200),
	RED_NETHER_BRICK3(2,7,Material.RED_NETHER_BRICK,(short)0,3,StackType.NETHER_STALK,2000,"ネザーウォート",StackType.RED_NETHER_BRICK,2000,"赤いネザーレンガ",2,FurnessType.NETHER_BRICK,2000),
	RED_NETHER_BRICK4(2,8,Material.RED_NETHER_BRICK,(short)0,4,StackType.NETHER_STALK,20000,"ネザーウォート",StackType.RED_NETHER_BRICK,20000,"赤いネザーレンガ",2,FurnessType.NETHER_BRICK,20000),
	IRON_INGOT1(2,9,Material.IRON_INGOT,(short)0,1,StackType.IRON_ORE,4,"鉄鉱石",StackType.IRON_INGOT,4,"鉄インゴット",3,FurnessType.COAL,1),
	IRON_INGOT2(2,10,Material.IRON_INGOT,(short)0,2,StackType.IRON_ORE,40,"鉄鉱石",StackType.IRON_INGOT,40,"鉄インゴット",3,FurnessType.COAL,10),
	IRON_INGOT3(2,11,Material.IRON_INGOT,(short)0,3,StackType.IRON_ORE,400,"鉄鉱石",StackType.IRON_INGOT,400,"鉄インゴット",3,FurnessType.COAL,100),
	IRON_INGOT4(2,12,Material.IRON_INGOT,(short)0,4,StackType.IRON_ORE,4000,"鉄鉱石",StackType.IRON_INGOT,4000,"鉄インゴット",3,FurnessType.COAL,1000),
	IRON_INGOT5(2,14,Material.IRON_INGOT,(short)0,1,StackType.IRON_ORE,50,"鉄鉱石",StackType.IRON_INGOT,50,"鉄インゴット",3,FurnessType.LAVA_BUCKET,1),
	IRON_INGOT6(2,15,Material.IRON_INGOT,(short)0,2,StackType.IRON_ORE,500,"鉄鉱石",StackType.IRON_INGOT,500,"鉄インゴット",3,FurnessType.LAVA_BUCKET,10),
	IRON_INGOT7(2,16,Material.IRON_INGOT,(short)0,3,StackType.IRON_ORE,5000,"鉄鉱石",StackType.IRON_INGOT,5000,"鉄インゴット",3,FurnessType.LAVA_BUCKET,100),
	IRON_INGOT8(2,17,Material.IRON_INGOT,(short)0,4,StackType.IRON_ORE,50000,"鉄鉱石",StackType.IRON_INGOT,50000,"鉄インゴット",3,FurnessType.LAVA_BUCKET,1000),
    GOLD_INGOT1(2,18,Material.GOLD_INGOT,(short)0,1,StackType.GOLD_ORE,4,"金鉱石",StackType.GOLD_INGOT,4,"金インゴット",3,FurnessType.COAL,1),
    GOLD_INGOT2(2,19,Material.GOLD_INGOT,(short)0,2,StackType.GOLD_ORE,40,"金鉱石",StackType.GOLD_INGOT,40,"金インゴット",3,FurnessType.COAL,10),
    GOLD_INGOT3(2,20,Material.GOLD_INGOT,(short)0,3,StackType.GOLD_ORE,400,"金鉱石",StackType.GOLD_INGOT,400,"金インゴット",3,FurnessType.COAL,100),
    GOLD_INGOT4(2,21,Material.GOLD_INGOT,(short)0,4,StackType.GOLD_ORE,4000,"金鉱石",StackType.GOLD_INGOT,4000,"金インゴット",3,FurnessType.COAL,1000),
    GOLD_INGOT5(2,23,Material.GOLD_INGOT,(short)0,1,StackType.GOLD_ORE,50,"金鉱石",StackType.GOLD_INGOT,50,"金インゴット",3,FurnessType.LAVA_BUCKET,1),
    GOLD_INGOT6(2,24,Material.GOLD_INGOT,(short)0,2,StackType.GOLD_ORE,500,"金鉱石",StackType.GOLD_INGOT,500,"金インゴット",3,FurnessType.LAVA_BUCKET,10),
    GOLD_INGOT7(2,25,Material.GOLD_INGOT,(short)0,3,StackType.GOLD_ORE,5000,"金鉱石",StackType.GOLD_INGOT,5000,"金インゴット",3,FurnessType.LAVA_BUCKET,100),
    GOLD_INGOT8(2,26,Material.GOLD_INGOT,(short)0,4,StackType.GOLD_ORE,50000,"金鉱石",StackType.GOLD_INGOT,50000,"金インゴット",3,FurnessType.LAVA_BUCKET,1000),
    GLASS_1(2,27,Material.GLASS,(short)0,1,StackType.SAND,4,"砂",StackType.GLASS,4,"ガラス",3,FurnessType.COAL,1),
    GLASS_2(2,28,Material.GLASS,(short)0,2,StackType.SAND,40,"砂",StackType.GLASS,40,"ガラス",3,FurnessType.COAL,10),
    GLASS_3(2,29,Material.GLASS,(short)0,3,StackType.SAND,400,"砂",StackType.GLASS,400,"ガラス",3,FurnessType.COAL,100),
    GLASS_4(2,30,Material.GLASS,(short)0,4,StackType.SAND,4000,"砂",StackType.GLASS,4000,"ガラス",3,FurnessType.COAL,1000),
    GLASS_5(2,32,Material.GLASS,(short)0,1,StackType.SAND,50,"砂",StackType.GLASS,50,"ガラス",3,FurnessType.LAVA_BUCKET,1),
    GLASS_6(2,33,Material.GLASS,(short)0,2,StackType.SAND,500,"砂",StackType.GLASS,500,"ガラス",3,FurnessType.LAVA_BUCKET,10),
    GLASS_7(2,34,Material.GLASS,(short)0,3,StackType.SAND,5000,"砂",StackType.GLASS,5000,"ガラス",3,FurnessType.LAVA_BUCKET,100),
    GLASS_8(2,35,Material.GLASS,(short)0,4,StackType.SAND,50000,"砂",StackType.GLASS,50000,"ガラス",3,FurnessType.LAVA_BUCKET,1000),
    NETHER_BRICK1(2,36,Material.NETHERRACK,(short)0,1,StackType.NETHERRACK,4,"ネザーラック",StackType.NETHER_BRICK_ITEM,4,"ネザーレンガ",3,FurnessType.COAL,1),
    NETHER_BRICK2(2,37,Material.NETHERRACK,(short)0,2,StackType.NETHERRACK,40,"ネザーラック",StackType.NETHER_BRICK_ITEM,40,"ネザーレンガ",3,FurnessType.COAL,10),
    NETHER_BRICK3(2,38,Material.NETHERRACK,(short)0,3,StackType.NETHERRACK,400,"ネザーラック",StackType.NETHER_BRICK_ITEM,400,"ネザーレンガ",3,FurnessType.COAL,100),
    NETHER_BRICK4(2,39,Material.NETHERRACK,(short)0,4,StackType.NETHERRACK,4000,"ネザーラック",StackType.NETHER_BRICK_ITEM,4000,"ネザーレンガ",3,FurnessType.COAL,1000),
    NETHER_BRICK5(2,41,Material.NETHERRACK,(short)0,1,StackType.NETHERRACK,50,"ネザーラック",StackType.NETHER_BRICK_ITEM,50,"ネザーレンガ",3,FurnessType.LAVA_BUCKET,1),
    NETHER_BRICK6(2,42,Material.NETHERRACK,(short)0,2,StackType.NETHERRACK,500,"ネザーラック",StackType.NETHER_BRICK_ITEM,500,"ネザーレンガ",3,FurnessType.LAVA_BUCKET,10),
    NETHER_BRICK7(2,43,Material.NETHERRACK,(short)0,3,StackType.NETHERRACK,5000,"ネザーラック",StackType.NETHER_BRICK_ITEM,5000,"ネザーレンガ",3,FurnessType.LAVA_BUCKET,100),
    NETHER_BRICK8(2,44,Material.NETHERRACK,(short)0,4,StackType.NETHERRACK,50000,"ネザーラック",StackType.NETHER_BRICK_ITEM,50000,"ネザーレンガ",3,FurnessType.LAVA_BUCKET,1000),

    //3ページ目
    BRICK1(3,0,Material.CLAY_BRICK,(short)0,1,StackType.CLAY_BALL,4,"粘土",StackType.CLAY_BRICK,4,"レンガ",3,FurnessType.COAL,1),
    BRICK2(3,1,Material.CLAY_BRICK,(short)0,2,StackType.CLAY_BALL,40,"粘土",StackType.CLAY_BRICK,40,"レンガ",3,FurnessType.COAL,10),
    BRICK3(3,2,Material.CLAY_BRICK,(short)0,3,StackType.CLAY_BALL,400,"粘土",StackType.CLAY_BRICK,400,"レンガ",3,FurnessType.COAL,100),
    BRICK4(3,3,Material.CLAY_BRICK,(short)0,4,StackType.CLAY_BALL,4000,"粘土",StackType.CLAY_BRICK,4000,"レンガ",3,FurnessType.COAL,1000),
    BRICK5(3,5,Material.CLAY_BRICK,(short)0,1,StackType.CLAY_BALL,50,"粘土",StackType.CLAY_BRICK,50,"レンガ",3,FurnessType.LAVA_BUCKET,1),
    BRICK6(3,6,Material.CLAY_BRICK,(short)0,2,StackType.CLAY_BALL,500,"粘土",StackType.CLAY_BRICK,500,"レンガ",3,FurnessType.LAVA_BUCKET,10),
    BRICK7(3,7,Material.CLAY_BRICK,(short)0,3,StackType.CLAY_BALL,5000,"粘土",StackType.CLAY_BRICK,5000,"レンガ",3,FurnessType.LAVA_BUCKET,100),
    BRICK8(3,8,Material.CLAY_BRICK,(short)0,4,StackType.CLAY_BALL,50000,"粘土",StackType.CLAY_BRICK,50000,"レンガ",3,FurnessType.LAVA_BUCKET,1000),
	;
	
	//メニュー番号
	private final int menunum;
	//スロット番号
	private final int slot;
	//メニューに表示されるブロック(Material)
	private final Material menu_icon;
	//メニューに表示されるブロックのダメージ値
	private final short durability;
	//メニューに表示されるブロックの個数
	private final int menu_icon_amount;
	//要求素材StackType(@see StackType)
	private final StackType need_stacktype;
	//要求素材個数
	private final int need_amount;
	//要求素材日本語名
	private final String need_jpname;
	//生成物StackType(@see StackType)
	private final StackType produce_stacktype;
	//生成物個数
	private final int produce_amount;
	//生成物日本語名
	private final String produce_jpname;
	//Configで指定してあるこのブロック一括クラフトを使用できる建築Lvの番号
	//ConfigManager,config.ymlを参照のこと
	private final int config_num;
	//精錬タイプ
	private final FurnessType furnessType;
	//燃料消費量
	private final int fuel;
	
	//コンストラクタ
	private CraftType(int menunum,int slot,Material menu_icon,short durability,int menu_icon_amount,
			StackType need_stacktype,int need_amount,String need_jpname,StackType produce_stacktype,
			int produce_amount,String produce_jpname,int config_num) {
		this.menunum = menunum;
		this.slot = slot;
		this.menu_icon = menu_icon;
		this.durability = durability;
		this.menu_icon_amount = menu_icon_amount;
		this.need_stacktype = need_stacktype;
		this.need_amount = need_amount;
		this.need_jpname = need_jpname;
		this.produce_stacktype = produce_stacktype;
		this.produce_amount = produce_amount;
		this.produce_jpname = produce_jpname;
		this.config_num = config_num;
		this.furnessType = FurnessType.NONE;
		this.fuel = 0;
		
	}
	
	private CraftType(int menunum,int slot,Material menu_icon,short durability,int menu_icon_amount,
			StackType need_stacktype,int need_amount,String need_jpname,StackType produce_stacktype,
			int produce_amount,String produce_jpname,int config_num,FurnessType furnessType,int fuel) {
		this.menunum = menunum;
		this.slot = slot;
		this.menu_icon = menu_icon;
		this.durability = durability;
		this.menu_icon_amount = menu_icon_amount;
		this.need_stacktype = need_stacktype;
		this.need_amount = need_amount;
		this.need_jpname = need_jpname;
		this.produce_stacktype = produce_stacktype;
		this.produce_amount = produce_amount;
		this.produce_jpname = produce_jpname;
		this.config_num = config_num;
		this.furnessType = furnessType;
		this.fuel = fuel;
	}
	
	/**
	 * メニュー番号を返します
	 * @return メニュー番号
	 */
	public int getMenunum() {
		return this.menunum;
	}
	
	/**
	 * スロット番号を返します
	 * @return スロット番号
	 */
	public int getSlot() {
		if(this.slot == 45 || this.slot == 53) {
			throw new IllegalArgumentException("CraftTypeのSlot番号に45,53は使用できません");
		} else {
			return this.slot;
		}
	}
	
	/**
	 * メニューアイコンを返します
	 * @return メニューアイコン(Material)
	 */
	public Material getMenu_icon() {
		return this.menu_icon;
	}
	
	/**
	 * メニューアイコンのダメージ値を返します
	 * @return メニューアイコンダメージ値
	 */
	public short getDurability() {
		return this.durability;
	}
	
	/**
	 * メニューアイコンのブロック個数を返します
	 * @return メニューアイコン個数
	 */
	public int getMenu_icon_amount() {
		return this.menu_icon_amount;
	}
	
	/**
	 * 必要素材のStackTypeを返します
	 * @return 必要素材StackType
	 * @see StackType
	 */
	public StackType getNeed_stacktype() {
		return this.need_stacktype;
	}
	
	/**
	 * 必要素材の個数を返します
	 * @return 必要素材個数
	 */
	public int getNeed_amount() {
		return this.need_amount;
	}
	
	/**
	 * 必要素材の日本語名を返します
	 * @return 必要素材日本語名
	 */
	public String getNeed_JPname() {
		return this.need_jpname;
	}
	
	/**
	 * 生成物のStackTypeを返します
	 * @return 生成物StackType
	 * @see StackType
	 */
	public StackType getProduce_stacktype() {
		return this.produce_stacktype;
	}
	
	/**
	 * 生成物の個数を返します
	 * @return 生成物個数
	 */
	public int getProduce_amount() {
		return this.produce_amount;
	}
	
	/**
	 * 生成物の日本語名を返します
	 * @return 生成物日本語名
	 */
	public String getProduce_JPname() {
		return this.produce_jpname;
	}
	
	/**
	 * コンフィグナンバーを返します
	 * @return コンフィグナンバー
	 */
	public int getConfig_Num() {
		return this.config_num;
	}
	
	/**
	 * メニューアイコンをアイテムスタックで返します
	 * @return メニューアイコン(ItemStack)
	 */
	public ItemStack getMenuIconItemStack() {
		ItemStack itemstack = new ItemStack(this.getMenu_icon());
		itemstack.setDurability(this.getDurability());
		return itemstack;
	}

    /**
     * 精錬タイプを返します
     * @return 精錬タイプ
     */
    public FurnessType getFurnessType() {
        return furnessType;
    }
    
    /**
     * 燃料消費量を返します
     * @return 燃料消費量
     */
    public int getFuel() {
    	return this.fuel;
    }
}


