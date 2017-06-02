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
	 * @param
	 */
	public void setFlytime(int flytime){
		this.flytime = flytime;
	}
	
	/**flytimeを取得します
	 * 
	 * @retrun flytime
	 */
	public int getFlytime(){
		return this.flytime;
	}
	
	/**flyflagを変更します
	 * 
	 * @param
	 */
	public void setFlyflag(boolean flyflag){
		this.flyflag = flyflag;
	}
	
	/**継続設定
	 * 
	 * @param
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
	
	/**endlessflyflagを取得します
	 * 
	 * @return endlessflyflag
	 */
	public boolean getEndlessflyflag(){
		return this.endlessflyflag;
	}
	
	/**flyの状態を取得します
	 * 
	 * @return ON/OFF
	 */
	public String getFlyState(){
		if(this.flyflag){
			return "ON";
		}else{
			return "OFF";
		}
	}
	
	/**Fly時間の状況を取得します
	 * 
	 * @return 時間/∞
	 */
	public String getFlyTimeState(){
		if(this.endlessflyflag){
			return "∞";
		}else{
			return Integer.toString(this.flytime);
		}
	}
}
