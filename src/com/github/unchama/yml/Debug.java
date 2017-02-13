package com.github.unchama.yml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.logging.Level;

public final class Debug extends Yml{
	enum DebugEnum {
		ALL(false),
		MINEBOOST(false),
		;
		private Boolean flag;

		private DebugEnum(Boolean flag){
			this.flag = flag;
		}

		public Boolean getDefaultFlag(){
			return flag;
		}

	}

	private static HashMap<String,Boolean> debugmap = new HashMap<String,Boolean>();

	public Debug() {
		init();
	}



	public Boolean getFlag(DebugEnum de){
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
