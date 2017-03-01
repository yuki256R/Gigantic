package com.github.unchama.sql.moduler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;

/**
 *
 * @author tar0ss
 *
 */
public abstract class PlayerFromSeichiTableManager extends PlayerTableManager{

	public PlayerFromSeichiTableManager(Sql sql) {
		super(sql);
	}
	/**ex)
	 * command = "add column if not exists name varchar(30) default null,"
	 *
	 * @return command
	 */
	protected abstract String addOriginalColumn();
	/**set new player data
	 *
	 * @param gp
	 * @return command
	 */
	protected boolean newPlayer(GiganticPlayer gp){
		PlayerDataTableManager tm = Gigantic.seichisql.getManager(PlayerDataTableManager.class);
		int existtype = tm.isExist(gp);
		if(existtype == 1){
			this.takeoverPlayer(gp,tm);
		}else if(existtype == 0){
			this.newPlayer(gp);
		}else{
			plugin.getLogger().warning("Failed to count player:" + gp.name + "in SeichiAssistPlayerData");
		}
	}
	/**ex)
		for(BlockType bt : BlockType.values()){
			double n = rs.getDouble(bt.getColumnName());
			datamap.put(bt, new MineBlock(n));
		}
	 *
	 * @param gp
	 * @throws SQLException
	 */
	@Override
	public abstract void loadPlayer(GiganticPlayer gp,ResultSet rs);
	/**ex)
		for(BlockType bt : datamap.keySet()){
			i++;
			command += bt.getColumnName() + " = '" + datamap.get(bt).getNum() + "',";
		}
	 *
	 * @param gp
	 * @return
	 */
	@Override
	protected abstract String savePlayer(GiganticPlayer gp);

}
