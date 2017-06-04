package com.github.unchama.command;

import org.bukkit.command.TabExecutor;

public enum CommandType {
	GACHA(new gachaCommand()),
	DEBUG(new debugCommand()),
	MINEBOOST(new mineboostCommand()),
	STICK(new stickCommand()),
    FLY(new flyCommand()),
    PRESENTBOX(new presentboxCommand()),
	GENCHANT(new EnchantmentCommand()),
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
