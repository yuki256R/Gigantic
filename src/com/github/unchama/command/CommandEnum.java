package com.github.unchama.command;

import org.bukkit.command.TabExecutor;

public enum CommandEnum {
	GACHA(new gachaCommand()),
	;


	private TabExecutor te;

	private CommandEnum(TabExecutor te){
		this.te = te;
	}


	public TabExecutor getCommand(){
		return te;
	}

	public static TabExecutor getCommandbyName(String name){
		for(CommandEnum ce : CommandEnum.values()){
			if(ce.name().equals(name.toUpperCase())){
				return ce.getCommand();
			}
		}
		return null;
	}

}
