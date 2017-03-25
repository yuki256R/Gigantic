package com.github.unchama.player.skill;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import com.github.unchama.gui.moduler.SkillMenuManager;
import com.github.unchama.gui.skillmenu.condendation.CondensationMenuManager;
import com.github.unchama.gui.skillmenu.explosion.ExplosionMenuManager;
import com.github.unchama.gui.skillmenu.fairyaegis.FairyAegisMenuManager;
import com.github.unchama.gui.skillmenu.magicdrive.MagicDriveMenuManager;
import com.github.unchama.gui.skillmenu.ruinfield.RuinFieldMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.skill.moduler.Skill;
import com.github.unchama.sql.SkillTableManager;

public class SkillManager extends DataManager implements UsingSql {
	public static enum SkillType {
		EXPLOSION(Explosion.class,ExplosionMenuManager.class),
		MAGICDRIVE(MagicDrive.class,MagicDriveMenuManager.class),
		CONDENSATION(Condensation.class,CondensationMenuManager.class),
		RUINFIELD(RuinField.class,RuinFieldMenuManager.class),
		FAIRYAEGIS(FairyAegis.class,FairyAegisMenuManager.class),
		;
		private Class<? extends Skill> skillClass;
		private Class<? extends SkillMenuManager> menuClass;

		SkillType(Class<? extends Skill> skillClass,Class<? extends SkillMenuManager> menuClass) {
			this.skillClass = skillClass;
			this.menuClass = menuClass;
		}

		/**スキルを管理するクラスを取得します．
		 *
		 * @return
		 */
		public Class<? extends Skill> getSkillClass() {
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

	SkillTableManager tm;

	private LinkedHashMap<Class<? extends Skill>, Skill> skillmap = new LinkedHashMap<Class<? extends Skill>, Skill>();

	public SkillManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(SkillTableManager.class);
		for (SkillType st : SkillType.values()) {
			try {
				skillmap.put(st.getSkillClass(), st.getSkillClass().getConstructor(GiganticPlayer.class).newInstance(gp));
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				plugin.getLogger().warning("Failed to create new skill(" + st.name().toLowerCase() + ") Instance of player:" + gp.name);
				e.printStackTrace();
			}
		}
	}

	/**与えられたクラスのスキルデータを返します
	 *
	 * @param skillClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Skill> T getSkill(Class<T> skillClass){
		return (T) skillmap.get(skillClass);
	}


	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}




}
