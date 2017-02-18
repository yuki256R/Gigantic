package com.github.unchama.yml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.logging.Level;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

public final class DebugManager extends YmlManager{
	public enum DebugEnum {
		MINEBOOST(false,ChatColor.AQUA),
		MINEBLOCK(false,ChatColor.RED),
		;
		private Boolean flag;
		private ChatColor c;

		private DebugEnum(Boolean flag,ChatColor c){
			this.flag = flag;
			this.c = c;
		}

		public Boolean getDefaultFlag(){
			return flag;
		}
		public ChatColor getColor(){
			return c;
		}

	}

	private static HashMap<String,Boolean> debugmap = new HashMap<String,Boolean>();

	public DebugManager() {
		super();
		init();
	}


	public void sendMessage(Player p,DebugEnum de,String message){
		if(getFlag(de)){
			p.sendMessage(de.getColor() + message);
		}
	}


	private Boolean getFlag(DebugEnum de){
		return this.getBoolean(de.name());
	}


	private void init() {

		for(DebugEnum de : DebugEnum.values()){
			Boolean defaultflag = getBoolean(de.name());
				debugmap.put(de.name(), defaultflag);
		}
	}


	private void makeFile(){
		try {
			OutputStream out = new FileOutputStream(file);
			for(DebugEnum de : DebugEnum.values()){
				String s = de.name() + ": " + de.getDefaultFlag().toString();
				s += System.getProperty("line.separator");
				out.write(s.getBytes());
			}
			out.close();
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save " + file.getName()
					+ " to " + file, ex);
		}
	}

	@Override
	public void saveDefaultFile(){
		if (!file.exists()) {
			makeFile();
		}
	}



}
