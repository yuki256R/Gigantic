package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.minestack.MineStack;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

public class MineStackTableManager extends PlayerFromSeichiTableManager{
	public static enum StackConvert{
		//<StackTypeの名前>(<SeichiAssist内の名前>)
		ORANGE_STAINED_CLAY("stained_clay1"),
		YELLOW_STAINED_CLAY("stained_clay4"),
		LIGHT_GRAY_STAINED_CLAY("stained_clay8"),
		BROWN_STAINED_CLAY("stained_clay12"),
		RED_STAINED_CLAY("stained_clay14"),
		COARSE_DIRT("dirt1"),
		PODZOL("dirt2"),
		RAW_FISH("raw_fish1"),
		CLOWNFISH("raw_fish2"),
		PUFFERFISH("raw_fish3"),
		INK_SACK("ink_sack0"),
		SPRUCE_WOOD("log1"),
		BIRCH_WOOD("log2"),
		JUNGLE_WOOD("log3"),
		DARK_OAK_WOOD("log_21"),
		LONG_GRASS("long_grass1"),
		FERN("long_grass2"),
		RED_ROSE("red_rose0"),
		BLUE_ORCHID("red_rose1"),
		ALLIUM("red_rose2"),
		AZURE_BLUET("red_rose3"),
		RED_TULIP("red_rose4"),
		ORANGE_TULIP("red_rose5"),
		WHITE_TULIP("red_rose6"),
		PINK_TULIP("red_rose7"),
		OXEYE_DAISY("red_rose8"),
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
		COCO_BEANS("ink_sack3"),
		SPRUCE_SAPLING("sapling1"),
		BIRCH_SAPLING("sapling2"),
		JUNGLE_SAPLING("sapling3"),
		ACACIA_SAPLING("sapling4"),
		DARK_OAK_SAPLING("sapling5"),
		STEP("step0"),
		COBBLESTONE_SLAB("step3"),
		WOOD_DOUBLE_STEP("wood_step0"),
		//DOUBLE_SPRUCE_WOOD_SLAB("wood_step1"),
		//DOUBLE_BIRCH_WOOD_SLAB("wood_step2"),
		//DOUBLE_JUNGLE_WOOD_SLAB("wood_step3"),
		//DOUBLE_ACACIA_WOOD_SLAB("wood_step4"),
		//DOUBLE_DARK_OAK_WOOD_SLAB("wood_step5"),
		SANDSTONE_SLAB("step1"),
		NETHER_FENCE("nether_brick_fence"),
		NETHER_SLAB("step6"),
		QUARTZ_SLAB("step7"),
		STONE_SLAB2("stone_slab20"),
		PRISMARINE("prismarine0"),
		PRISMARINE_BRICKS("prismarine1"),
		DARK_PRISMARINE("prismarine2"),
		BRICK_SLAB("step4"),
		CLAY_BRICK("brick_item"),
		DARK_OAK_WOOD_PLANK("wood5"),
		DARK_OAK_FENCE_GATE("dark_oak_fence"),
		SMOOTH_BRICK("smooth_brick0"),
		MOSSY_STONE_BRICK("smooth_brick1"),
		CRACKED_STONE_BRICK("smooth_brick2"),
		CHISELED_STONE_BRICK("smooth_brick3"),
		STONE_SLAB("step5"),
		;
		private String oldname;

		StackConvert(String oldname){
			this.oldname = oldname;
		}
		public String getOldName(){
			return "stack_"+ this.oldname;
		}
		public String getNewName(){
			return this.name();
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
		HashMap<StackType,MineStack> datamap = m.datamap;
		String command = "";
		for(StackType st : datamap.keySet()){
			command += st.getColumnName() + " = '" + datamap.get(st).getNum() + "',";
		}
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		MineStackManager m = gp.getManager(MineStackManager.class);
		HashMap<StackType,MineStack> datamap = m.datamap;
		tm.getMineStack(gp,datamap);

		/*
		for(StackType st : StackType.values()){
			if(!datamap.containsKey(st)){
				datamap.put(st,new MineStack());
			}
		}*/
		PlayerManager.getPlayer(gp).sendMessage("takeover");
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		MineStackManager m = gp.getManager(MineStackManager.class);
		HashMap<StackType,MineStack> datamap = m.datamap;
		for(StackType st : StackType.values()){
			datamap.put(st,new MineStack());
		}
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		MineStackManager m = gp.getManager(MineStackManager.class);
		HashMap<StackType,MineStack> datamap = m.datamap;
		for(StackType st : StackType.values()){
			datamap.put(st,new MineStack(rs.getLong(st.getColumnName())));
		}
	}

}
