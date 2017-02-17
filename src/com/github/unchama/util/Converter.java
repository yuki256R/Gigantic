package com.github.unchama.util;

import org.bukkit.entity.Player;

public class Converter {
	/**"true"or"1"と一致したときTRUE，そうでない場合FALSEを返す
	 *
	 * @param s
	 * @return
	 */
	public static Boolean toBoolean(String s){
		Boolean flag;
		if(s.equalsIgnoreCase("true")){
			flag = true;
		}else if(Integer.parseInt(s)==1){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}

	/**プレイヤー型からプレイヤーネームを返す
	 *
	 * @param p
	 * @return
	 */
	public static String toString(Player p){
		return p.getName().toLowerCase();
	}

	/**
	 *
	 * @param _second
	 * @return
	 */
	public static String toTimeString(int _second) {
		int second = _second;
		int minute = 0;
		int hour = 0;
		String time = "";
		while(second >= 60){
			second -=60;
			minute++;
		}
		while(minute >= 60){
			minute -= 60;
			hour++;
		}
		if(hour != 0){
			time = hour + "時間";
		}
		if(minute != 0){
			time = time + minute + "分";
		}
		/*
		if(second != 0){
			time = time + second + "秒";
		}
		*/
		return time;
	}
}
