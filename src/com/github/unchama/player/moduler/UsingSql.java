package com.github.unchama.player.moduler;



/**DataManager用インターフェース
 * SQLを使用したい時に実装してください．
 *
 * @author tar0ss
 *
 */
public interface UsingSql {
	public abstract void save();
	public abstract void load();
}
