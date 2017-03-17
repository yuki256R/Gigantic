package com.github.unchama.player.skill;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import org.bukkit.inventory.Inventory;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.skill.moduler.Skill;
import com.github.unchama.sql.SkillTableManager;

public class SkillManager extends DataManager implements UsingSql {
	public static enum SkillType {
		EXPLOSION(Explosion.class),
		MAGICDRIVE(MagicDrive.class),
		CONDENSATION(Condensation.class),
		RUINFIELD(RuinField.class),
		FAIRYAEGIS(FairyAegis.class),
		;
		private Class<? extends Skill> skillClass;

		SkillType(Class<? extends Skill> skillClass) {
			this.skillClass = skillClass;
		}

		public Class<? extends Skill> getSkillClass() {
			return skillClass;
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
	 * @param skilltype
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Skill> T getSkill(Class<T> skilltype){
		return (T) skillmap.get(skilltype);
	}


	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, true);
	}

	/**スキルタイプ選択メニューを開きます．
	 *
	 * @return
	 */
	public static Inventory getSkillTypeMenu(){
		return null;
	}



}
