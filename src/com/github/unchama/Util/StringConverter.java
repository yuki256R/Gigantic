package com.github.unchama.Util;

public class StringConverter {

	//"true"or"1"と一致したときTRUE，そうでない場合FALSEを返す
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
}
