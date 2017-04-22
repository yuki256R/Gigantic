package com.github.unchama.player.seichiskill.moduler;

import java.util.LinkedHashMap;

import com.github.unchama.gui.moduler.SkillMenuManager;
import com.github.unchama.gui.skillmenu.condensation.CondensationMenuManager;
import com.github.unchama.gui.skillmenu.explosion.ExplosionMenuManager;
import com.github.unchama.gui.skillmenu.magicdrive.MagicDriveMenuManager;
import com.github.unchama.player.seichiskill.CondensationManager;
import com.github.unchama.player.seichiskill.ExplosionManager;
import com.github.unchama.player.seichiskill.MagicDriveManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;

public enum SkillType {
	EXPLOSION(ExplosionManager.class, ExplosionMenuManager.class,
			Information.EX_COOLTIME), MAGICDRIVE(MagicDriveManager.class,
			MagicDriveMenuManager.class, Information.MD_COOLTIME),
			CONDENSATION(CondensationManager.class,CondensationMenuManager.class)
	/*
	 * CONDENSATION(Condensation.class,CondensationMenuManager.class),
	 * RUINFIELD(RuinField.class,RuinFieldMenuManager.class),
	 * FAIRYAEGIS(FairyAegis.class,FairyAegisMenuManager.class),
	 */
	;
	private Class<? extends SkillManager> skillClass;
	private Class<? extends SkillMenuManager> menuClass;
	private Information info;

	private static LinkedHashMap<Class<? extends SkillManager>, SkillType> skillclassmap = new LinkedHashMap<Class<? extends SkillManager>, SkillType>();
	private static LinkedHashMap<Class<? extends SkillMenuManager>, SkillType> skillmenuclassmap = new LinkedHashMap<Class<? extends SkillMenuManager>, SkillType>();

	SkillType(Class<? extends SkillManager> skillClass,
			Class<? extends SkillMenuManager> menuClass) {
		this(skillClass,menuClass,null);
	}

	SkillType(Class<? extends SkillManager> skillClass,
			Class<? extends SkillMenuManager> menuClass, Information info) {
		this.skillClass = skillClass;
		this.menuClass = menuClass;
		this.info = info;
	}

	static {
		for (SkillType st : SkillType.values()) {
			skillclassmap.put(st.getSkillClass(), st);
			skillmenuclassmap.put(st.getMenuClass(), st);
		}
	}

	/**
	 * スキルを管理するクラスを取得します．
	 *
	 * @return
	 */
	public Class<? extends SkillManager> getSkillClass() {
		return skillClass;
	}

	/**
	 * スキル専用メニューを取得します．
	 *
	 * @return
	 */
	public Class<? extends SkillMenuManager> getMenuClass() {
		return this.menuClass;
	}

	public static SkillType getSkillTypebySkillClass(
			Class<? extends SkillManager> clazz) {
		return skillclassmap.get(clazz);
	}

	public Information getInformation() {
		return this.info;
	}

}
