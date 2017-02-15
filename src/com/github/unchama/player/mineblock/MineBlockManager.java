package com.github.unchama.player.mineblock;

import java.util.HashMap;

import org.bukkit.Material;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.DataManager;
import com.github.unchama.sql.Sql;

public class MineBlockManager extends DataManager{

	Sql sql = Gigantic.sql;
	private HashMap<BlockType,MineBlock> datamap = new HashMap<BlockType,MineBlock>();


	//new Player Instance
	public MineBlockManager(){
		for(BlockType bt : BlockType.values()){
			datamap.put(bt,new MineBlock());
		}
	}
	
	//load Player Instance
	public MineBlockManager(HashMap<BlockType,MineBlock> datamap){
		this.datamap = datamap;
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
