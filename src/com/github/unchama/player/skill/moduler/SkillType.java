package com.github.unchama.player.skill.moduler;



import com.github.unchama.gui.moduler.SkillMenuManager;
import com.github.unchama.gui.skillmenu.explosion.ExplosionMenuManager;
import com.github.unchama.player.skill.ExplosionManager;

public enum SkillType {
	EXPLOSION(ExplosionManager.class,ExplosionMenuManager.class),
	/*
	MAGICDRIVE(MagicDrive.class,MagicDriveMenuManager.class),
	CONDENSATION(Condensation.class,CondensationMenuManager.class),
	RUINFIELD(RuinField.class,RuinFieldMenuManager.class),
	FAIRYAEGIS(FairyAegis.class,FairyAegisMenuManager.class),*/
	;
	private Class<? extends SkillManager> skillClass;
	private Class<? extends SkillMenuManager> menuClass;

	SkillType(Class<? extends SkillManager> skillClass,Class<? extends SkillMenuManager> menuClass) {
		this.skillClass = skillClass;
		this.menuClass = menuClass;
	}

	/**スキルを管理するクラスを取得します．
	 *
	 * @return
	 */
	public Class<? extends SkillManager> getSkillClass() {
		return skillClass;
	}

	/**スキル専用メニューを取得します．
	 *
	 * @return
	 */
	public Class<? extends SkillMenuManager> getMenuClass(){
		return this.menuClass;
	}

}
