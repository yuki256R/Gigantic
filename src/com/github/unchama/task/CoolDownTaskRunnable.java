package com.github.unchama.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.player.skill.moduler.SkillManager;
import com.github.unchama.player.skill.moduler.SkillType;

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

	SkillManager m;
	SideBarManager Sm;

	Information info;

	int cooltime;
	SkillType st;
	//■の数
	int snum = 10;
	//一度に減る棒の数
	int dnum;
	//棒が減るtick数（倍数）
	int interval;

	public CoolDownTaskRunnable(GiganticPlayer gp ,int cooltime,SkillType st){
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
	/*
	public void runtestCoolDownTask(int num) {
		cooltime = this.getCooldown(num);
		//5tick未満だった場合クールダウン無し
		if (cooltime > 5) {
			cooldown = true;
			int interval = cooltime/10;
			//11個分の■を表示
			coolstring = 11;
			//指定されたクールタイム経過後クールダウンを解除
			Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,
					new Runnable() {
						@Override
						public void run() {
							Bukkit.getScheduler().runTask(plugin,
									new Runnable() {
										@Override
										public void run() {
											Sm.updateInfo(Information.EX_COOLTIME,getCoolString(coolstring));
											coolstring--;
											if(coolstring < 1)this.cancel();
										}
									});
						}
					}, 0, interval);

		}
	}
	private String getCoolString(
			int coolstring) {
		String cs = "";
		for(int i = 0 ; i < coolstring ; i++){
			cs += "■";
		}
		return cs;
	}*/

}
