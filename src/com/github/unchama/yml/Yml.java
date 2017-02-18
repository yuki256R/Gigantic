package com.github.unchama.yml;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;



public class Yml {
	enum YmlEnum{
		CONFIG(ConfigManager.class),
		DEBUG(DebugManager.class),
		MAINMENU(MainMenuManager.class),
		;

		private Class<? extends YmlManager> managerClass;

		YmlEnum(Class<? extends YmlManager> managerClass){
			this.managerClass = managerClass;
		}

		public Class<? extends YmlManager> getManagerClass(){
			return managerClass;
		}
		/**sqlのテーブル名を取得する
		 *
		 * @return
		 */
		public String getYmlName(){
			return this.name().toLowerCase() + ".yml";
		}

		public static String getTableNamebyClass(Class<? extends YmlManager> _class) {
			for(YmlEnum ye : YmlEnum.values()){
				if(ye.getManagerClass().equals(_class)){
					return ye.getYmlName();
				}
			}
			return "example";
		}
	}

	private static HashMap<YmlEnum,YmlManager> managermap = new HashMap<YmlEnum,YmlManager>();


	public Yml(){
		//instance作成
		for(YmlEnum ye : YmlEnum.values()){
			try {
				this.managermap.put(ye,ye.getManagerClass().getConstructor().newInstance());
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	public ConfigManager getConfigManager(){
		return (ConfigManager) managermap.get(YmlEnum.CONFIG);
	}
	public DebugManager getDebugManager(){
		return (DebugManager) managermap.get(YmlEnum.DEBUG);
	}
	public MainMenuManager getMainMenuManager(){
		return (MainMenuManager) managermap.get(YmlEnum.MAINMENU);
	}
}
