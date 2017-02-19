package com.github.unchama.sql;

import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;


public class GiganticTableManager extends PlayerTableManager{

	public GiganticTableManager(Sql sql){
		super(sql);
	}

	@Override
	public Boolean save(GiganticPlayer gp) {
		//String command = "";
		//final String struuid = gp.uuid.toString().toLowerCase();

		//no data that should be saved
		return true;
	}

	@Override
	String addOriginalColumn() {
		//loginflag??
		return null;
	}

	@Override
	void insertNewPlayer(GiganticPlayer gp) {
			/*
			//初見さんにLv1メッセージを送信
			p.sendMessage(SeichiAssist.config.getLvMessage(1));
			//初見さんであることを全体告知
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "【初見キタ】" + p.getName() + "のプレイヤーデータ作成完了");
			Util.sendEveryMessage(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+name+"さんは初参加です。整地鯖へヨウコソ！" + ChatColor.RESET +" - " + ChatColor.YELLOW + ChatColor.UNDERLINE +  "http://seichi.click");
			Util.sendEverySound(Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			//初見プレイヤーに木の棒、エリトラ、ピッケルを配布
			p.getInventory().addItem(new ItemStack(Material.STICK));
			p.getInventory().addItem(new ItemStack(Material.ELYTRA));
			p.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
			p.getInventory().addItem(new ItemStack(Material.DIAMOND_SPADE));
			MebiusListener.give(p);
			*/
		
	}

	@Override
	void loadPlayer(GiganticPlayer gp) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	String savePlayer(GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


}
