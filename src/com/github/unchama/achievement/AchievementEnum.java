package com.github.unchama.achievement;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.bukkit.event.Listener;

import com.github.unchama.achievement.achievements.MineBlockAchievement;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.util.ClassUtil;

public enum AchievementEnum {
	MINEBLOCK(3001,new MineBlockAchievement(10000)),
	;

	private static LinkedHashMap<Integer,GiganticAchievement> idMap = new LinkedHashMap<>();

	//識別No.
	private int id;
	//使用するクラス
	private GiganticAchievement achiv;

	AchievementEnum(int id,GiganticAchievement achiv){
		this.id = id;
		this.achiv = achiv;
	}


	public int getID(){
		return this.id;
	}

	public GiganticAchievement getAchievement(){
		return this.achiv;
	}


	public static Optional<GiganticAchievement> getAchievement(int id){
		return Optional.ofNullable(idMap.get(id));
	}

    public static void registerAll(Gigantic plugin) {
        for (AchievementEnum element : AchievementEnum.values()) {
        	GiganticAchievement achiv = element.getAchievement();
        	idMap.put(element.getID(), achiv);
        	if(ClassUtil.isImplemented(achiv.getClass(), Listener.class)){
        		plugin.getServer().getPluginManager().registerEvents((Listener)achiv,plugin);
        	}
        }
    }
}
