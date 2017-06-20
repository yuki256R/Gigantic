package com.github.unchama.player.home;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.HomeTableManager;

/**
 *
 * @author yuki_256
 *
 */
public class HomeManager extends DataManager implements UsingSql {

	public static Location[] homepoint = new Location[config.getSubHomeMax() + (10 * (config.getHomeNum()-1))];
	HomeTableManager tm;

	public HomeManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(HomeTableManager.class);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	//ホームポイント関連

		//ホームポイントを設定する
		public void setHomePoint(Location l, int x) {
			if(x >= 0 & x < config.getSubHomeMax() + 10 * (config.getServerNum()-1)){
				HomeManager.homepoint[x] = l;
			}
		}

		//ホームポイントを取得する
		public Location getHomePoint(int x) {
			if(x >= 0 & x < config.getSubHomeMax() + 10 * (config.getServerNum()-1)){
				return HomeManager.homepoint[x];
			}else{
				return null;
			}
		}

		//文字列からデータを読み込む（DB用）
		public void SetHome(String str){
			String[] s = str.split(",", -1);
			for( int x = 0 ; x < config.getSubHomeMax() + (10 * (config.getHomeNum()-1)) ; x++){
				if (s.length < x*4+3){
					break;
				}
				if(s[x*4].length() > 0 && s[x*4+1].length() > 0 && s[x*4+2].length() > 0 && s[x*4+3].length() > 0 ){

					Location l = new Location( Bukkit.getWorld(s[x*4+3]) , Integer.parseInt(s[x*4]) , Integer.parseInt(s[x*4+1]) , Integer.parseInt(s[x*4+2]) );
					HomeManager.homepoint[x] = l;
				}
			}
		}

		//ホームデータを文字列で返す（DB用）
		public String HomeToString(){
			String s = "";
			for( int x = 0 ; x < config.getSubHomeMax() + (10 * (config.getHomeNum()-1)) ; x++){
				if (HomeManager.homepoint[x] == null || homepoint[x].getWorld() == null){
					//設定されてない場合
					s += ",,,,";
				}else{
					//設定されてる場合
					s += String.valueOf( (int)homepoint[x].getX() ) +",";
					s += String.valueOf( (int)homepoint[x].getY() ) +",";
					s += String.valueOf( (int)homepoint[x].getZ() ) +",";
					s += homepoint[x].getWorld().getName() +",";
				}
			}
			return s;
		}

		//文字列からデータを読み込む（引継ぎ用）
		public void SeichiFromSetHome(String str, int servernum){
			String[] s = str.split(",", -1);
			for( int x = 0 ; x < config.getSubHomeMax() ; x++){
				if (s.length < x*4+3){
					break;
				}
				if(s[x*4].length() > 0 && s[x*4+1].length() > 0 && s[x*4+2].length() > 0 && s[x*4+3].length() > 0 ){

					Location l = new Location( Bukkit.getWorld(s[x*4+3]) , Integer.parseInt(s[x*4]) , Integer.parseInt(s[x*4+1]) , Integer.parseInt(s[x*4+2]) );
					HomeManager.homepoint[x + (10 * (servernum-1))] = l;
				}
			}
		}

	//ホームネーム関連
}