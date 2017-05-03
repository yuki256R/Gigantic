package com.github.unchama.player.time;

import org.bukkit.Location;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.PlayerTimeTableManager;
import com.github.unchama.util.Util;

public class PlayerTimeManager extends DataManager implements UsingSql {

	//プレイ時間差分計算用int
	public int servertick;
	//プレイ時間
	public int playtick;

	//現在座標
	public Location loc;
	//累計放置時間
	public int totalidletick;
	//放置時間
	public int idletime;

	PlayerTimeTableManager tm = sql.getManager(PlayerTimeTableManager.class);

	public PlayerTimeManager(GiganticPlayer gp) {
		super(gp);
	}

    @Override
    public void save(Boolean loginflag) {
        tm.save(gp, loginflag);
    }

	//10分間動きがなければ放置
	public boolean isIdle(){
		return idletime >= 10;
	}

	//放置時間を除いた累計プレイ時間を「HH時間MM分」の文字列で返す
	public String GetTotalLoginTimeToString(){
		return Util.toTimeString(Util.toSecond(playtick - totalidletick));
	}
}
