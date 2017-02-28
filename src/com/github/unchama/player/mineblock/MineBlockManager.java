package com.github.unchama.player.mineblock;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.github.unchama.event.SeichiLevelUpEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.MineBlockTableManager;
import com.github.unchama.util.SeichiLevelUtil;

public class MineBlockManager extends DataManager implements UsingSql,Initializable{

	public HashMap<BlockType,MineBlock> datamap = new HashMap<BlockType,MineBlock>();
	public MineBlock all;
	public int level;
	MineBlockTableManager tm;



	public MineBlockManager(GiganticPlayer gp){
		super(gp);
		this.tm = sql.getManager(MineBlockTableManager.class);
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
		double inc = breaknum * ratio;
		datamap.get(bt).increase(inc);
		all.increase(inc);
	}


	@Override
	public void save() {
		tm.save(gp);
	}

	@Override
	public void load(){
		tm.load(gp);
	}

	@Override
	public void init() {
		this.calcLevel();
	}

	private boolean calcLevel(){
		boolean changeflag = false;
		while(SeichiLevelUtil.canLevelup(level,this.all.getNum())){
			Bukkit.getServer().getPluginManager().callEvent(new SeichiLevelUpEvent(gp,level + 1));
			level ++;
			changeflag = true;
		}
		return changeflag;
	}















}
