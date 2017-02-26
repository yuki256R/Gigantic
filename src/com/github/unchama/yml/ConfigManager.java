package com.github.unchama.yml;




public class ConfigManager extends YmlManager{

	public ConfigManager(){
		super();
		}

	public String getDB(){
		return getString("db");
	}
	public String getTable() {
		return getString("table");
	}
	public String getID(){
		return getString("id");
	}
	public String getPW(){
		return getString("pw");
	}
	public String getURL(){
		String url = "jdbc:mysql://";
		url += getString("host");
		String port = getString("port");
		if(port != null){
			url += ":" + port;
		}
		return url;
	}

	public String getSeichiDB(){
		return getString("seichiassist.db");
	}
	public String getSeichiTable() {
		return getString("seichiassist.table");
	}
	public String getSeichiID(){
		return getString("seichiassist.id");
	}
	public String getSeichiPW(){
		return getString("seichiassist.pw");
	}
	public String getSeichiURL(){
		String url = "jdbc:mysql://";
		url += getString("seichiassist.host");
		String port = getString("seichiassist.port");
		if(port != null){
			url += ":" + port;
		}
		return url;
	}
	public Boolean getOldDataFlag(){
		return this.getBoolean("olddatabase");
	}

	public float getNumOfPeopleRate(){
		return this.getFloat("mineboost.rate.numofpeople");
	}

	@Override
	public void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
	}

	public float getMinuteMineRate() {
		return this.getFloat("mineboost.rate.minutemine");
	}

	public int getMaxSeichiLevel(){
		return this.getInt("MaxSeichiLevel");
	}

	public String getSeichiLevelUpMessage(int level){
		return this.getString("seichi.levelupmessage");
	}
	public String getSeichiLevelMessage(int level) {
		String message = this.fc.getString("seichi.levelmessage." + level);
		return message != null ? message : null;
	}



}
