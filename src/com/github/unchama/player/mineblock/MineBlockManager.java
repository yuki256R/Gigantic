package com.github.unchama.player.mineblock;

import java.util.HashMap;

import org.bukkit.Material;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.DataManager;
import com.github.unchama.sql.Sql;

public class MineBlockManager extends DataManager{

	Sql sql = Gigantic.sql;


	private HashMap<Material,MineBlock> datamap= new HashMap<Material,MineBlock>();


	//new Player Instance
	public MineBlockManager(){
		for(Material m : BlockType.getmaterialMap().keySet()){
			datamap.put(m,new MineBlock());
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
		datamap.get(material).increase(breaknum * ratio);
	}
}
