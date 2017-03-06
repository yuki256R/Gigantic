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
	public abstract Boolean save(GiganticPlayer gp,boolean loginflag);
}
