package com.github.unchama.gacha;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bukkit.Bukkit;

import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gui.admin.gacha.gigantic.AdminGiganticGachaMenuManager;
import com.github.unchama.gui.admin.gacha.moduler.AdminGachaMenuManager;
import com.github.unchama.gui.admin.gacha.premium.AdminPremiumGachaMenuManager;
import com.github.unchama.sql.gacha.GiganticGachaTableManager;
import com.github.unchama.sql.gacha.PremiumGachaTableManager;
import com.github.unchama.sql.moduler.GachaTableManager;

public class Gacha {
	public static enum GachaType{
		GIGANTIC(GiganticGachaManager.class,GiganticGachaTableManager.class,AdminGiganticGachaMenuManager.class),
		PREMIUM(PremiumGachaManager.class,PremiumGachaTableManager.class,AdminPremiumGachaMenuManager.class),
		;
		private Class<? extends GachaManager> gm;
		private Class<? extends GachaTableManager> tm;
		private Class<? extends AdminGachaMenuManager> mm;

		private static HashMap<Class<? extends GachaTableManager>,GachaType> tablemap;
		private static HashMap<Class<? extends AdminGachaMenuManager>,GachaType> menumap;

		static{
			tablemap = new HashMap<Class<? extends GachaTableManager>,GachaType>();
			menumap = new HashMap<Class<? extends AdminGachaMenuManager>,GachaType>();
			for(GachaType gt : values()){
				tablemap.put(gt.getTableManagerClass(), gt);
				menumap.put(gt.getMenuManagerClass(), gt);
			}
		}


		GachaType(Class<? extends GachaManager> gm,Class<? extends GachaTableManager> tm,Class<? extends AdminGachaMenuManager> mm){
			this.gm = gm;
			this.tm = tm;
			this.mm = mm;
		}

		public Class<? extends GachaManager> getManagerClass(){
			return gm;
		}
		public Class<? extends GachaTableManager> getTableManagerClass(){
			return tm;
		}
		public Class<? extends AdminGachaMenuManager> getMenuManagerClass(){
			return mm;
		}

		/**TableManagerクラスからManagerクラスを取得します
		 *
		 * @param tm
		 * @return
		 */
		public static GachaType getGachaTypebyTable(Class<? extends GachaTableManager> tm){
			return tablemap.get(tm);
		}
		/**MenuManagerクラスからManagerクラスを取得します
		 *
		 * @param tm
		 * @return
		 */
		public static GachaType getGachaTypebyMenu(Class<? extends AdminGachaMenuManager> mm){
			return menumap.get(mm);
		}

	}

	private LinkedHashMap<Class<? extends GachaManager>,GachaManager> managermap = new LinkedHashMap<Class<? extends GachaManager>,GachaManager>();

	public Gacha(){
		for(GachaType gt : GachaType.values()){
			try {
				this.managermap.put(gt.getManagerClass(),gt.getManagerClass().getConstructor(GachaType.class).newInstance(gt));
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				Bukkit.getServer().getLogger().warning("Failed to create new Instance of gacha:" + gt.name());
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends GachaManager> T getManager(Class<T> type){
		return (T) managermap.get(type);
	}
}
