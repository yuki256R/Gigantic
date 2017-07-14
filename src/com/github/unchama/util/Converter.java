package com.github.unchama.util;

import java.util.Arrays;
import java.util.BitSet;

import org.bukkit.entity.Player;

/**
 * @author tar0ss
 *
 */
public class Converter {
	/**
	 * "true"or"1"と一致したときTRUE，そうでない場合FALSEを返す
	 *
	 * @param s
	 * @return
	 */
	public static Boolean toBoolean(String s) {
		Boolean flag;
		if (s.equalsIgnoreCase("true")) {
			flag = true;
		} else if (s.equalsIgnoreCase("1")) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public static float toFloat(String s) throws NumberFormatException {
		return Float.valueOf(s);
	}

	public static int toInt(String s) throws NumberFormatException {
		return Integer.valueOf(s);
	}

	public static short toShort(String s) throws NumberFormatException {
		return Short.valueOf(s);
	}

	public static long toLong(String s) throws NumberFormatException {
		return Long.valueOf(s);
	}

	public static String getName(Player p) {
		return p.getName().toLowerCase();// 全て小文字に
	}

	public static String getName(String name) {
		return name.toLowerCase();// 小文字に
	}

	public static long SecondtoTick(int sec){
		return sec * 20;
	}
	public static long MinutetoTick(int min){
		return SecondtoTick(min * 60);
	}
	public static long HourtoTick(int h){
		return MinutetoTick(h * 60);
	}
	public static long DaytoTick(int d){
		return HourtoTick(d * 24);
	}
	public static long WeektoTick(int w){
		return DaytoTick(w * 7);
	}
	public static long YeartoTick(int y){
		return DaytoTick(y * 365);
	}


	/**与えられたtick数から時間表示を返します
	 *
	 * @param _tick
	 * @return "〇時間〇分〇秒〇"
	 */
	public static String toTimeString(long _tick) {
		long tick = _tick;
		long second = 0;
		long minute = 0;
		long hour = 0;
		String time = "";
		while (tick >= 20) {
			tick -= 20;
			second++;
		}
		while (second >= 60) {
			second -= 60;
			minute++;
		}
		while (minute >= 60) {
			minute -= 60;
			hour++;
		}
		if (hour != 0) {
			time += hour + "時間";
		}
		if (minute != 0) {
			time += minute + "分";
		}
		if (second != 0) {
			time += second + "秒";
		}
		if (tick != 0) {
			time += "" + tick;
		}

		if (time.equals("")) {
			time = "なし";
		}

		return time;
	}

	public static double toDouble(String s) throws NumberFormatException {
		return Double.valueOf(s);
	}

	public static String toString(BitSet set) {
		long[] lArray = set.toLongArray();
		String[] sArray = Arrays.stream(lArray).mapToObj(Long::toHexString).toArray(String[]::new);
		String str = String.join(",", sArray);
		return str;
	}

	public static BitSet toBitSet(String str) {
		String[] sAr = str.split(",");
		long[] lAr = Arrays.stream(sAr).mapToLong(x -> Long.parseUnsignedLong(x, 16)).toArray();
		BitSet set = BitSet.valueOf(lAr);
		return set;
	}

}
