package com.github.unchama.player.seichiskill.moduler;

import java.util.LinkedHashMap;

import com.github.unchama.gui.moduler.ActiveSkillMenuManager;
import com.github.unchama.gui.seichiskill.active.condensation.CondensationMenuManager;
import com.github.unchama.gui.seichiskill.active.explosion.ExplosionMenuManager;
import com.github.unchama.gui.seichiskill.active.fairyaegis.FairyAegisMenuManager;
import com.github.unchama.gui.seichiskill.active.magicdrive.MagicDriveMenuManager;
import com.github.unchama.gui.seichiskill.active.ruinfield.RuinFieldMenuManager;
import com.github.unchama.player.seichiskill.active.CondensationManager;
import com.github.unchama.player.seichiskill.active.ExplosionManager;
import com.github.unchama.player.seichiskill.active.FairyAegisManager;
import com.github.unchama.player.seichiskill.active.MagicDriveManager;
import com.github.unchama.player.seichiskill.active.RuinFieldManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;

public enum ActiveSkillType {
	EXPLOSION(ExplosionManager.class, ExplosionMenuManager.class,
			Information.EX_COOLTIME), MAGICDRIVE(MagicDriveManager.class,
			MagicDriveMenuManager.class, Information.MD_COOLTIME), CONDENSATION(
			CondensationManager.class, CondensationMenuManager.class), RUINFIELD(
			RuinFieldManager.class, RuinFieldMenuManager.class),FAIRYAEGIS(FairyAegisManager.class,FairyAegisMenuManager.class),
	/*
	 * CONDENSATION(Condensation.class,CondensationMenuManager.class),
	 * RUINFIELD(RuinField.class,RuinFieldMenuManager.class),
	 * FAIRYAEGIS(FairyAegis.class,FairyAegisMenuManager.class),
	 */
	;
	private Class<? extends ActiveSkillManager> skillClass;
	private Class<? extends ActiveSkillMenuManager> menuClass;
	private Information info;

	private static LinkedHashMap<Class<? extends ActiveSkillManager>, ActiveSkillType> skillclassmap = new LinkedHashMap<Class<? extends ActiveSkillManager>, ActiveSkillType>();
	private static LinkedHashMap<Class<? extends ActiveSkillMenuManager>, ActiveSkillType> skillmenuclassmap = new LinkedHashMap<Class<? extends ActiveSkillMenuManager>, ActiveSkillType>();

	ActiveSkillType(Class<? extends ActiveSkillManager> skillClass,
			Class<? extends ActiveSkillMenuManager> menuClass) {
		this(skillClass, menuClass, null);
	}

	ActiveSkillType(Class<? extends ActiveSkillManager> skillClass,
			Class<? extends ActiveSkillMenuManager> menuClass, Information info) {
		this.skillClass = skillClass;
		this.menuClass = menuClass;
		this.info = info;
	}

	static {
		for (ActiveSkillType st : ActiveSkillType.values()) {
			skillclassmap.put(st.getSkillClass(), st);
			skillmenuclassmap.put(st.getMenuClass(), st);
		}
	}

	/**
	 * スキルを管理するクラスを取得します．
	 *
	 * @return
	 */
	public Class<? extends ActiveSkillManager> getSkillClass() {
		return skillClass;
	}

	/**
	 * スキル専用メニューを取得します．
	 *
	 * @return
	 */
	public Class<? extends ActiveSkillMenuManager> getMenuClass() {
		return this.menuClass;
	}

	public static ActiveSkillType getActiveSkillTypebySkillClass(
			Class<? extends ActiveSkillManager> clazz) {
		return skillclassmap.get(clazz);
	}

	public Information getInformation() {
		return this.info;
	}


}
