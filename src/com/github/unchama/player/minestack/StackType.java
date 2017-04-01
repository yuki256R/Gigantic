package com.github.unchama.player.minestack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/*
 * 同じMaterial名でもアイテムとして種類が違えば，別々に定義すること．
 * わからない箇所は印をつけてスキップしてください．
 *
 */


public enum StackType {
//全てのアイテム列挙
	//Material名が同じなら日本語名だけを引数とする．
	STONE("石"),
	//Material名が異なっていて，耐久値（durability)も異なっていれば引数に追加
	GRANITE(Material.STONE,"花崗岩",1),
	POLISHED_GRANITE(Material.STONE,"滑らかな花崗岩",2),
	DIORITE(Material.STONE,"閃緑岩",3),
	POLISHED_DIORITE(Material.STONE,"滑らかな閃緑岩",4),
	ANDESITE(Material.STONE,"安山岩",5),
	POLISHED_ANDESITE(Material.STONE,"滑らかな安山岩",6),
	GRASS("草ブロック"),
	DIRT("土"),
	COARSE_DIRT(Material.DIRT,"粗い土",1),
	PODZOL(Material.DIRT,"ポドゾル",2),
	COBBLESTONE("丸石"),
	WOOD("オークの木材"),
	SPRUCE_WOOD_PLANK(Material.WOOD,"マツの木材",1),
	BIRCH_WOOD_PLANK(Material.WOOD,"シラカバの木材",2),
	JUNGLE_WOOD_PLANK(Material.WOOD,"ジャングルの木材",3),
	ACACIA_WOOD_PLANK(Material.WOOD,"アカシアの木材",4),
	DARK_OAK_WOOD_PLANK(Material.WOOD,"ダークオークの木材",5),
	SAPLING("オークの苗木"),
	SPRUCE_SAPLING(Material.SAPLING,"マツの苗木",1),
	BIRCH_SAPLING(Material.SAPLING,"シラカバの苗木",2),
	JUNGLE_SAPLING(Material.SAPLING,"ジャングルの苗木",3),
	ACACIA_SAPLING(Material.SAPLING,"アカシアの苗木",4),
	DARK_OAK_SAPLING(Material.SAPLING,"ダークオークの苗木",5),
	//BEDROCK("岩盤"),
	SAND("砂"),
	RED_SAND(Material.SAND,"赤い砂",1),
	GRAVEL("砂利"),
	GOLD_ORE("金鉱石"),
	IRON_ORE("鉄鉱石"),
	COAL_ORE("石炭鉱石"),
	LOG("オークの原木"),
	SPRUCE_WOOD(Material.LOG,"マツの原木",1),
	BIRCH_WOOD(Material.LOG,"シラカバの原木",2),
	JUNGLE_WOOD(Material.LOG,"ジャングルの原木",3),
	LEAVES("オークの葉"),
	SPRUCE_LEAVES(Material.LEAVES,"マツの葉",1),
	BIRCH_LEAVES(Material.LEAVES,"シラカバの葉",2),
	JUNGLE_LEAVES(Material.LEAVES,"ジャングルの葉",3),
	SPONGE("スポンジ"),
	WET_SPONGE(Material.SPONGE,"濡れたスポンジ",1),
	GLASS("ガラス"),
	LAPIS_ORE("ラピスラズリ鉱石"),
	LAPIS_BLOCK("ラピスラズリブロック"),
	DISPENSER("ディスペンサー"),
	SANDSTONE("砂岩"),
	CHISELED_SANDSTONE(Material.SANDSTONE,"模様入り砂岩",1),
	SMOOTH_SANDSTONE(Material.SANDSTONE,"滑らかな砂岩",2),
	NOTE_BLOCK("音符ブロック"),
	POWERED_RAIL("パワードレール"),
	DETECTOR_RAIL("ディテクターレール"),
	PISTON_STICKY_BASE("粘着ピストン"),
	WEB("クモの巣"),
	LONG_DEAD_GRASS(Material.LONG_GRASS,"枯れ木"),
	LONG_GRASS(Material.LONG_GRASS,"草",1),
	FERN(Material.LONG_GRASS,"シダ",2),
	DEAD_BUSH("枯れ木"),
	PISTON_BASE("ピストン"),
	WOOL("羊毛"),
	ORANGE_WOOL(Material.WOOL,"橙色の羊毛",1),
	MAGENTA_WOOL(Material.WOOL,"赤紫色の羊毛",2),
	LIGHT_BLUE_WOOL(Material.WOOL,"空色の羊毛",3),
	YELLOW_WOOL(Material.WOOL,"黄色の羊毛",4),
	LIME_WOOL(Material.WOOL,"黄緑色の羊毛",5),
	PINK_WOOL(Material.WOOL,"桃色の羊毛",6),
	GRAY_WOOL(Material.WOOL,"灰色の羊毛",7),
	LIGHT_GRAY_WOOL(Material.WOOL,"薄灰色の羊毛",8),
	CYAN_WOOL(Material.WOOL,"水色の羊毛",9),
	PURPLE_WOOL(Material.WOOL,"紫色の羊毛",10),
	BLUE_WOOL(Material.WOOL,"青色の羊毛",11),
	BROWN_WOOL(Material.WOOL,"茶色の羊毛",12),
	GREEN_WOOL(Material.WOOL,"緑色の羊毛",13),
	RED_WOOL(Material.WOOL,"赤色の羊毛",14),
	BLACK_WOOL(Material.WOOL,"黒色の羊毛",15),
	YELLOW_FLOWER("タンポポ"),
	RED_ROSE("ポピー"),
	BLUE_ORCHID(Material.RED_ROSE,"ヒスイラン",1),
	ALLIUM(Material.RED_ROSE,"アリウム",2),
	AZURE_BLUET(Material.RED_ROSE,"ヒナソウ",3),
	RED_TULIP(Material.RED_ROSE,"赤色のチューリップ",4),
	ORANGE_TULIP(Material.RED_ROSE,"橙色のチューリップ",5),
	WHITE_TULIP(Material.RED_ROSE,"白色のチューリップ",6),
	PINK_TULIP(Material.RED_ROSE,"桃色のチューリップ",7),
	OXEYE_DAISY(Material.RED_ROSE,"フランスギク",8),
	BROWN_MUSHROOM("茶色キノコ"),
	RED_MUSHROOM("赤キノコ"),
	GOLD_BLOCK("金ブロック"),
	IRON_BLOCK("鉄ブロック"),
	STEP("石ハーフブロック"),
	SANDSTONE_SLAB(Material.STEP,"砂岩ハーフブロック",1),
	//WOODEN_SLAB(Material.STEP,"オーク木材のハーフブロック",2),//???
	COBBLESTONE_SLAB(Material.STEP,"丸石ハーフブロック",3),
	BRICK_SLAB(Material.STEP,"レンガハーフブロック",4),
	STONE_SLAB(Material.STEP,"石レンガハーフブロック",5),
	NETHER_SLAB(Material.STEP,"ネザーレンガハーフブロック",6),
	QUARTZ_SLAB(Material.STEP,"ネザー水晶ハーフブロック",7),
	BRICK("レンガ"),
	TNT("TNT"),
	BOOKSHELF("本棚"),
	MOSSY_COBBLESTONE("苔石"),
	OBSIDIAN("黒曜石"),
	TORCH("松明"),
	WOOD_STAIRS("オークの木の階段"),
	CHEST("チェスト"),
	DIAMOND_ORE("ダイヤモンド鉱石"),
	DIAMOND_BLOCK("ダイヤモンドブロック"),
	WORKBENCH("作業台"),
	SOIL("耕地"),
	FURNACE("かまど"),
	//SIGN_POST("看板"),
	//WOODEN_DOOR("オークのドア"),
	LADDER("はしご"),
	RAILS("レール"),
	COBBLESTONE_STAIRS("丸石の階段"),
	//WALL_SIGN("看板"),
	LEVER("レバー"),
	STONE_PLATE("石の感圧板"),
	//IRON_DOOR_BLOCK("鉄のドア"),
	WOOD_PLATE("木の感圧板"),
	REDSTONE_ORE("レッドストーン鉱石"),
	REDSTONE_TORCH_ON("レッドストーントーチ"),
	STONE_BUTTON("ボタン"),
	SNOW("雪層"),
	ICE("氷"),
	SNOW_BLOCK("雪"),
	CACTUS("サボテン"),
	CLAY("粘土"),
	//SUGAR_CANE_BLOCK("サトウキビ"),
	JUKEBOX("ジュークボックス"),
	FENCE("オークのフェンス"),
	PUMPKIN("カボチャ"),
	NETHERRACK("ネザーラック"),
	SOUL_SAND("ソウルサンド"),
	GLOWSTONE("グロウストーン"),
	JACK_O_LANTERN("ジャック・オ・ランタン"),
	//DIODE_BLOCK_OFF("レッドストーンリピーター"),
	//DIODE_BLOCK_ON("レッドストーンリピーター"),
	STAINED_GLASS("白色の色付きガラス"),
	ORANGE_STAINED_GLASS(Material.STAINED_GLASS,"橙色の色付きガラス",1),
	MAGENTA_STAINED_GLASS(Material.STAINED_GLASS,"赤紫色の色付きガラス",2),
	LIGHT_BLUE_STAINED_GLASS(Material.STAINED_GLASS,"空色の色付きガラス",3),
	YELLOW_STAINED_GLASS(Material.STAINED_GLASS,"黄色の色付きガラス",4),
	LIME_STAINED_GLASS(Material.STAINED_GLASS,"黄緑色の色付きガラス",5),
	PINK_STAINED_GLASS(Material.STAINED_GLASS,"桃色の色付きガラス",6),
	GRAY_STAINED_GLASS(Material.STAINED_GLASS,"灰色の色付きガラス",7),
	LIGHT_GRAY_STAINED_GLASS(Material.STAINED_GLASS,"薄灰色の色付きガラス",8),
	CYAN_STAINED_GLASS(Material.STAINED_GLASS,"水色の色付きガラス",9),
	PURPLE_STAINED_GLASS(Material.STAINED_GLASS,"紫色の色付きガラス",10),
	BLUE_STAINED_GLASS(Material.STAINED_GLASS,"青色の色付きガラス",11),
	BROWN_STAINED_GLASS(Material.STAINED_GLASS,"茶色の色付きガラス",12),
	GREEN_STAINED_GLASS(Material.STAINED_GLASS,"緑色の色付きガラス",13),
	RED_STAINED_GLASS(Material.STAINED_GLASS,"赤色の色付きガラス",14),
	BLACK_STAINED_GLASS(Material.STAINED_GLASS,"黒色の色付きガラス",15),
	TRAP_DOOR("木のトラップドア"),
	MONSTER_EGGS("石"),
	SMOOTH_BRICK("石レンガ"),
	MOSSY_STONE_BRICK(Material.SMOOTH_BRICK,"苔石レンガ",1),
	CRACKED_STONE_BRICK(Material.SMOOTH_BRICK,"ひびの入った石レンガ",2),
	CHISELED_STONE_BRICK(Material.SMOOTH_BRICK,"模様入り石レンガ",3),
	HUGE_MUSHROOM_1("茶色キノコブロック"),
	HUGE_MUSHROOM_2("赤色キノコブロック"),
	IRON_FENCE("鉄格子"),
	THIN_GLASS("板ガラス"),
	MELON_BLOCK("スイカ"),
	VINE("ツタ"),
	FENCE_GATE("オークのフェンスゲート"),
	BRICK_STAIRS("レンガの階段"),
	SMOOTH_STAIRS("石レンガの階段"),
	MYCEL("菌糸"),
	WATER_LILY("スイレンの葉"),
	NETHER_BRICK("ネザーレンガ"),
	NETHER_FENCE("ネザーレンガのフェンス"),
	NETHER_BRICK_STAIRS("ネザーレンガの階段"),
	ENCHANTMENT_TABLE("エンチャントテーブル"),
	//BREWING_STAND("醸造台"),
	//CAULDRON("大釜"),
	//ENDER_PORTAL_FRAME("エンドポータル"),
	ENDER_STONE("エンドストーン"),
	DRAGON_EGG("ドラゴンの卵"),
	REDSTONE_LAMP_OFF("レッドストーンランプ"),
	WOOD_DOUBLE_STEP("オークの木材ハーフブロック"),
	//DOUBLE_SPRUCE_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,"マツの木材ハーフブロック",1),
	//DOUBLE_BIRCH_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,"シラカバの木材ハーフブロック",2),
	//DOUBLE_JUNGLE_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,"ジャングルの木材ハーフブロック",3),
	//DOUBLE_ACACIA_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,"アカシアの木材ハーフブロック",4),
	//DOUBLE_DARK_OAK_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,"ダークオークの木材ハーフブロック",5),
	WOOD_STEP("オークの木材ハーフブロック"),
	//COCOA("カカオ"),
	SANDSTONE_STAIRS("砂岩の階段"),
	EMERALD_ORE("エメラルド鉱石"),
	ENDER_CHEST("エンダーチェスト"),
	TRIPWIRE_HOOK("トリップワイヤーフック"),
	//TRIPWIRE("トリップワイヤー"),
	EMERALD_BLOCK("エメラルドブロック"),
	SPRUCE_WOOD_STAIRS("マツの木の階段"),
	BIRCH_WOOD_STAIRS("シラカバの木の階段"),
	JUNGLE_WOOD_STAIRS("ジャングルの木の階段"),
	//COMMAND("コマンドブロック"),
	BEACON("ビーコン"),
	COBBLE_WALL("丸石の壁"),
	MOSSY_COBBLESTONE_WALL(Material.COBBLE_WALL,"苔石の壁",1),
	//FLOWER_POT("植木鉢"),
	//CARROT("ニンジン"),
	//POTATO("ジャガイモ"),
	WOOD_BUTTON("ボタン"),
	//SKULL("Mobの頭"),
	ANVIL("金床"),
	ANVIL_2(Material.ANVIL,"少し壊れた金床",1),
	ANVIL_3(Material.ANVIL,"かなり壊れた金床",2),
	TRAPPED_CHEST("トラップチェスト"),
	GOLD_PLATE("重量感圧板（軽）"),
	IRON_PLATE("重量感圧板（重）"),
	//REDSTONE_COMPARATOR_OFF("レッドストーンコンパレーター"),
	//REDSTONE_COMPARATOR_ON("レッドストーンコンパレーター"),
	DAYLIGHT_DETECTOR("日照センサー"),
	REDSTONE_BLOCK("レッドストーンブロック"),
	QUARTZ_ORE("ネザー水晶鉱石"),
	HOPPER("ホッパー"),
	QUARTZ_BLOCK("ネザー水晶ブロック"),
	CHISELED_QUARTZ_BLOCK(Material.QUARTZ_BLOCK,"模様入りネザー水晶ブロック",1),
	PILLAR_QUARTZ_BLOCK(Material.QUARTZ_BLOCK,"柱状ネザー水晶ブロック",2),
	QUARTZ_STAIRS("ネザー水晶の階段"),
	ACTIVATOR_RAIL("アクティベーターレール"),
	DROPPER("ドロッパー"),
	STAINED_CLAY("白色の堅焼き粘土"),
	ORANGE_STAINED_CLAY(Material.STAINED_CLAY,"橙色の堅焼き粘土",1),
	MAGENTA_STAINED_CLAY(Material.STAINED_CLAY,"赤紫色の堅焼き粘土",2),
	LIGHT_BLUE_STAINED_CLAY(Material.STAINED_CLAY,"空色の堅焼き粘土",3),
	YELLOW_STAINED_CLAY(Material.STAINED_CLAY,"黄色の堅焼き粘土",4),
	LIME_STAINED_CLAY(Material.STAINED_CLAY,"黄緑色の堅焼き粘土",5),
	PINK_STAINED_CLAY(Material.STAINED_CLAY,"桃色の堅焼き粘土",6),
	GRAY_STAINED_CLAY(Material.STAINED_CLAY,"灰色の堅焼き粘土",7),
	LIGHT_GRAY_STAINED_CLAY(Material.STAINED_CLAY,"薄灰色の堅焼き粘土",8),
	CYAN_STAINED_CLAY(Material.STAINED_CLAY,"水色の堅焼き粘土",9),
	PURPLE_STAINED_CLAY(Material.STAINED_CLAY,"紫色の堅焼き粘土",10),
	BLUE_STAINED_CLAY(Material.STAINED_CLAY,"青色の堅焼き粘土",11),
	BROWN_STAINED_CLAY(Material.STAINED_CLAY,"茶色の堅焼き粘土",12),
	GREEN_STAINED_CLAY(Material.STAINED_CLAY,"緑色の堅焼き粘土",13),
	RED_STAINED_CLAY(Material.STAINED_CLAY,"赤色の堅焼き粘土",14),
	BLACK_STAINED_CLAY(Material.STAINED_CLAY,"黒色の堅焼き粘土",15),
	STAINED_GLASS_PANE("白色の色付きガラス板"),
	ORANGE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"橙色の色付きガラス板",1),
	MAGENTA_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"赤紫色の色付きガラス板",2),
	LIGHT_BLUE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"空色の色付きガラス板",3),
	YELLOW_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"黄色の色付きガラス板",4),
	LIME_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"黄緑色の色付きガラス板",5),
	PINK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"桃色の色付きガラス板",6),
	GRAY_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"灰色の色付きガラス板",7),
	LIGHT_GRAY_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"薄灰色の色付きガラス板",8),
	CYAN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"水色の色付きガラス板",9),
	PURPLE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"紫色の色付きガラス板",10),
	BLUE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"青色の色付きガラス板",11),
	BROWN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"茶色の色付きガラス板",12),
	GREEN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"緑色の色付きガラス板",13),
	RED_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"赤色の色付きガラス板",14),
	BLACK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,"黒色の色付きガラス板",15),
	LEAVES_2("アカシアの葉"),
	DARK_OAK_LEAVES(Material.LEAVES_2,"ダークオークの葉",1),
	LOG_2("アカシアの原木"),
	DARK_OAK_WOOD(Material.LOG_2,"ダークオークの原木",1),
	ACACIA_STAIRS("アカシアの木の階段"),
	DARK_OAK_STAIRS("ダークオークの木の階段"),
	SLIME_BLOCK("スライムブロック"),
	//BARRIER("バリアブロック"),
	IRON_TRAPDOOR("鉄のトラップドア"),
	PRISMARINE("プリズマリン"),
	PRISMARINE_BRICKS(Material.PRISMARINE,"プリズマリンレンガ",1),
	DARK_PRISMARINE(Material.PRISMARINE,"ダークプリズマリン",2),
	SEA_LANTERN("シーランタン"),
	HAY_BLOCK("干草の俵"),
	CARPET("カーペット"),
	ORANGE_CARPET(Material.CARPET,"橙色のカーペット",1),
	MAGENTA_CARPET(Material.CARPET,"赤紫色のカーペット",2),
	LIGHT_BLUE_CARPET(Material.CARPET,"空色のカーペット",3),
	YELLOW_CARPET(Material.CARPET,"黄色のカーペット",4),
	LIME_CARPET(Material.CARPET,"黄緑色のカーペット",5),
	PINK_CARPET(Material.CARPET,"桃色のカーペット",6),
	GRAY_CARPET(Material.CARPET,"灰色のカーペット",7),
	LIGHT_GRAY_CARPET(Material.CARPET,"薄灰色のカーペット",8),
	CYAN_CARPET(Material.CARPET,"水色のカーペット",9),
	PURPLE_CARPET(Material.CARPET,"紫色のカーペット",10),
	BLUE_CARPET(Material.CARPET,"青色のカーペット",11),
	BROWN_CARPET(Material.CARPET,"茶色のカーペット",12),
	GREEN_CARPET(Material.CARPET,"緑色のカーペット",13),
	RED_CARPET(Material.CARPET,"赤色のカーペット",14),
	BLACK_CARPET(Material.CARPET,"黒色のカーペット",15),
	HARD_CLAY("堅焼き粘土"),
	COAL_BLOCK("石炭ブロック"),
	PACKED_ICE("氷塊"),
	DOUBLE_PLANT("ヒマワリ"),
	LILAC(Material.DOUBLE_PLANT,"ライラック",1),
	DOUBLE_TALLGRASS(Material.DOUBLE_PLANT,"高い草",2),
	LARGE_FERN(Material.DOUBLE_PLANT,"大きなシダ",3),
	ROSE_BUSH(Material.DOUBLE_PLANT,"バラの低木",4),
	PEONY(Material.DOUBLE_PLANT,"ボタン",5),
	//STANDING_BANNER("旗"),
	//WALL_BANNER("旗"),
	//DAYLIGHT_DETECTOR_INVERTED("日照センサー"),
	RED_SANDSTONE("赤い砂岩"),
	CHISELED_RED_SANDSTONE(Material.RED_SANDSTONE,"模様入りの赤い砂岩",1),
	SMOOTH_RED_SANDSTONE(Material.RED_SANDSTONE,"滑らかな赤い砂岩",2),
	RED_SANDSTONE_STAIRS("赤い砂岩の階段"),
	//DOUBLE_STONE_SLAB2("赤い砂岩ハーフブロック"),
	STONE_SLAB2("赤い砂岩ハーフブロック"),
	SPRUCE_FENCE_GATE("マツのフェンスゲート"),
	BIRCH_FENCE_GATE("シラカバのフェンスゲート"),
	JUNGLE_FENCE_GATE("ジャングルのフェンスゲート"),
	DARK_OAK_FENCE_GATE("ダークオークのフェンスゲート"),
	ACACIA_FENCE_GATE("アカシアのフェンスゲート"),
	SPRUCE_FENCE("マツのフェンス"),
	BIRCH_FENCE("シラカバのフェンス"),
	JUNGLE_FENCE("ジャングルのフェンス"),
	DARK_OAK_FENCE("ダークオークのフェンス"),
	ACACIA_FENCE("アカシアのフェンス"),
	/*SPRUCE_DOOR("マツのドア"),
	BIRCH_DOOR("シラカバのドア"),
	JUNGLE_DOOR("ジャングルのドア"),
	ACACIA_DOOR("アカシアのドア"),
	DARK_OAK_DOOR("ダークオークのドア"),*/
	END_ROD("エンドロッド"),
	CHORUS_PLANT("コーラスプラント"),
	CHORUS_FLOWER("コーラスフラワー"),
	PURPUR_BLOCK("プルパーブロック"),
	PURPUR_PILLAR("柱状プルパーブロック"),
	PURPUR_STAIRS("プルパーの階段"),
	//PURPUR_DOUBLE_SLAB("プルパーハーフブロック"),
	PURPUR_SLAB("プルパーハーフブロック"),
	END_BRICKS("エンドストーンレンガ"),
	//BEETROOT_BLOCK("ビートルートの種"),
	//GRASS_PATH("草の道"),
	MAGMA("マグマブロック"),
	NETHER_WART_BLOCK("ネザーウォートブロック"),
	RED_NETHER_BRICK("赤いネザーレンガ"),
	BONE_BLOCK("骨ブロック"),
	IRON_SPADE("鉄のシャベル"),
	IRON_PICKAXE("鉄のツルハシ"),
	IRON_AXE("鉄の斧"),
	FLINT_AND_STEEL("火打石と打ち金"),
	APPLE("リンゴ"),
	BOW("弓"),
	ARROW("矢"),
	COAL("石炭"),
	CHARCOAL(Material.COAL,"木炭",1),
	DIAMOND("ダイヤモンド"),
	IRON_INGOT("鉄インゴット"),
	GOLD_INGOT("金インゴット"),
	IRON_SWORD("鉄の剣"),
	WOOD_SWORD("木の剣"),
	WOOD_SPADE("木のシャベル"),
	WOOD_PICKAXE("木のツルハシ"),
	WOOD_AXE("木の斧"),
	STONE_SWORD("石の剣"),
	STONE_SPADE("石のシャベル"),
	STONE_PICKAXE("石のツルハシ"),
	STONE_AXE("石の斧"),
	DIAMOND_SWORD("ダイヤの剣"),
	DIAMOND_SPADE("ダイヤのシャベル"),
	DIAMOND_PICKAXE("ダイヤのツルハシ"),
	DIAMOND_AXE("ダイヤの斧"),
	STICK("棒"),
	BOWL("ボウル"),
	MUSHROOM_SOUP("キノコシチュー"),
	GOLD_SWORD("金の剣"),
	GOLD_SPADE("金のシャベル"),
	GOLD_PICKAXE("金のツルハシ"),
	GOLD_AXE("金の斧"),
	STRING("糸"),
	FEATHER("羽"),
	SULPHUR("火薬"),
	WOOD_HOE("木のクワ"),
	STONE_HOE("石のクワ"),
	IRON_HOE("鉄のクワ"),
	DIAMOND_HOE("ダイヤのクワ"),
	GOLD_HOE("金のクワ"),
	SEEDS("種"),
	WHEAT("小麦"),
	BREAD("パン"),
	LEATHER_HELMET("革の帽子"),
	LEATHER_CHESTPLATE("革の上着"),
	LEATHER_LEGGINGS("革のズボン"),
	LEATHER_BOOTS("革のブーツ"),
	CHAINMAIL_HELMET("チェーンヘルメット"),
	CHAINMAIL_CHESTPLATE("チェーンチェストプレート"),
	CHAINMAIL_LEGGINGS("チェーンレギンス"),
	CHAINMAIL_BOOTS("チェーンブーツ"),
	IRON_HELMET("鉄のヘルメット"),
	IRON_CHESTPLATE("鉄のチェストプレート"),
	IRON_LEGGINGS("鉄のレギンス"),
	IRON_BOOTS("鉄のブーツ"),
	DIAMOND_HELMET("ダイヤのヘルメット"),
	DIAMOND_CHESTPLATE("ダイヤのチェストプレート"),
	DIAMOND_LEGGINGS("ダイヤのレギンス"),
	DIAMOND_BOOTS("ダイヤのブーツ"),
	GOLD_HELMET("金のヘルメット"),
	GOLD_CHESTPLATE("金のチェストプレート"),
	GOLD_LEGGINGS("金のレギンス"),
	GOLD_BOOTS("金のブーツ"),
	FLINT("火打石"),
	PORK("生の豚肉"),
	GRILLED_PORK("焼き豚"),
	PAINTING("絵画"),
	GOLDEN_APPLE("金のリンゴ"),
	ENCHANTED_GOLDEN_APPLE(Material.GOLDEN_APPLE,"金のリンゴ",1),
	SIGN("看板"),
	WOOD_DOOR("オークのドア"),
	BUCKET("バケツ"),
	WATER_BUCKET("水入りバケツ"),
	LAVA_BUCKET("溶岩入りバケツ"),
	MINECART("トロッコ"),
	SADDLE("サドル"),
	IRON_DOOR("鉄のドア"),
	REDSTONE("レッドストーン"),
	SNOW_BALL("雪玉"),
	BOAT("オークのボート"),
	LEATHER("革"),
	MILK_BUCKET("牛乳"),
	CLAY_BRICK("レンガ"),
	CLAY_BALL("粘土"),
	SUGAR_CANE("サトウキビ"),
	PAPER("紙"),
	BOOK("本"),
	SLIME_BALL("スライムボール"),
	STORAGE_MINECART("チェスト付きトロッコ"),
	POWERED_MINECART("かまど付きトロッコ"),
	EGG("卵"),
	COMPASS("コンパス"),
	FISHING_ROD("釣竿"),
	WATCH("時計"),
	GLOWSTONE_DUST("グロウストーンダスト"),
	RAW_FISH("生魚"),
	RAW_SALMON(Material.RAW_FISH,"生鮭",1),
	CLOWNFISH(Material.RAW_FISH,"クマノミ",2),
	PUFFERFISH(Material.RAW_FISH,"フグ",3),
	COOKED_FISH("焼き魚"),
	COOKED_SALMON(Material.COOKED_FISH,"焼き鮭",1),
	INK_SACK("イカスミ"),
	ROSE_RED(Material.INK_SACK,"赤色の染料",1),
	CACTUS_GREEN(Material.INK_SACK,"緑色の染料",2),
	COCO_BEANS(Material.INK_SACK,"カカオ豆",3),
	LAPIS_LAZULI(Material.INK_SACK,"ラピスラズリ",4),
	PURPLE_DYE(Material.INK_SACK,"紫色の染料",5),
	CYAN_DYE(Material.INK_SACK,"水色の染料",6),
	LIGHT_GRAY_DYE(Material.INK_SACK,"薄灰色の染料",7),
	GRAY_DYE(Material.INK_SACK,"灰色の染料",8),
	PINK_DYE(Material.INK_SACK,"桃色の染料",9),
	LIME_DYE(Material.INK_SACK,"黄緑色の染料",10),
	DANDELION_YELLOW(Material.INK_SACK,"黄色の染料",11),
	LIGHT_BLUE_DYE(Material.INK_SACK,"空色の染料",12),
	MAGENTA_DYE(Material.INK_SACK,"赤紫色の染料",13),
	ORANGE_DYE(Material.INK_SACK,"橙色の染料",14),
	BONE_MEAL(Material.INK_SACK,"骨粉",15),
	BONE("骨"),
	SUGAR("砂糖"),
	CAKE("ケーキ"),
	BED("ベッド"),
	DIODE("レッドストーンリピーター"),
	COOKIE("クッキー"),
	//MAP("地図"),//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	SHEARS("ハサミ"),
	MELON("スイカ"),
	PUMPKIN_SEEDS("カボチャの種"),
	MELON_SEEDS("スイカの種"),
	RAW_BEEF("生の牛肉"),
	COOKED_BEEF("ステーキ"),
	RAW_CHICKEN("生の鶏肉"),
	COOKED_CHICKEN("焼き鳥"),
	ROTTEN_FLESH("腐った肉"),
	ENDER_PEARL("エンダーパール"),
	BLAZE_ROD("ブレイズロッド"),
	GHAST_TEAR("ガストの涙"),
	GOLD_NUGGET("金塊"),
	NETHER_STALK("ネザーウォート"),
	POTION("ポーション"),
	GLASS_BOTTLE("ガラス瓶"),
	SPIDER_EYE("クモの目"),
	FERMENTED_SPIDER_EYE("発酵したクモの目"),
	BLAZE_POWDER("ブレイズパウダー"),
	MAGMA_CREAM("マグマクリーム"),
	BREWING_STAND_ITEM("醸造台"),
	CAULDRON_ITEM("大釜"),
	EYE_OF_ENDER("エンダーアイ"),
	SPECKLED_MELON("きらめくスイカ"),
	/*
	SPAWN_ELDER_GUARDIAN(Material.MONSTER_EGG,"スポーン　エルダーガーディアン",4),
	SPAWN_WITHER_SKELETON(Material.MONSTER_EGG,"スポーン　ウィザースケルトン",5),
	SPAWN_STRAY(Material.MONSTER_EGG,"スポーン　ストレイ",6),
	SPAWN_HUSK(Material.MONSTER_EGG,"スポーン　ハスク",23),
	SPAWN_ZOMBIE_VILLAGER(Material.MONSTER_EGG,"スポーン　村人ゾンビ",27),
	SPAWN_SKELETON_HORSE(Material.MONSTER_EGG,"スポーン　スケルトンのウマ",28),
	SPAWN_ZOMBIE_HORSE(Material.MONSTER_EGG,"スポーン　アンデッドのウマ",29),
	SPAWN_DONKEY(Material.MONSTER_EGG,"スポーン　ロバ",31),
	SPAWN_MULE(Material.MONSTER_EGG,"スポーン　ラバ",32),
	SPAWN_EVOKER(Material.MONSTER_EGG,"スポーン　エヴォーカー",34),
	SPAWN_VEX(Material.MONSTER_EGG,"スポーン　ヴェックス",35),
	SPAWN_VINDICATOR(Material.MONSTER_EGG,"スポーン　ヴィンディケーター",36),//noitem↑
	SPAWN_CREEPER(Material.MONSTER_EGG,"スポーン　クリーパー",50),
	SPAWN_SKELETON(Material.MONSTER_EGG,"スポーン　スケルトン",51),
	SPAWN_SPIDER(Material.MONSTER_EGG,"スポーン　クモ",52),
	SPAWN_ZOMBIE(Material.MONSTER_EGG,"スポーン　ゾンビ",54),
	SPAWN_SLIME(Material.MONSTER_EGG,"スポーン　スライム",55),
	SPAWN_GHAST(Material.MONSTER_EGG,"スポーン　ガスト",56),
	SPAWN_ZOMBIE_PIGMAN(Material.MONSTER_EGG,"スポーン　ゾンビピッグマン",57),
	SPAWN_ENDERMAN(Material.MONSTER_EGG,"スポーン　エンダーマン",58),
	SPAWN_CAVE_SPIDER(Material.MONSTER_EGG,"スポーン　洞窟グモ",59),
	SPAWN_SILVERFISH(Material.MONSTER_EGG,"スポーン　シルバーフィッシュ",60),
	SPAWN_BLAZE(Material.MONSTER_EGG,"スポーン　ブレイズ",61),
	SPAWN_MAGMA_CUBE(Material.MONSTER_EGG,"スポーン　マグマキューブ",62),
	SPAWN_BAT(Material.MONSTER_EGG,"スポーン　コウモリ",65),
	SPAWN_WITCH(Material.MONSTER_EGG,"スポーン　ウィッチ",66),
	SPAWN_ENDERMITE(Material.MONSTER_EGG,"スポーン　エンダーマイト",67),
	SPAWN_GUARDIAN(Material.MONSTER_EGG,"スポーン　ガーディアン",68),
	SPAWN_SHULKER(Material.MONSTER_EGG,"スポーン　シュルカー",69),
	SPAWN_PIG(Material.MONSTER_EGG,"スポーン　ブタ",90),
	SPAWN_SHEEP(Material.MONSTER_EGG,"スポーン　ヒツジ",91),
	SPAWN_COW(Material.MONSTER_EGG,"スポーン　ウシ",92),
	SPAWN_CHICKEN(Material.MONSTER_EGG,"スポーン　ニワトリ",93),
	SPAWN_SQUID(Material.MONSTER_EGG,"スポーン　イカ",94),
	SPAWN_WOLF(Material.MONSTER_EGG,"スポーン　オオカミ",95),
	SPAWN_MOOSHROOM(Material.MONSTER_EGG,"スポーン　ムーシュルーム",96),
	SPAWN_OCELOT(Material.MONSTER_EGG,"スポーン　ヤマネコ",98),
	SPAWN_HORSE(Material.MONSTER_EGG,"スポーン　ウマ",100),
	SPAWN_RABBIT(Material.MONSTER_EGG,"スポーン　ウサギ",101),
	SPAWN_POLAR_BEAR(Material.MONSTER_EGG,"スポーン　シロクマ",102),//noitem
	SPAWN_LLAMA(Material.MONSTER_EGG,"スポーン　ラマ",103),//noitem
	SPAWN_VILLAGER(Material.MONSTER_EGG,"スポーン　村人",120),
	*/
	EXP_BOTTLE("エンチャント瓶"),
	FIREBALL("ファイヤーチャージ"),
	BOOK_AND_QUILL("本と羽ペン"),
	//WRITTEN_BOOK("記入済みの本"),
	EMERALD("エメラルド"),
	ITEM_FRAME("額縁"),
	FLOWER_POT_ITEM("植木鉢"),
	CARROT_ITEM("ニンジン"),
	POTATO_ITEM("ジャガイモ"),
	BAKED_POTATO("ベイクドポテト"),
	POISONOUS_POTATO("青くなったジャガイモ"),
	EMPTY_MAP("白紙の地図"),
	GOLDEN_CARROT("金のニンジン"),
	SKULL_ITEM("スケルトンの頭蓋骨"),
	MOB_HEAD_WITHER_SKELETON(Material.SKULL_ITEM,"ウィザースケルトンの頭蓋骨",1),
	MOB_HEAD_ZOMBIE(Material.SKULL_ITEM,"ゾンビの頭",2),
	//MOB_HEAD_HUMAN(Material.SKULL_ITEM,"頭",3),
	MOB_HEAD_CREEPER(Material.SKULL_ITEM,"クリーパーの頭",4),
	MOB_HEAD_DRAGON(Material.SKULL_ITEM,"ドラゴンの頭",5),
	CARROT_STICK("ニンジン付きの棒"),
	NETHER_STAR("ネザースター"),
	PUMPKIN_PIE("パンプキンパイ"),
	//FIREWORK("ロケット花火"),
	FIREWORK_CHARGE("花火の星"),
	ENCHANTED_BOOK("エンチャント本"),
	REDSTONE_COMPARATOR("レッドストーンコンパレーター"),
	NETHER_BRICK_ITEM("ネザーレンガ"),
	QUARTZ("ネザー水晶"),
	EXPLOSIVE_MINECART("TNT付きトロッコ"),
	HOPPER_MINECART("ホッパー付きトロッコ"),
	PRISMARINE_SHARD("プリズマリンの欠片"),
	PRISMARINE_CRYSTALS("プリズマリンクリスタル"),
	RABBIT("生の兎"),
	COOKED_RABBIT("焼き兎肉"),
	RABBIT_STEW("ウサギシチュー"),
	RABBIT_FOOT("ウサギの足"),
	RABBIT_HIDE("ウサギの皮"),
	ARMOR_STAND("防具立て"),
	IRON_BARDING("鉄の馬鎧"),
	GOLD_BARDING("金の馬鎧"),
	DIAMOND_BARDING("ダイヤの馬鎧"),
	LEASH("リード"),
	NAME_TAG("名札"),
	//COMMAND_MINECART("コマンドブロック"),
	MUTTON("生の羊肉"),
	COOKED_MUTTON("焼いた羊肉"),
	//BANNER("旗"),
	END_CRYSTAL("エンドクリスタル"),
	SPRUCE_DOOR_ITEM("マツのドア"),
	BIRCH_DOOR_ITEM("シラカバのドア"),
	JUNGLE_DOOR_ITEM("ジャングルのドア"),
	ACACIA_DOOR_ITEM("アカシアのドア"),
	DARK_OAK_DOOR_ITEM("ダークオークのドア"),
	CHORUS_FRUIT("コーラスフルーツ"),
	CHORUS_FRUIT_POPPED("焼いたコーラスフルーツ"),
	BEETROOT("ビートルート"),
	BEETROOT_SEEDS("ビートルートの種"),
	BEETROOT_SOUP("ビートルートスープ"),
	//DRAGONS_BREATH("ドラゴンブレス"),
	//SPLASH_POTION("スプラッシュポーション"),
	SPECTRAL_ARROW("マーキングの矢"),
	//TIPPED_ARROW("効果付きの矢"),
	//LINGERING_POTION("残留ポーション"),
	SHIELD("盾"),
	ELYTRA("エリトラ"),
	BOAT_SPRUCE("マツのボート"),
	BOAT_BIRCH("シラカバのボート"),
	BOAT_JUNGLE("ジャングルのボート"),
	BOAT_ACACIA("アカシアのボート"),
	BOAT_DARK_OAK("ダークオークのボート"),
	GOLD_RECORD("レコード"),
	GREEN_RECORD("レコード"),
	RECORD_3("レコード"),
	RECORD_4("レコード"),
	RECORD_5("レコード"),
	RECORD_6("レコード"),
	RECORD_7("レコード"),
	RECORD_8("レコード"),
	RECORD_9("レコード"),
	RECORD_10("レコード"),
	RECORD_11("レコード"),
	RECORD_12("レコード"),
	;


	private final Material material;
	private final String jpname;
	//private final int maxStack;
	private final short durability;


	private StackType(String jpname){
		this(null,jpname);
	}
	private StackType(Material material,String jpname) {
		this(material,jpname,0);
	}
	private StackType(Material material,String jpname,int durability) {
		this(material,jpname,(short)durability);
	}
	private StackType(Material material,String jpname,short durability){
		this.material = material;
		this.jpname = jpname;
		this.durability = durability;
	}


	public static HashMap<Material,List<Short> > material_map = new HashMap<Material, List<Short>>();
	public static HashMap<Material,StackType> m_s_map = new HashMap<Material,StackType>();
	public static HashMap<Integer,StackType> i_s_map = new HashMap<Integer,StackType>();

	static{
		for(StackType st : values()){
			i_s_map.put(st.ordinal(), st);
			if(!material_map.containsKey(st.getMaterial())){
				material_map.put(st.getMaterial(),new ArrayList<Short>(Arrays.asList(st.getDurability())));
				m_s_map.put(st.getMaterial(), st);
			}else{
				material_map.get(st.getMaterial()).add(new Short(st.getDurability()));
			}
		}
	}



	/**Materialを返します
	 *
	 * @return
	 */
	public Material getMaterial(){
		return this.material != null ? this.material : Material.getMaterial(this.name().toUpperCase());
	}
	/**jpnameを返します．
	 *
	 * @return
	 */
	public String getJPname(){
		return this.jpname;
	}
	/**durabilityを返します．
	 *
	 * @return
	 */
	public short getDurability(){
		return this.durability;
	}
	/**カラムネームを返します．
	 *
	 * @return
	 */
	public String getColumnName(){
		return this.name();
	}

	/**Stackできるかどうか判定します．
	 *
	 * @param itemstack
	 * @return
	 */
	public static boolean canStack(ItemStack itemstack){
		Material m = itemstack.getType();
		short durability = itemstack.getDurability();
		return material_map.containsKey(m) ? material_map.get(m).contains(durability) : false;

	}

	public ItemStack getItemStack(){
		ItemStack itemstack =  new ItemStack(this.getMaterial());
		itemstack.setDurability(this.getDurability());
		return itemstack;
	}
	public static StackType getStackType(ItemStack itemstack) {
		Material m = itemstack.getType();
		short durability = itemstack.getDurability();
		int i = m_s_map.get(m).ordinal();
		return i_s_map.get(i + durability);
	}



	/*
	//Material
	private Material material;

	//オブジェクトマテリアル名
	private String name;
	//日本語名
	private String jpname;
	//解禁レベル
	private Integer level;

	//耐久値
	private Integer durability;
	//説明文の有無
	private Boolean nameloreflag;
	//ガチャで使用されるかどうか
	private Boolean gachaflag;
	//説明文
	private List<String> lore;
	//アイテムスタック型
	private ItemStack itemstack;
	//マテリアルの種類
	private Integer type;

	//スキルで適用するか
	private Boolean skillflag;
	//幸運が適用されるか
	private Boolean luckflag;
	//スキルで使うツールか
	private Boolean toolflag;
	//棒を右クリック時に無視されるか
	private Boolean cancelflag;
	//スキル条件：透過するか
	private Boolean transflag;
	//重力値条件：無視されるか
	private Boolean ignoreflag;
*/


}

