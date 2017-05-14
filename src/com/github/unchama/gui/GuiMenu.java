package com.github.unchama.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import com.github.unchama.gui.admin.AdminTypeMenuManager;
import com.github.unchama.gui.admin.customhead.AdminCustomHeadGiveMenuManager;
import com.github.unchama.gui.admin.gacha.AdminGachaTypeMenuManager;
import com.github.unchama.gui.admin.gacha.gigantic.AG_AppleGachaMenuManager;
import com.github.unchama.gui.admin.gacha.gigantic.AG_EditGachaMenuManager;
import com.github.unchama.gui.admin.gacha.gigantic.AG_ListGachaMenuManager;
import com.github.unchama.gui.admin.gacha.gigantic.AG_TicketGachaMenuManager;
import com.github.unchama.gui.admin.gacha.gigantic.AdminGiganticGachaMenuManager;
import com.github.unchama.gui.admin.gacha.premium.AP_AppleGachaMenuManager;
import com.github.unchama.gui.admin.gacha.premium.AP_EditGachaMenuManager;
import com.github.unchama.gui.admin.gacha.premium.AP_ListGachaMenuManager;
import com.github.unchama.gui.admin.gacha.premium.AP_TicketGachaMenuManager;
import com.github.unchama.gui.admin.gacha.premium.AdminPremiumGachaMenuManager;
import com.github.unchama.gui.build.BlockCraftMenuManagerFirstPage;
import com.github.unchama.gui.build.BlockCraftMenuManagerSecondPage;
import com.github.unchama.gui.build.BlockCraftMenuManagerThirdPage;
import com.github.unchama.gui.huntingpoint.HuntingPointMainMenuManager;
import com.github.unchama.gui.huntingpoint.HuntingPointShopMenuManager;
import com.github.unchama.gui.minestack.StackCategoryMenuManager;
import com.github.unchama.gui.minestack.build.CategoryBuildMenuManager;
import com.github.unchama.gui.minestack.item.CategoryItemMenuManager;
import com.github.unchama.gui.minestack.material.CategoryMaterialMenuManager;
import com.github.unchama.gui.minestack.otherwise.CategoryOtherwiseMenuManager;
import com.github.unchama.gui.minestack.redstone.CategoryRedstoneMenuManager;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.gui.seichireward.SeichiRewardMenuManager;
import com.github.unchama.gui.seichiskill.active.ActiveSkillToggleMenuManager;
import com.github.unchama.gui.seichiskill.active.ActiveSkillTypeMenuManager;
import com.github.unchama.gui.seichiskill.active.condensation.C_OriginMenuManager;
import com.github.unchama.gui.seichiskill.active.condensation.C_RangeMenuManager;
import com.github.unchama.gui.seichiskill.active.condensation.CondensationMenuManager;
import com.github.unchama.gui.seichiskill.active.explosion.E_RangeMenuManager;
import com.github.unchama.gui.seichiskill.active.explosion.ExplosionMenuManager;
import com.github.unchama.gui.seichiskill.active.fairyaegis.F_RangeMenuManager;
import com.github.unchama.gui.seichiskill.active.fairyaegis.FairyAegisMenuManager;
import com.github.unchama.gui.seichiskill.active.magicdrive.MD_RangeMenuManager;
import com.github.unchama.gui.seichiskill.active.magicdrive.MagicDriveMenuManager;
import com.github.unchama.gui.seichiskill.active.ruinfield.R_OriginMenuManager;
import com.github.unchama.gui.seichiskill.active.ruinfield.R_RangeMenuManager;
import com.github.unchama.gui.seichiskill.active.ruinfield.RuinFieldMenuManager;
import com.github.unchama.gui.seichiskill.passive.PassiveSkillTypeMenuManager;
import com.github.unchama.player.build.BuildMenuManager;


public final class GuiMenu {
	public static enum ManagerType {
		MAINMENU(MainMenuManager.class),
		ACTIVESKILLTYPEMENU(ActiveSkillTypeMenuManager.class),
		ACTIVESKILLTOGGLEMENU(ActiveSkillToggleMenuManager.class),
		EXPLOSIONMENU(ExplosionMenuManager.class),
		E_RANGEMENU(E_RangeMenuManager.class),
		MD_RANGEMENU(MD_RangeMenuManager.class),
		MAGICDRIVEMENU(MagicDriveMenuManager.class),
		CONDENSATIONMENU(CondensationMenuManager.class),
		C_RANGEMENU(C_RangeMenuManager.class),
		C_ORIGINMENU(C_OriginMenuManager.class),
		RUINFIELDMENU(RuinFieldMenuManager.class),
		R_RANGEMENU(R_RangeMenuManager.class),
		R_ORIGINMENU(R_OriginMenuManager.class),
		FAIRYAEGISMENU(FairyAegisMenuManager.class),
		F_RANGEMENU(F_RangeMenuManager.class),
		PASSIVESKILLTYPEMENU(PassiveSkillTypeMenuManager.class),
		MINESTACKCATEGORYMENU(StackCategoryMenuManager.class),
		BUILDCATEGORYMENU(CategoryBuildMenuManager.class),
		ITEMCATEGORYMENU(CategoryItemMenuManager.class),
		REDSTONECATEGORYMENU(CategoryRedstoneMenuManager.class),
		MATERIALCATEGORYMENU(CategoryMaterialMenuManager.class),
		OTHERWISECATEGORYMENU(CategoryOtherwiseMenuManager.class),
		BUILDMENU(BuildMenuManager.class),
		REGIONMENU(RegionMenuManager.class),
		HUNTMAINMENU(HuntingPointMainMenuManager.class),
		HUNTSHOPMENU(HuntingPointShopMenuManager.class),
		ADMINTYPEMENU(AdminTypeMenuManager.class),
		ADMINGACHATYPEMENU(AdminGachaTypeMenuManager.class),
		ADMINGIGANTICGACHAMENU(AdminGiganticGachaMenuManager.class),
		AG_LISTGACHAMENU(AG_ListGachaMenuManager.class),
		AG_EDITGACHAMENU(AG_EditGachaMenuManager.class),
		AG_TICKETGACHAMENU(AG_TicketGachaMenuManager.class),
		AG_APPLEGACHAMENU(AG_AppleGachaMenuManager.class),
		ADMINPREMIUMGACHAMENU(AdminPremiumGachaMenuManager.class),
		AP_LISTGACHAMENU(AP_ListGachaMenuManager.class),
		AP_EDITGACHAMENU(AP_EditGachaMenuManager.class),
		AP_TICKETGACHAMENU(AP_TicketGachaMenuManager.class),
		AP_APPLEGACHAMENU(AP_AppleGachaMenuManager.class),
		ADMINCUSTOMHEADGIBEMENU(AdminCustomHeadGiveMenuManager.class),
		BLOCKCRAFTMENUFIRST(BlockCraftMenuManagerFirstPage.class),
		BLOCKCRAFTMENUSECOND(BlockCraftMenuManagerSecondPage.class),
		BLOCKCRAFTMENUTHIRD(BlockCraftMenuManagerThirdPage.class),
		SEICHIREWARDMENU(SeichiRewardMenuManager.class),
		;

		// 使用するManagerClass
		private Class<? extends GuiMenuManager> managerClass;

		private static LinkedHashMap<Class<? extends GuiMenuManager>, ManagerType> managertypemap = new LinkedHashMap<Class<? extends GuiMenuManager>, ManagerType>();

		// Enum用コンストラクタ
		ManagerType(Class<? extends GuiMenuManager> managerClass) {
			this.managerClass = managerClass;
		}

		static{
			for(ManagerType mt : ManagerType.values()){
				managertypemap.put(mt.getManagerClass(), mt);
			}
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
		public static String getMenuNamebyClass(
				Class<? extends GuiMenuManager> _class) {
			for (ManagerType ye : ManagerType.values()) {
				if (ye.getManagerClass().equals(_class)) {
					return ye.getYmlName();
				}
			}
			return "example";
		}


		/**
		 * ManagerClassからmanagerTypeを取得します．
		 * @param _class
		 * @return
		 */
		public static ManagerType getTypebyClass(
				Class<? extends GuiMenuManager> _class){
			return managertypemap.get(_class);
		}
	}



	// 全てのGuiMenuManagerを格納するMap
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
