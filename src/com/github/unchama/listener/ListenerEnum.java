package com.github.unchama.listener;

import com.github.unchama.listener.listeners.*;
import org.bukkit.event.Listener;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.listener.listeners.BlockBreakListener;
import com.github.unchama.listener.listeners.BlockColoringSkillListener;
import com.github.unchama.listener.listeners.BlockLineUpListener;
import com.github.unchama.listener.listeners.BlockPlaceEventListener;
import com.github.unchama.listener.listeners.BuildBlockIncrementListener;
import com.github.unchama.listener.listeners.BuildLevelListener;
import com.github.unchama.listener.listeners.ConvertPlacementListener;
import com.github.unchama.listener.listeners.EnchantmentListener;
import com.github.unchama.listener.listeners.EntityListener;
import com.github.unchama.listener.listeners.ExchangeInventoryListener;
import com.github.unchama.listener.listeners.FishingLevelListener;
import com.github.unchama.listener.listeners.FishingListener;
import com.github.unchama.listener.listeners.GachaAppleListener;
import com.github.unchama.listener.listeners.GeneralBreakListener;
import com.github.unchama.listener.listeners.GiganticInteractListener;
import com.github.unchama.listener.listeners.GiganticPlayerAvailableListener;
import com.github.unchama.listener.listeners.HeadPlaceCancelListener;
import com.github.unchama.listener.listeners.HuntingExpIncrementListener;
import com.github.unchama.listener.listeners.HuntingLevelListener;
import com.github.unchama.listener.listeners.HuntingPointEventListener;
import com.github.unchama.listener.listeners.InventoryClickListener;
import com.github.unchama.listener.listeners.MenuClickListener;
import com.github.unchama.listener.listeners.MineBlockIncrementListener;
import com.github.unchama.listener.listeners.MinuteListener;
import com.github.unchama.listener.listeners.PlayerChatListener;
import com.github.unchama.listener.listeners.PlayerFirstJoinListener;
import com.github.unchama.listener.listeners.PlayerInteractListener;
import com.github.unchama.listener.listeners.PlayerJoinListener;
import com.github.unchama.listener.listeners.PlayerPickupItemListener;
import com.github.unchama.listener.listeners.PlayerQuitListener;
import com.github.unchama.listener.listeners.PlayerStatisticListener;
import com.github.unchama.listener.listeners.PlayerSwapHandItemsListener;
import com.github.unchama.listener.listeners.PlayerTimeIncrementListener;
import com.github.unchama.listener.listeners.RankingUpdateListener;
import com.github.unchama.listener.listeners.SecondListener;
import com.github.unchama.listener.listeners.SeichiLevelListener;

/**リスナーを追加するときはここに必ず追記すること．
 *
 * @author tar0ss
 *
 */
public enum ListenerEnum {
	PLAYERJOIN(new PlayerJoinListener()),
	FIRSTJOIN(new PlayerFirstJoinListener()),
	PLAYERQUIT(new PlayerQuitListener()),
	SECOND(new SecondListener()),
	MINUTE(new MinuteListener()),
	STATISTIC(new PlayerStatisticListener()),
	PLAYERCLICK(new PlayerInteractListener()),
	INVNETORYCLICK(new InventoryClickListener()),
	SEICHILEVEL(new SeichiLevelListener()),
	GENERALBREAK(new GeneralBreakListener()),
	BLOCKBREAK(new BlockBreakListener()),
	MENUCLICK(new MenuClickListener()),
	GIGANTICINTERACT(new GiganticInteractListener()),
	PLAYERSWAPHANDITEMS(new PlayerSwapHandItemsListener()),
    BUILD(new BlockPlaceEventListener()),
    MINEBLOCKINCREMENT(new MineBlockIncrementListener()),
    BUILDBLOCKINCREMENT(new BuildBlockIncrementListener()),
    HUNTINGPOINT(new HuntingPointEventListener()),
    HUNTINGLEVEL(new HuntingLevelListener()),
    HUNTINGEXP(new HuntingExpIncrementListener()),
    PLAYERPICKUPITEM(new PlayerPickupItemListener()),
    BLOCKLINEUP(new BlockLineUpListener()),
    RANKINGUPDATE(new RankingUpdateListener()),
	ENCHANTMENT(new EnchantmentListener()),
    BUILDLEVEL(new BuildLevelListener()),
    GACHAAPPLE(new GachaAppleListener()),
    PLAYERCHAT(new PlayerChatListener()),
    CONVERTPLACEMENT(new ConvertPlacementListener()),
    HEADPLACECANCEL(new HeadPlaceCancelListener()),
	EXCHANGEINVENTORY(new ExchangeInventoryListener()),
    BLOCKCOLORING(new BlockColoringSkillListener()),
    FISHING(new FishingListener()),
    FISHINGLEVEL(new FishingLevelListener()),
    ENTITYLISTENER(new EntityListener()),
    PLAYERTIMEINCREMENT(new PlayerTimeIncrementListener()),
    GIGANTICPLAYERAVAILABLE(new GiganticPlayerAvailableListener()),
	ENTITYDEATH(new EntityDeathListener()),
	ENTITYDAMAGEBYENITIY(new EntityDamageByEntityListener()),
	;
	private Listener listener;

	private ListenerEnum(Listener listener){
		this.listener = listener;
	}

	private Listener getListener(){
		return listener;
	}

	public static void registEvents(Gigantic plugin){
		for(ListenerEnum le : ListenerEnum.values()){
			plugin.getServer().getPluginManager().registerEvents(le.getListener(),plugin);
		}
	}
}
