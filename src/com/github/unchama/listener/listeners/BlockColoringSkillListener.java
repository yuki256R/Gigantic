package com.github.unchama.listener.listeners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.buildskill.BuildSkillManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager;

/**
 * 手に持った染料で染められるブロックの色を変更するスキル
 * @author ten_niti
 *
 */
public class BlockColoringSkillListener implements Listener {

	private static Map<Material, Material> ConvertBlocks = new HashMap<Material, Material>(){
		{
			put(Material.GLASS, Material.STAINED_GLASS); // ガラス
			put(Material.THIN_GLASS, Material.STAINED_GLASS_PANE); // 板ガラス
			put(Material.HARD_CLAY, Material.STAINED_CLAY); // 堅焼き粘土
		}
	};
	private static Random random = new Random();

	private static Set<Material> colorBlocks = new HashSet<Material>(Arrays.asList(
			Material.WOOL, // 羊毛
			Material.CARPET, // カーペット
			Material.STAINED_GLASS, // 色ガラス
			Material.GLASS,
			Material.STAINED_GLASS_PANE, // 色板ガラス
			Material.THIN_GLASS,
			Material.STAINED_CLAY, // 色堅焼き粘土
			Material.HARD_CLAY
			));

    @SuppressWarnings("deprecation")
	@EventHandler
    public void BlockColoringEvent(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		BuildSkillManager manager = gp.getManager(BuildSkillManager.class);
		if(!manager.isBlockColoringFlag()){
			return;
		}

		// 左クリックの時終了
		Action action = event.getAction();
		if (!action.equals(Action.RIGHT_CLICK_AIR)
				&& !action.equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		// メインハンドでなければ終了
		EquipmentSlot hand = event.getHand();
		if (hand == null || hand.equals(EquipmentSlot.OFF_HAND)) {
			return;
		}

		// 染料でなければ終了
		ItemStack itemstack = event.getItem();
		if(itemstack == null || itemstack.getType() != Material.INK_SACK){
			return;
		}
	    // インクと色付きブロックでDataValueが同一ではないため変換する（何故か並びが逆）
		byte value = (byte) (15 - itemstack.getData().getData());

		Block block = player.getTargetBlock(Util.getFluidMaterials(), 5);
		if(block == null){
			return;
		}

		// 対象外のブロックなら終了
		Material material = block.getType();
		if(!colorBlocks.contains(material)){
			return;
		}

		// 色なし＝別ブロックの場合は色付きブロックに変換
		if(ConvertBlocks.containsKey(material)){
			block.setType(ConvertBlocks.get(material));
		}else if(block.getData() == value){
			// 同じ色なら終了
			return;
		}

		player.playSound(player.getLocation(),
				Sound.ENTITY_EGG_THROW, (float) 0.7, (float) 1);
		block.setData(value);
		// 確率で消費
		if(random.nextDouble() < ((double)1 / 8)){
			int amount = itemstack.getAmount();
			if(amount > 1){
				itemstack.setAmount(amount - 1);
			}else{
				itemstack.setType(Material.AIR);
			}
		}

		event.setCancelled(true);
    }
    DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler
    public void BlockWashingEvent(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		BuildSkillManager manager = gp.getManager(BuildSkillManager.class);
		if(!manager.isBlockWashingFlag()){
			return;
		}

		// 左クリックの時終了
		Action action = event.getAction();
		if (!action.equals(Action.RIGHT_CLICK_AIR)
				&& !action.equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		// メインハンドでなければ終了
		EquipmentSlot hand = event.getHand();
		if (hand == null || hand.equals(EquipmentSlot.OFF_HAND)) {
			return;
		}
		// スポンジでなければ終了
		ItemStack itemstack = event.getItem();
		if(itemstack == null || itemstack.getType() != Material.FEATHER){
			return;
		}

		Block block = player.getTargetBlock(Util.getFluidMaterials(), 5);
		if(block == null){
			return;
		}

		// 元が違う色付きブロックを戻す
		Material material = block.getType();
		if(!ConvertBlocks.containsValue(material)){
			return;
		}
		for(Material mat : ConvertBlocks.keySet()){
			if(ConvertBlocks.get(mat) == material){
				player.playSound(player.getLocation(),
						Sound.ENTITY_CHICKEN_STEP, (float) 2, (float) 0.3);
				block.setType(mat);

				// 確率で消費
				if(random.nextDouble() < ((double)1 / 16)){
					int amount = itemstack.getAmount();
					if(amount > 1){
						itemstack.setAmount(amount - 1);
					}else{
						itemstack.setType(Material.AIR);
					}
				}

				event.setCancelled(true);
				return;
			}
		}
	}

}
