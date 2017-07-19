package com.github.unchama.command;

import org.bukkit.command.TabExecutor;

import com.github.unchama.command.commands.AchieveCommand;
import com.github.unchama.command.commands.DebugCommand;
import com.github.unchama.command.commands.EnchantmentCommand;
import com.github.unchama.command.commands.FlyCommand;
import com.github.unchama.command.commands.GachaCommand;
import com.github.unchama.command.commands.GrowthCommand;
import com.github.unchama.command.commands.HomeCommand;
import com.github.unchama.command.commands.LastquitCommand;
import com.github.unchama.command.commands.ListenCommand;
import com.github.unchama.command.commands.MineBoostCommand;
import com.github.unchama.command.commands.PresentBoxCommand;
import com.github.unchama.command.commands.StickCommand;
import com.github.unchama.command.commands.TakeoverCommand;
/**
 * @author tar0ss
 *
 */
public enum CommandType {
	GACHA(new GachaCommand()),
	DEBUG(new DebugCommand()),
	MINEBOOST(new MineBoostCommand()),
	STICK(new StickCommand()),
	FLY(new FlyCommand()),
	GROWTH(new GrowthCommand()),
	PRESENTBOX(new PresentBoxCommand()),
	GENCHANT(new EnchantmentCommand()),
	HOME(new HomeCommand()),
	LISTEN(new ListenCommand()),
	TAKEOVER(new TakeoverCommand()),
	LASTQUIT(new LastquitCommand()),
	ACHIEVE(new AchieveCommand()),
	;


	private TabExecutor te;

	private CommandType(TabExecutor te){
		this.te = te;
	}


	public TabExecutor getCommand(){
		return te;
	}

	public static TabExecutor getCommandbyName(String name){
		for(CommandType ce : CommandType.values()){
			if(ce.name().equals(name.toUpperCase())){
				return ce.getCommand();
			}
		}
		return null;
	}

}
