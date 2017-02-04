package com.github.unchama.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.github.unchama.gigantic.Gigantic;

public class Sql {
	private Gigantic plugin;
	private final String url;
	private final String db;
	private final String id;
	private final String pw;

	public static Connection con = null;
	private Statement stmt = null;

	private ResultSet rs = null;

	//コンストラクタ
	public Sql(Gigantic plugin ,String url, String db, String id, String pw){
		this.plugin = plugin;
		this.url = url;
		this.db = db;
		this.id = id;
		this.pw = pw;
	}


	/**
	 * 接続関数
	 *
	 * @param url 接続先url
	 * @param id ユーザーID
	 * @param pw ユーザーPW
	 * @param db データベースネーム
	 * @param table テーブルネーム
	 * @return
	 */

}
