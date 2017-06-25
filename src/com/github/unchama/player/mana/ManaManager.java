package com.github.unchama.player.mana;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Finalizable;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.sql.player.ManaTableManager;
import com.github.unchama.util.Util;

/**
 * @author tar0ss
 *
 */
public class ManaManager extends DataManager implements Initializable, UsingSql, Finalizable{

	private double m;
	private double max;
	BossBar manabar;
	ManaTableManager tm;
	SeichiLevelManager Sm;

	//デバッグ時のマナ保存用
	private double debugmana = -1;

	public ManaManager(GiganticPlayer gp) {
	    super(gp);
        tm = sql.getManager(ManaTableManager.class);
	}

	@Override
	public void init() {
		Sm = gp.getManager(SeichiLevelManager.class);
		this.updateMaxMana(Sm.getLevel());
		Player player = PlayerManager.getPlayer(gp);
		display(player);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	@Override
	public void fin() {
		if(this.debugmana != -1){
			this.m = this.debugmana;
		}
		this.removeBar();
	}
	/**
	 * 現在マナをマナバーに表示します
	 *
	 * @param player
	 * @param level
	 */
	public void display(Player player) {
		if (manabar != null)
			removeBar();

		if (max == 0)
			return;

		double progress = 0;
		if (m / max > 1.0) {
			m = max;
		}
		progress = m / max;

		BarColor bc = getColor(progress);

		manabar = Bukkit.getServer().createBossBar(
				ChatColor.AQUA + "" + ChatColor.BOLD + "マナ(" + Util.Decimal(m)
						+ "/" + max + ")", bc, BarStyle.SOLID);
		manabar.setProgress(progress);
		manabar.addPlayer(player);
	}
	/**最大マナに占める現在マナの割り合いを更新します．
	 * 現在マナが最大マナを超えている場合1となります．
	 * @return
	 */
	private void updateBar(){
		double progress =  m / max > 1.0 ? 1.0 : m / max;
		manabar.setProgress(progress);
		manabar.setTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "マナ(" + Util.Decimal(m)
				+ "/" + max + ")");
		BarColor bc = getColor(progress);

		manabar.setColor(bc);



	}
	private BarColor getColor(double progress) {
		if(progress > 0.99){
			//マックス表示
			return BarColor.PURPLE;
		}else if(progress > 0.1){
			//通常表示
			return BarColor.BLUE;
		}else if(progress > 0.01){
			//ピンク表示
			return BarColor.PINK;
		}else{
			//赤表示
			return BarColor.RED;
		}
	}

	/**iだけマナを増加させます．maxを超えた場合はmaxとなります．
	 *
	 * @param i
	 * @param level
	 */
	public void increase(double i){
		this.m += i;
		if(m > max) m = max;
		this.updateBar();
	}
	/**dだけマナを減少させます．０以下にはなりません．
	 *
	 * @param d
	 */
	public void decrease(double d){
		this.m -= d;
		if(m < 0) m = 0;
		this.updateBar();
	}
	/**hのマナを持っている時はtrueを出力します．
	 *
	 * @param h
	 * @return
	 */
	public boolean hasMana(double h){
		return m >= h ? true : false;
	}
	/**
	 * 現在のバーを削除します
	 */
	public void removeBar() {
		try {
			manabar.removeAll();
		} catch (NullPointerException e) {
			Bukkit.getLogger().warning("manabarがNullです.");
		}
	}

	/**
	 * 現在マナを取得します．
	 *
	 * @return
	 */
	public double getMana() {
		return this.m;
	}

	/**
	 * 現在マナを設定します．
	 *
	 * @param mana
	 */
	public void setMana(double mana) {
		this.m = mana;
	}

	/**マナ値を最大マナにします．（全回復）
	 *
	 */
	public void fullMana(){
		this.m = this.max;
	}

	/**
	 * 現在のレベルに応じてMaxManaを変更します．
	 *
	 */
	public void updateMaxMana(int level) {
		this.max = SeichiLevelManager.levelmap.get(level).getMaxMana();
	}

	/**ユーザーのレベルアップ時に実行します．
	 *
	 */
	public void Levelup(int level){
		this.updateMaxMana(level);
		this.fullMana();
		Player player = PlayerManager.getPlayer(gp);
		this.display(player);
	}

	/**デバッグ用マナ保存メソッドです．
	 *
	 */
	public void setDebugMana(){
		this.updateMaxMana(Sm.getLevel());
		if(this.debugmana == -1){
			this.debugmana = m;
		}
		this.fullMana();
		Player player = PlayerManager.getPlayer(gp);
		this.display(player);
	}


}
