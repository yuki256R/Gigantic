package com.github.unchama.player.moduler;



/**DataManager用インターフェース
 * SQLを使用したい時に実装してください．
 *
 * @author tar0ss
 *
 */
public interface UsingSql {
	public abstract void save(boolean loginflag);
	public abstract void load();
}
