package com.github.unchama.player.home;

import javax.xml.bind.DatatypeConverter;

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

	public Location[] homepoint = new Location[config.getSubHomeMax()];
	private String[] homename = new String[config.getSubHomeMax()];
	private boolean changename;
	private int homenum;
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
			if(x >= 0 & x < config.getSubHomeMax()){
				this.homepoint[x] = l;
			}
		}

		//ホームポイントを取得する
		public Location getHomePoint(int x) {
			if(x >= 0 & x < config.getSubHomeMax()){
				return this.homepoint[x];
			}else{
				return null;
			}
		}

		//文字列からデータを読み込む（DB用）
		public void SetHome(String str){
			String[] s = str.split(",", -1);
			for( int x = 0 ; x < config.getSubHomeMax() ; x++){
				if (s.length < x*4+3){
					break;
				}
				if(s[x*4].length() > 0 && s[x*4+1].length() > 0 && s[x*4+2].length() > 0 && s[x*4+3].length() > 0 ){

					Location l = new Location( Bukkit.getWorld(s[x*4+3]) , Integer.parseInt(s[x*4]) , Integer.parseInt(s[x*4+1]) , Integer.parseInt(s[x*4+2]) );
					this.homepoint[x] = l;
				}
			}
		}

		//ホームデータを文字列で返す（DB用）
		public String HomeToString(){
			String s = "";
			for( int x = 0 ; x < config.getSubHomeMax() ; x++){
				if (this.homepoint[x] == null || homepoint[x].getWorld() == null){
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

		public int getHomeNum() {
			return homenum;
		}

		public int setHomeNum(int homenum) {
			return this.homenum = homenum;
		}

	//ホームネーム関連

		//ホームの名前を設定する
		public void setHomeName(String homename, int x){
			if(x >= 0 & x < config.getSubHomeMax())
				this.homename[x] = homename;
		}

		//ホームの名前を取得する
		public String getHomeName(int x){
			if(x >= 0 & x < config.getSubHomeMax()){
				return this.homename[x];
			}else{
				return null;
			}
		}

		//DB用,16進文字列から名前を読み込む
		public void setName(String str){
			String[] s = str.split(",", 0);
			for( int x = 0 ; x < config.getSubHomeMax() ; x++){
				String decText = new String(DatatypeConverter.parseHexBinary(s[x]));
				this.homename[x] = decText;
			}
		}

		//DB用,名前を16進文字列に変換してから返す
		public String getName(){
			String s = "";
			for( int x = 0 ; x < config.getSubHomeMax() ; x++){

				// 名前の文字列からUTF8のバイト列に変換
				byte[] data = this.homename[x].getBytes();
				// 16進文字列に変換
				String hexText = DatatypeConverter.printHexBinary(data);

				s += hexText + ",";
			}
		    return s;
		}

		//名付けイベントを呼び出すかどうか
		public boolean getChangeName() {
			return this.changename;
		}

		public boolean setChangeName(boolean changename) {
			return this.changename = changename;
		}
}