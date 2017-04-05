package com.github.unchama.player.fly;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

public class FlyManager extends DataManager{
	private int flytime;
	private boolean flyflag;
	private boolean endlessflyflag;

	public FlyManager(GiganticPlayer gp) {
		super(gp);
	}
	
	/**flytimeを設定します。
	 * 
	 * @param flytime(int)
	 */
	public void setFlytime(int flytime){
		this.flytime = flytime;
	}
	
	/**flytimeを取得します
	 * 
	 * @retrun flytime(int)
	 */
	public int getFlytime(){
		return this.flytime;
	}
	
	/**1分ごとに呼び出す処理(flytimeを減らす)
	 * 
	 */
	public void decrease_min(){
		int flytime = this.flytime;
		setFlytime(flytime - 1);
	}
	
	/**flyflagを変更します
	 * 
	 * @param flyflag(boolean)
	 */
	public void setFlyflag(boolean flyflag){
		this.flyflag = flyflag;
	}
	
	/**継続設定
	 * 
	 * @param endlessflyflag(boolean)
	 */
	public void setEndlessflyflag(boolean endlessflyflag){
		this.endlessflyflag = endlessflyflag;
	}
	
	/**flyflagを取得します。
	 * 
	 * @return flyflag
	 */
	public boolean getFlyflag(){
		return this.flyflag;
	}
	
	/**endlessflagを取得します
	 * 
	 * @return endlessflyflag
	 */
	public boolean getEndlessflyflag(){
		return this.endlessflyflag;
	}

}
