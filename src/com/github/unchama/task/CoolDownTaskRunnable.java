package com.github.unchama.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;

/**クールダウンを管理するタスク
 * 必ず下の形で起動させること
 * new CoolDownTaskRunnable(this,cooldown,st).runTaskTimerAsynchronously(plugin, 0, 1);
 *
 * @author tar0ss
 *
 */
public class CoolDownTaskRunnable extends BukkitRunnable{
	Gigantic plugin = Gigantic.plugin;
	GiganticPlayer gp;

	ActiveSkillManager m;
	SideBarManager Sm;

	Information info;

	int cooltime;
	ActiveSkillType st;
	//■の数
	int snum = 10;
	//一度に減る棒の数
	int dnum;
	//棒が減るtick数（倍数）
	int interval;

	public CoolDownTaskRunnable(GiganticPlayer gp ,int cooltime,ActiveSkillType st){
		this.gp = gp;
		m = gp.getManager(st.getSkillClass());
		Sm = gp.getManager(SideBarManager.class);
		info = st.getInformation();
		this.cooltime = cooltime;;
		this.st = st;
		m.setCoolDown(true);
		dnum = getDecreaseNum();
		interval = cooltime/10;
	}


	/**
	 * 一度に減る棒の数を取得します
	 * @return
	 */
	private int getDecreaseNum() {
		if(cooltime < 10){
			dnum = 2;
		}else{
			dnum = 1;
		}
		return dnum;
	}


	@Override
	public void run() {
		Bukkit.getScheduler().runTask(plugin, new Runnable(){

			@Override
			public void run() {
				Sm.updateInfo(info, getCoolTimeString(snum));
				Sm.refresh();

				cooltime--;
				if(cooltime % interval == 0){
					snum -= dnum;
				}
				if(snum < 0){
					snum = 0;
				}
				if(cooltime < 0){
					m.setCoolDown(false);
					m.playCoolTimeFinishSound();
					cancel();
					return;
				}
			}



		});
	}
	private String getCoolTimeString(int snum) {
		String cs = "";
		for(int i = 0 ; i < snum ; i++){
			cs += "■";
		}

		if(cs.equals("")){
			cs = "%DELETE%";
		}
		return cs;
	}

}
