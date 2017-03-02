package com.github.unchama.player.moduler;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;
import com.github.unchama.yml.ConfigManager;

/**全てのマネージャーで必ず実装してください．
 *
 * @author tar0ss
 *
 */
public abstract class DataManager{
	protected Sql sql = Gigantic.sql;
	protected Gigantic plugin = Gigantic.plugin;
	protected ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	protected GiganticPlayer gp;
	protected boolean loaded;

	protected DataManager(GiganticPlayer gp){
		this.gp = gp;
		this.loaded = false;
	}
	/**sqlからデータをロードしたときに使う
	 * sqlを使用しない場合はインスタンス作成と同時に行う．
	 *
	 */
	public void setLoaded(Boolean flag){
		this.loaded = flag;
	}
	//ロード処理が終わっているか確認する．
	public boolean isLoaded(){
		return this.loaded;
	}

}
