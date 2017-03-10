package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.minestack.MineStack;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

public class MineStackTableManager extends PlayerFromSeichiTableManager{
	public static enum StackConvert{
		//<StackTypeの名前>(<SeichiAssist内の名前>)
		DIRT,
		GRASS,
		COBBLESTONE,
		STONE,
		GRANITE,
		DIORITE,
		ANDESITE,
		GRAVEL,
		SAND,
		SANDSTONE,
		NETHERRACK,
		SOUL_SAND,
		COAL,
		COAL_ORE,
		ENDER_STONE,
		IRON_ORE,
		OBSIDIAN,
		PACKED_ICE,
		QUARTZ,
		QUARTZ_ORE,
		MAGMA,
		GOLD_ORE,
		GLOWSTONE,
		REDSTONE_ORE,
		LAPIS_LAZULI,
		LAPIS_ORE,
		DIAMOND,
		DIAMOND_ORE,
		EMERALD,
		EMERALD_ORE,
		RED_SAND,
		RED_SANDSTONE,
		HARD_CLAY,
		STAINED_CLAY,
		CLAY,
		MOSSY_COBBLESTONE,
		ICE,
		ORANGE_STAINED_CLAY("stained_clay1"),
		YELLOW_STAINED_CLAY("stained_clay4"),
		LIGHT_GRAY_STAINED_CLAY("stained_clay8"),
		BROWN_STAINED_CLAY("stained_clay12"),
		RED_STAINED_CLAY("stained_clay14"),
		COARSE_DIRT("dirt1"),
		PODZOL("dirt2"),
		SNOW_BLOCK,
		MYCEL,
		BONE_BLOCK,
		ENDER_PEARL,
		SLIME_BALL,
		ROTTEN_FLESH,
		BONE,
		SULPHUR,
		ARROW,
		SPIDER_EYE,
		STRING,
		EGG,
		PORK,
		RAW_CHICKEN,
		MUTTON,
		RAW_BEEF,
		RAW_FISH("raw_fish0"),
		RAW_SALMON("raw_fish1"),
		CLOWNFISH("raw_fish2"),
		PUFFERFISH("raw_fish3"),
		BLAZE_ROD,
		GHAST_TEAR,
		MAGMA_CREAM,
		PRISMARINE_SHARD,
		PRISMARINE_CRYSTALS,
		FEATHER,
		INK_SACK("ink_sack0"),
		LEATHER,
		RABBIT_HIDE,
		RABBIT_FOOT,
		LOG,
		SPRUCE_WOOD("log1"),
		BIRCH_WOOD("log2"),
		JUNGLE_WOOD("log3"),
		DARK_OAK_WOOD("log_21"),
		SEEDS,
		APPLE,
		LONG_GRASS("long_grass1"),
		FERN("long_grass2"),
		DEAD_BUSH,
		CACTUS,
		VINE,
		WATER_LILY,
		YELLOW_FLOWER,
		RED_ROSE("red_rose0"),
		BLUE_ORCHID("red_rose1"),
		ALLIUM("red_rose2"),
		AZURE_BLUET("red_rose3"),
		RED_TULIP("red_rose4"),
		ORANGE_TULIP("red_rose5"),
		WHITE_TULIP("red_rose6"),
		PINK_TULIP("red_rose7"),
		OXEYE_DAISY("red_rose8"),
		LEAVES,
		SPRUCE_LEAVES("leaves1"),
		BIRCH_LEAVES("leaves2"),
		JUNGLE_LEAVES("leaves3"),
		DARK_OAK_LEAVES("leaves_21"),
		DOUBLE_PLANT("double_plant0"),
		LILAC("double_plant1"),
		DOUBLE_TALLGRASS("double_plant2"),
		LARGE_FERN("double_plant3"),
		ROSE_BUSH("double_plant4"),
		PEONY("double_plant5"),
		SUGER_CANE,
		PUMPKIN,
		COCO_BEANS("ink_sack3"),
		HUGE_MUSHROOM_1,
		HUDE_MUSHROOM_2,
		MELON,
		MELON_BLOCK,
		BROWN_MASHROOM,
		RED_MASHROOM,
		SAPLING,
		SPRUCE_SAPLING("sapling1"),
		BIRCH_SAPLING("sapling2"),
		JUNGLE_SAPLING("sapling3"),
		ACACIA_SAPLING("sapling4"),
		DARK_OAK_SAPLING("sapling5"),
		BEETROOT,
		BEETROOT_SEEDS,
		CARROT_ITEM,
		POTATO_ITEM,
		WHEAT,
		PUMPKIN_SEEDS,
		MELON_SEEDS,
		NETHER_STALK,
		STEP("step0"),
		COBBLESTONE_SLAB("step3"),
		WOOD_DOUBLE_STEP("wood_step0"),
		POLISHED_GRANITE,
		POLISHED_DIORITE,
		POLISHED_ANDESITE,
		//DOUBLE_SPRUCE_WOOD_SLAB("wood_step1"),
		//DOUBLE_BIRCH_WOOD_SLAB("wood_step2"),
		//DOUBLE_JUNGLE_WOOD_SLAB("wood_step3"),
		//DOUBLE_ACACIA_WOOD_SLAB("wood_step4"),
		//DOUBLE_DARK_OAK_WOOD_SLAB("wood_step5"),
		FLINT,
		SANDSTONE_SLAB("step1"),
		GLASS,
		IRON_INGOT,
		NETHER_BRICK,
		NETHER_FENCE("nether_brick_fence"),
		NETHER_BRICK_STAIRS,
		TORCH,
		JACK_O_LANTERN,
		NETHER_BRICK_ITEM,
		NETHER_SLAB("step6"),
		QUARTZ_SLAB("step7"),
		QUARTZ_BLOCK,
		END_BRICKS,
		PURPUR_BLOCK,
		PURPUR_PILLAR,
		PURPUR_STAIRS,
		PURPUR_SLAB,
		GOLD_INGOT,
		SNOW_BALL,
		WOOD,
		FENCE,
		BUCKET,
		WATER_BUCKET,
		LAVA_BUCKET,
		MILK_BUCKET,
		STONE_SLAB2("stone_slab20"),
		PRISMARINE("prismarine0"),
		PRISMARINE_BRICKS("prismarine1"),
		DARK_PRISMARINE("prismarine2"),
		SEA_LANTERN,
		BRICK_SLAB("step4"),
		CLAY_BALL,
		BRICK,
		CLAY_BRICK("brick_item"),
		DARK_OAK_WOOD_PLANK("wood5"),
		DARK_OAK_FENCE_GATE("dark_oak_fence"),
		WEB,
		RAILS,
		SMOOTH_BRICK("smooth_brick0"),
		MOSSY_STONE_BRICK("smooth_brick1"),
		CRACKED_STONE_BRICK("smooth_brick2"),
		CHISELED_STONE_BRICK("smooth_brick3"),
		STONE_SLAB("step5"),
		RED_NETHER_BRICK,
		;
		private String oldname;

		StackConvert(){
			this(null);
		}
		StackConvert(String oldname){
			this.oldname = oldname;
		}
		public String getOldName(){
			return "stack_" + (this.oldname == null ? this.name() : this.oldname).toLowerCase();
		}
		public String getNewName(){
			return this.name();
		}
		public static boolean isExist(String name){
			for(StackConvert sc : StackConvert.values()){
				if(sc.getNewName().equals(name)){
					return true;
				}
			}
			return false;
		}
	}

	public MineStackTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand(){
		String command = "";
		//MineStack add
		for(StackType st : StackType.values()){
			command += "add column if not exists " +
						st.getColumnName() + " bigint default 0,";
		}
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		MineStackManager m = gp.getManager(MineStackManager.class);
		LinkedHashMap<StackType,MineStack> datamap = m.datamap;
		String command = "";
		for(StackType st : datamap.keySet()){
			command += st.getColumnName() + " = '" + datamap.get(st).getNum() + "',";
		}
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		MineStackManager m = gp.getManager(MineStackManager.class);
		LinkedHashMap<StackType,MineStack> datamap = m.datamap;
		datamap.putAll(tm.getMineStack(gp));
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		MineStackManager m = gp.getManager(MineStackManager.class);
		LinkedHashMap<StackType,MineStack> datamap = m.datamap;
		for(StackType st : StackType.values()){
			datamap.put(st,new MineStack());
		}
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		MineStackManager m = gp.getManager(MineStackManager.class);
		LinkedHashMap<StackType,MineStack> datamap = m.datamap;
		for(StackType st : StackType.values()){
			datamap.put(st,new MineStack(rs.getLong(st.getColumnName())));
		}
	}

}
