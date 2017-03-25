package com.github.unchama.sql.moduler;

import java.util.HashMap;
import java.util.UUID;

import com.github.unchama.player.GiganticPlayer;

/**プレイヤーデータをロードするときに実装してください．
 *
 * @author tar0ss
 *
 */
public interface GiganticLoadable {
	public abstract Boolean multiload(HashMap<UUID, GiganticPlayer> tmpmap);
	/**セーブするときに実行します．プレイヤーがログアウトする場合のみloginflagをfalseにしてください．
	 *
	 * @param gp
	 * @param loginflag
	 * @return
	 */
	public abstract Boolean save(GiganticPlayer gp,boolean loginflag);
}
