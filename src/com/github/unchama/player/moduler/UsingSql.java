package com.github.unchama.player.moduler;




/**DataManager用インターフェース
 * SQLを使用したい時に実装してください．
 *
 * @author tar0ss
 *
 */
public interface UsingSql {
	/**sqlを保存するときのメソッドです．
	 * 
	 * @param loginflag
	 */
	public abstract void save(Boolean loginflag);
}
