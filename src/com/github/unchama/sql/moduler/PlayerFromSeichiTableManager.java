package com.github.unchama.sql.moduler;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * SeichiAssistからのデータを引き継ぐ場合はPlayerFromSeichiTableManagerを継承してください
 * 通常のプレイヤーデータのセーブ・ロードをする場合はPlayerDataTableを継承してください．
 *
 * @author tar0ss
 *
 */
public abstract class PlayerFromSeichiTableManager extends PlayerTableManager {

	public PlayerFromSeichiTableManager(Sql sql) {
		super(sql);
	}

	/**
	 * テーブル作成時に追加するカラムをコマンドとして返り値としてください． ex) command =
	 * "add column if not exists name varchar(30) default null,"
	 * "add"で始まり，","で終わるようにしてください．
	 *
	 * @return command
	 */
	protected abstract String addColumnCommand();

	/**
	 * データ保存時のコマンドを返り値としてください． ex) for(BlockType bt : datamap.keySet()){ i++;
	 * command += bt.getColumnName() + " = '" + datamap.get(bt).getNum() + "',";
	 * }
	 *
	 * @param gp
	 * @return
	 */
	protected abstract String saveCommand(GiganticPlayer gp);

	/*
	 * SeichiAssistのsqlからデータを引き継ぐメソッド
	 *
	 * @param gp 引き継ぎたいプレイヤー
	 *
	 * @param tm PlayerDataTableマネージャー
	 */
	protected abstract void takeoverPlayer(GiganticPlayer gp,
			PlayerDataTableManager tm);

	/**
	 * 新規プレイヤーのデータを作成するメソッド
	 *
	 * @param gp
	 *            プレイヤー
	 */
	protected abstract void firstjoinPlayer(GiganticPlayer gp);

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		PlayerDataTableManager tm = Gigantic.seichisql
				.getManager(PlayerDataTableManager.class);
		int existtype = tm.isExist(gp);
		if (existtype == 1) {
			debug.info(DebugEnum.SQL, "Table:" + table + " " +  gp.name + "のデータをPlayerDataから引き継ぎます．");
			this.takeoverPlayer(gp, tm);
		} else if (existtype == 0) {
			this.firstjoinPlayer(gp);
		} else {
			plugin.getLogger().warning(
					"Failed to count player:" + gp.name
							+ "in SeichiAssistPlayerData");
			return false;
		}
		return true;
	}

}