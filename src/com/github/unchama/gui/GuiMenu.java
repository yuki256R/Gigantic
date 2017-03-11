package com.github.unchama.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.yml.moduler.YmlManager;

public final class GuiMenu {
	public static enum ManagerType {
		MAINMENU(MainMenuManager.class),
		;
		// 使用するManagerClass
		private Class<? extends GuiMenuManager> managerClass;

		// Enum用コンストラクタ
		ManagerType(Class<? extends GuiMenuManager> managerClass) {
			this.managerClass = managerClass;
		}

		/**
		 * 使用するManagerClassを返り値とします．
		 *
		 * @return Class<? extends YmlManager>
		 */
		public Class<? extends GuiMenuManager> getManagerClass() {
			return managerClass;
		}

		/**
		 * sqlのテーブル名を返り値とします．
		 *
		 * @return String
		 */
		public String getYmlName() {
			return this.name().toLowerCase() + ".yml";
		}

		/**
		 * ManagerClassからテーブル名を取得します．存在しない場合はexampleを返します．
		 *
		 * @param ManagerClass
		 * @return TableName
		 */
		public static String getTableNamebyClass(
				Class<? extends YmlManager> _class) {
			for (ManagerType ye : ManagerType.values()) {
				if (ye.getManagerClass().equals(_class)) {
					return ye.getYmlName();
				}
			}
			return "example";
		}
	}

	// 全てのYmlManager格納するMap
	private LinkedHashMap<Class<? extends GuiMenuManager>, GuiMenuManager> managermap = new LinkedHashMap<Class<? extends GuiMenuManager>, GuiMenuManager>();

	/**
	 * Class Ymlのコンストラクタです． プラグイン始動時に一度だけ呼び出されます．
	 */
	public GuiMenu() {
		managermap.clear();
		// instance作成
		for (ManagerType ye : ManagerType.values()) {
			try {
				managermap.put(ye.managerClass, ye.getManagerClass()
						.getConstructor().newInstance());
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ManagerClassを引数として，そのクラスのインスタンスを返り値とします． Managerを外部から操作したいときに使用します．
	 *
	 * @param managertype
	 * @return <T extends YmlManager>
	 */
	@SuppressWarnings("unchecked")
	public <T extends GuiMenuManager> T getManager(Class<T> type) {
		return (T) managermap.get(type);
	}
}
