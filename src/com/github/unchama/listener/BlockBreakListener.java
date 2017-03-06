package com.github.unchama.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.github.unchama.event.GiganticBreakEvent;

public class BlockBreakListener implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreakListener(BlockBreakEvent event){
		if(!(event instanceof GiganticBreakEvent)){
            event.setCancelled(true);
            Bukkit.getServer().getPluginManager().callEvent(new GiganticBreakEvent(event.getBlock(),event.getPlayer()));
		}
	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreakNotCancelledListener(BlockBreakEvent event){
		if(event.isCancelled())return;
		//以下cancelされなかったときの処理
		/*以下の処理を実行
		・経験値の取得（プレイヤーの経験値データに直接加算）
		・使用ツールのシルク，非シルクを判定しドロップマテリアルを取得
		・ドロップの量を取得（ラピスなどは，複数ドロップするため）
		・破壊ブロックの統計量加算
		・使用ツールの耐久値を変更
		 */


	}
}
