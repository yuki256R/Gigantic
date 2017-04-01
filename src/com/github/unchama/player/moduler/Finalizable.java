package com.github.unchama.player.moduler;


/**DataManager用インターフェース
 * セーブ前の最終処理が必要な時に実装してください．
 * @author tar0ss
 *
 */
public interface Finalizable {
	/**セーブ前の最終処理を行います．
	 *
	 */
	public abstract void fin();
}
