package com.github.unchama.command;

import org.bukkit.command.TabExecutor;
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
<<<<<<< HEAD
	HOME(new HomeCommand()),
=======
	LISTEN(new ListenCommand()),
	TAKEOVER(new TakeoverCommand()),
	LASTQUIT(new LastquitCommand()),
>>>>>>> origin/master
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
