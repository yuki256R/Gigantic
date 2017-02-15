package com.github.unchama.player.mineblock;

import java.util.HashMap;

import org.bukkit.Material;

import com.github.unchama.player.DataManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.MineBlockTableManager;

public class MineBlockManager extends DataManager{

	private HashMap<BlockType,MineBlock> datamap = new HashMap<BlockType,MineBlock>();
	MineBlockTableManager tm;


	//new Player Instance
	public MineBlockManager(GiganticPlayer gp){
		super(gp);
		this.tm = sql.getMineBlockTableManager();
		if(!tm.load(gp.uuid)){
			plugin.getLogger().warning("Failed to load MineBlock of player:" + gp.name);
		}
		for(BlockType bt : BlockType.values()){
			datamap.put(bt,new MineBlock());
		}
	}








	public void increase(Material material){
		this.increase(material,1);
	}
	/**破壊した数を引数に整地量を加算
	 *
	 * @param material
	 * @param breaknum
	 */
	public void increase(Material material, int breaknum) {
		double ratio = BlockType.getIncreaseRatio(material);
		BlockType bt = BlockType.getmaterialMap().get(material);
		datamap.get(bt).increase(breaknum * ratio);
	}
}
