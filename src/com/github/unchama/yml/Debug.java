package com.github.unchama.yml;

import java.util.HashMap;

import com.github.unchama.enumdata.DebugEnum;
import com.github.unchama.util.Converter;

public final class Debug extends Yml{
	private static HashMap<String,Boolean> debugmap = new HashMap<String,Boolean>();
	
	public Debug() {
		putAllFlag();

	}
	private void putAllFlag() {
		
		for(DebugEnum de : DebugEnum.values()){
			Boolean defaultflag = getBoolean(de.name());
			if(defaultflag == null){
				debugmap.put(de.name(), de.getDefaultFlag());
			}else{
				debugmap.put(de.name(), defaultflag);
			}
		}
	}
	@Override
	public String getFileName() {
		return "debug";
	}

	@Override
	public void saveDefault() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	public Boolean getFlag(String s){
		Boolean flag = debugmap.get(s.toUpperCase());
		if(flag = null){
			plugin.getLogger().warning(this.getFileName() + ".yml内に" + s +"値が宣言されていません．");
			return true;
		}
		return flag;
	}
	
	public Boolean getDebugMode() {
		String s;
		Boolean flag;

		s = getString("debugmode");

		if(s==null){
			plugin.getLogger().warning("config内にDEBUG値が宣言されていません．");
			flag = false;
		}else{
			flag = Converter.toBoolean(s);
			if(flag){
				plugin.getLogger().fine("Giganticをデバッグモードで起動します");
			}else{
				plugin.getLogger().fine("Giganticを通常モードで起動します");
			}
		}
		return flag;
	}

}
