package com.github.unchama.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

public final class TimeUtil {
	public static Calendar getCalendar(){
		return Calendar.getInstance();
	}
	public static Date getCurrentDate(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	public static String format(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
	public static String format(Date d,String s){
		SimpleDateFormat sdf = new SimpleDateFormat(s);
		return sdf.format(d);
	}
	public static String getDateTimeOnString(TimeType tt,int i) {
		Calendar cal = Calendar.getInstance();
		switch(tt){
		case DAY:
			cal.add(Calendar.DATE, i);
			break;
		case WEEK:
			cal.add(Calendar.DATE, i*7);
			int weeknum = cal.get(Calendar.WEEK_OF_MONTH);
			cal.set(Calendar.DATE, (weeknum-1) * 7);
			break;
		case MONTH:
			cal.add(Calendar.MONTH, i);
			cal.set(Calendar.DATE, 1);
			break;
		case YEAR:
			cal.add(Calendar.YEAR, i);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 00:00:00'");
		return sdf.format(cal.getTime());
	}
	public static String getDateTimeName(TimeType tt, int i) {
		Calendar cal = Calendar.getInstance();
		switch(tt){
		case DAY:
			cal.add(Calendar.DATE, i);
			break;
		case WEEK:
			cal.add(Calendar.DATE, i*7);
			int weeknum = cal.get(Calendar.WEEK_OF_MONTH);
			cal.set(Calendar.DATE, (weeknum-1) * 7);
			break;
		case MONTH:
			cal.add(Calendar.MONTH, i);
			cal.set(Calendar.DATE, 1);
			break;
		case YEAR:
			cal.add(Calendar.YEAR, i);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(cal.getTime());
	}
}
