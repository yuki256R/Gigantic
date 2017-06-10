package com.github.unchama.player.mineblock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;

import com.github.unchama.gigantic.Gigantic;


/**
 * @author tar0ss
 *
 */
public enum BlockType{
	//Materialの種類が一つならjpnameのみ記述
	STONE("石"),
	//Materialの種類が２つ以上なら全てのmaterial名をListに追加
	DIRT("土",new ArrayList<Material>(Arrays.asList(
			Material.DIRT,
			Material.GRASS
			))),
	COBBLESTONE("丸石",new ArrayList<Material>(Arrays.asList(
			Material.COBBLESTONE,
			Material.MOSSY_COBBLESTONE
			))),
	WOOD("木材"),
	SAPLING("苗木"),
	SAND("砂"),
	GRAVEL("砂利"),
	GOLD_ORE("金鉱石"),
	IRON_ORE("鉄鉱石"),
	COAL_ORE("石炭鉱石"),
	LOG("原木",new ArrayList<Material>(Arrays.asList(
			Material.LOG,
			Material.LOG_2
			))),
	LEAVES("葉系ブロック",new ArrayList<Material>(Arrays.asList(
			Material.LEAVES,
			Material.LEAVES_2
			))),
	LAPIS_ORE("ラピスラズリ鉱石"),
	SANDSTONE("砂岩"),
	WEB("クモの巣"),
	OBSIDIAN("黒曜石"),
	DIAMOND_ORE("ダイヤモンド鉱石"),
	RAILS("レール"),
	REDSTONE_ORE("レッドストーン鉱石",new ArrayList<Material>(Arrays.asList(
			Material.REDSTONE_ORE,
			Material.GLOWING_REDSTONE_ORE
			))),
	ICE("氷"),
	SNOW_BLOCK("雪"),
	CLAY("粘土"),
	FENCE("フェンス",new ArrayList<Material>(Arrays.asList(
			Material.FENCE,
			Material.DARK_OAK_FENCE,
			Material.NETHER_FENCE
			))),
	NETHERRACK("ネザーラック"),
	SOUL_SAND("ソウルサンド"),
	GLOWSTONE("グロウストーン"),
	MONSTER_EGGS("紙魚入り石"),
	SMOOTH_BRICK("石レンガ"),
	HUGE_MUSHROOM("キノコブロック",new ArrayList<Material>(Arrays.asList(
			Material.HUGE_MUSHROOM_1,
			Material.HUGE_MUSHROOM_2
			))),
	NETHER_BRICK("ネザーレンガ",new ArrayList<Material>(Arrays.asList(
			Material.NETHER_BRICK,
			Material.NETHER_BRICK_STAIRS
			))),
	ENDER_STONE("エンドストーン"),
	EMERALD_ORE("エメラルド鉱石"),
	QUARTZ_ORE("ネザー水晶鉱石"),
	PRISMARINE("プリズマリン"),
	SEA_LANTERN("シーランタン"),
	HARD_CLAY("堅焼き粘土",new ArrayList<Material>(Arrays.asList(
			Material.HARD_CLAY,
			Material.STAINED_CLAY
			))),
	PACKED_ICE("氷塊"),
	RED_SANDSTONE("赤い砂岩",new ArrayList<Material>(Arrays.asList(
			Material.RED_SANDSTONE,
			Material.RED_SANDSTONE_STAIRS
			))),
	FROSTED_ICE("氷霜"),
	MAGMA("マグマブロック"),
	BONE_BLOCK("骨ブロック"),
	PURPUR_BLOCK("プルパーブロック",new ArrayList<Material>(Arrays.asList(
			Material.PURPUR_BLOCK,
			Material.PURPUR_PILLAR
			))),
	END_BRICKS("エンドストーンレンガ"),
	MYCEL("菌糸"),
	;

	Gigantic plugin = Gigantic.plugin;

	private final String jpname;

	private final ArrayList<Material> materialList;


	private BlockType(String jpname){
		this(jpname,null);
	}
	private BlockType(String jpname,ArrayList<Material> ml) {
		this.jpname = jpname;
		this.materialList = ml;
	}


	private static HashMap<Material,BlockType> materialMap =
			new HashMap<Material,BlockType>();

	static{
		for(BlockType bt : values()){
			for(Material m : bt.getMaterialList()){
				materialMap.put(m,bt);
			}
		}
	}

	public String getColumnName(){
		return this.name();
	}
	public String getjpname(){
		return this.jpname;
	}
	public ArrayList<Material> getMaterialList(){
		if(this.materialList == null){
			Material m = Material.getMaterial(this.name());
			if(m == null){
				plugin.getLogger().warning("not found Material." + this.name());
				return null;
			}
			return new ArrayList<Material>(Arrays.asList(m));
		}else{
			return this.materialList;
		}
	}

	public static Boolean contains(Material material){
		return materialMap.containsKey(material);
	}

	public static HashMap<Material,BlockType> getmaterialMap(){
		return materialMap;
	}
}
