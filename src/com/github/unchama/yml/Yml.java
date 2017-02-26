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

	private static HashMap<Class<? extends YmlManager>,YmlManager> managermap = new HashMap<Class<? extends YmlManager>,YmlManager>();


	public Yml(){
		//instance作成
		for(YmlEnum ye : YmlEnum.values()){
			try {
				managermap.put(ye.managerClass,ye.getManagerClass().getConstructor().newInstance());
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public <T extends YmlManager> T getManager(Class<T> type){
		return (T) managermap.get(type);
	}
}
