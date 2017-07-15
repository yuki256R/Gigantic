package com.github.unchama.listener.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.buildskill.BuildSkillManager;

/**
 * 設置したブロックの見た目を変える
 *
 * @author ten_niti
 *
 */
public class ConvertPlacementListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		BuildSkillManager manager = gp.getManager(BuildSkillManager.class);
		if(!manager.isConvertPlacementFlag()){
			return;
		}

		Block block = event.getBlock();
		if (block.getType() == Material.LOG) {
			// 原木を全面皮へ
			switch (block.getData()) {
			// オーク
			case 0:
			case 4:
			case 8:
				block.setData((byte) 12);
				break;
			// マツ
			case 1:
			case 5:
			case 9:
				block.setData((byte) 13);
				break;
			// シラカバ
			case 2:
			case 6:
			case 10:
				block.setData((byte) 14);
				break;
			// ジャングル
			case 3:
			case 7:
			case 11:
				block.setData((byte) 15);
				break;
			}
		} else if (block.getType() == Material.LOG_2) {
			switch (block.getData()) {
			// アカシア
			case 0:
			case 4:
			case 8:
				block.setData((byte) 12);
				break;
			// ダークオーク
			case 1:
			case 5:
			case 9:
				block.setData((byte) 13);
				break;
			}
		} else if (block.getType() == Material.HUGE_MUSHROOM_1) {
			// 茶キノコブロック
			// スポンジっぽい見た目
			block.setData((byte) 0);
		} else if (block.getType() == Material.HUGE_MUSHROOM_2) {
			// 赤キノコブロック
			// 白い茎の見た目
			block.setData((byte) 15);
		}
	}

}
