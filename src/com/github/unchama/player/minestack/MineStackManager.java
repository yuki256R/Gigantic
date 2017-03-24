package com.github.unchama.player.minestack;

import java.util.LinkedHashMap;

import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.MineStackTableManager;

public class MineStackManager extends DataManager implements UsingSql{
	MineStackTableManager tm ;
	public LinkedHashMap<StackType,MineStack> datamap;

	public MineStackManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(MineStackTableManager.class);
		this.datamap = new LinkedHashMap<StackType,MineStack>();
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	public boolean add(ItemStack itemstack){
		if(StackType.canStack(itemstack) && this.isLoaded()){
			StackType st = StackType.getStackType(itemstack);
			datamap.get(st).add(itemstack.getAmount());
			return true;
		}else{
			return false;
		}
	}
}
