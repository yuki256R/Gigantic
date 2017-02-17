package com.github.unchama.yml;




public class Config extends Yml{

	public Config(){
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



}
