package com.github.unchama.listener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.unchama.player.seichiskill.passive.skywalk.SkyWalkManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import zedly.zenchantments.Zenchantments;

import com.github.unchama.event.GiganticInteractEvent;
import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.GachaNotification;
import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.gui.moduler.KeyItem;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildData;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.player.buildskill.BuildSkillManager;
import com.github.unchama.player.gacha.PlayerGachaManager;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.player.seichiskill.active.MagicDriveManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.task.MagicDriveTaskRunnable;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

import de.tr7zw.itemnbtapi.NBTItem;

/**
 * プレイヤーがGiganticPlayerデータを持っている時にのみコールされます．
 * キャンセルすると，PlayerInteractEventもキャンセルされます．
 *
 * @author tar0ss
 *
 */
public class GiganticInteractListener implements Listener {
	Gigantic plugin = Gigantic.plugin;
	GuiMenu guimenu = Gigantic.guimenu;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	Gacha gacha = Gigantic.gacha;
	Zenchantments Ze;

	GiganticInteractListener() {
		Ze = Util.getZenchantments();
	}

	//ブロック設置座標のブロック判別
    final private List<Material> PutMaterialList = Arrays.asList(
    	Material.AIR,
    	Material.SNOW,
    	Material.LONG_GRASS,
    	Material.DEAD_BUSH,
    	Material.YELLOW_FLOWER,
    	Material.RED_ROSE,
    	Material.RED_MUSHROOM,
    	Material.BROWN_MUSHROOM
    );
  //設置対象の[setunder]分の下のブロックが空気かどうか
    final private List<Material> UnderMaterialType = Arrays.asList(
		Material.AIR,
		Material.LAVA,
		Material.STATIONARY_LAVA,
		Material.WATER,
		Material.STATIONARY_WATER
    );

	/**
	 * ガチャを引く処理
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void rollGacha(GiganticInteractEvent event) {
		Player player = event.getPlayer();
		// プレイヤーが起こしたアクションを取得
		Action action = event.getAction();
		// アクションを起こした手を取得
		EquipmentSlot equipmentslot = event.getHand();

		ItemStack item = event.getItem();
		if (item == null)
			return;

		NBTItem nbti = new NBTItem(item);
		// gacha券tagを判定
		if (GachaManager.isTicket(nbti)) {
			event.setCancelled(true);
		} else {
			return;
		}

		if (equipmentslot == null) {
			return;
		}

		if (equipmentslot.equals(EquipmentSlot.OFF_HAND)) {
			return;
		}

		if (action.equals(Action.LEFT_CLICK_AIR)
				|| action.equals(Action.LEFT_CLICK_BLOCK)) {
			return;
		}

		int count = 1;

		if (player.isSneaking()) {
			count = item.getAmount();
		}

		GiganticPlayer gp = event.getGiganticPlayer();
		GachaType gt = GachaManager.getGachaType(nbti);
		GachaManager gm = gacha.getManager(gt.getManagerClass());

		if (gm.isMaintenance()) {
			player.sendMessage(ChatColor.AQUA + "メンテナンス中です．");
			event.setCancelled(true);
			return;
		}
		PlayerGachaManager pm = gp.getManager(PlayerGachaManager.class);
		boolean dropped = false;
		// 通知用のガチャ結果リスト
		List<GachaItem> gachaItems = new ArrayList<GachaItem>();

		// まとめてガチャを開封する
		for (int i = 0; i < count; i++) {
			// ガチャを回す
			GachaItem gachaitem = pm.roll(gt);
			gachaItems.add(gachaitem);
			ItemStack itemstack = gachaitem.getItem();
			dropped |= Util.giveItem(player, itemstack, false);
		}

		// 引いた物の通知
		GachaNotification.Notification(player, gachaItems, dropped);

		// ガチャ券を減らす
		if (player.isSneaking() || item.getAmount() == 1) {
			player.getInventory()
					.setItemInMainHand(new ItemStack(Material.AIR));
		} else {
			item.setAmount(item.getAmount() - 1);
		}
		return;
	}

	/**
	 * クリックに関係するメニューオープン処理
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onGiganticOpenMenuListener(GiganticInteractEvent event) {
		Player player = event.getPlayer();
		// プレイヤーが起こしたアクションを取得
		Action action = event.getAction();
		// アクションを起こした手を取得
		EquipmentSlot equipmentslot = event.getHand();

		if (equipmentslot == null) {
			return;
		}

		if (equipmentslot.equals(EquipmentSlot.OFF_HAND)) {
			return;
		}

		for (GuiMenu.ManagerType mt : GuiMenu.ManagerType.values()) {
			GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
					.getManagerClass());
			// キーアイテムを持っていなければ終了
			if (!m.hasKey())
				continue;
			// クリックの種類が指定のものと違うとき終了
			String click = m.getClickType();
			if (click == null) {
				continue;
			}
			if (click.equalsIgnoreCase("left")) {
				if (action.equals(Action.RIGHT_CLICK_AIR)
						|| action.equals(Action.RIGHT_CLICK_BLOCK)) {
					continue;
				}
			} else if (click.equalsIgnoreCase("right")) {
				if (action.equals(Action.LEFT_CLICK_AIR)
						|| action.equals(Action.LEFT_CLICK_BLOCK)) {
					continue;
				}
			} else {
				continue;
			}

			KeyItem keyitem = m.getKeyItem();
			ItemStack item = event.getItem();

			if (item == null) {
				return;
			}

			if (keyitem.getMaterial() != null) {
				if (!item.getType().equals(keyitem.getMaterial())) {
					continue;
				} else {
					if (!(item.getDurability() == (short) keyitem.getDamage())) {
						continue;
					}
				}
			}

			if (keyitem.getName() != null) {
				if (!item.getItemMeta().getDisplayName()
						.equalsIgnoreCase(keyitem.getName())) {
					continue;
				}
			}

			if (keyitem.getLore() != null) {
				List<String> tmplore = keyitem.getLore();
				int maxcount = 20;
				int count = 0;
				while (!tmplore.isEmpty() && count <= maxcount) {
					String tmp = tmplore.get(0);
					for (String c : item.getItemMeta().getLore()) {
						if (c.equalsIgnoreCase(tmp)) {
							tmplore.remove(0);
							break;
						}
					}
					count++;
				}

				if (!tmplore.isEmpty()) {
					continue;
				}
			}
			/*if (!player.getInventory().getItemInOffHand().getType()
					.equals(Material.AIR)) {
				player.sendMessage(ChatColor.RED
						+ "オフハンドにアイテムを持った状態でメニューを開くことはできません");
				return;
			}*/
			event.setCancelled(true);
			m.open(player, 0, true);
			continue;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void MagicDrive(GiganticInteractEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		GiganticPlayer gp = event.getGiganticPlayer();
		MagicDriveManager skill = gp.getManager(MagicDriveManager.class);

		// 左クリックの時終了
		Action action = event.getAction();
		if (!action.equals(Action.RIGHT_CLICK_AIR)
				&& !action.equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		// オフハンドから実行したとき終了
		EquipmentSlot hand = event.getHand();
		if (hand == null) {
			return;
		}
		if (hand.equals(EquipmentSlot.OFF_HAND))
			return;

		// トグルがオフなら終了
		if (!skill.getToggle()) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルのトグルがオフなため発動できません");
			return;
		}

		// サバイバルではないとき終了
		if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
			debug.sendMessage(player, DebugEnum.SKILL,
					"サバイバルではないのでスキルの発動ができません．");
			return;
		}

		// フライ中に使用していた時終了
		if (player.isFlying()) {
			player.sendMessage("フライ中はスキルの発動ができません．");
			return;
		}

		// 使用可能ワールドではないとき終了
		if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
			player.sendMessage("このワールドではスキルの発動ができません．");
			return;
		}

		ItemStack tool = event.getItem();
		if (tool == null) {
			return;
		}
		// スキルを発動できるツールでないとき終了
		if (!ActiveSkillManager.canBreak(tool)) {
			player.sendMessage("スキルの発動ができるツールではありません．");
			return;
		}

		// 木こりエンチャントがある時終了
		if (Ze.isCompatible("木こり", tool)) {
			player.sendMessage("木こりエンチャントがあるためスキルが発動できません");
			return;
		}

		Block block = player.getTargetBlock(Util.getFluidMaterials(), 50);

		Material material = block.getType();
		// スキルを発動できるブロックでないとき終了
		if (!ActiveSkillManager.canBreak(material)) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルが発動できるブロックではありません．");
			return;
		}

		event.setCancelled(true);

		new MagicDriveTaskRunnable(player, skill, tool, block)
				.runTaskTimerAsynchronously(plugin, 0, 1);
	}

    /**
     * 範囲設置スキル処理
     *
     * @param
     */
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerMenuUIEvent(PlayerInteractEvent event) {
        //プレイヤーを取得
        Player player = event.getPlayer();
        //ワールドデータを取得
        World playerworld = player.getWorld();
        //プレイヤーが起こしたアクションを取得
        Action action = event.getAction();
        //プレイヤーデータ
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

        //コンフィグ
        ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
        //スキル使用時の倍率を取得しておく
        BigDecimal blockCountMag = config.getBlockCountMag();

        //スキルフラグ
        boolean zoneSetSkillFlag = gp.getManager(BuildSkillManager.class).isZone_flag();
        //スキルダートフラグ
        boolean zoneSkillDirtFlag = gp.getManager(BuildSkillManager.class).isZone_dirt_flag();
        //MineStackフラグ
        boolean zoneSkillMinestackFlag = gp.getManager(BuildSkillManager.class).isZone_minestack_flag();

        //MineStack用
        StackType currentStackType = null;
        //Check用
        boolean check = false;

     // デッドコードのためコメントアウト
//        //プレイヤーデータが無い場合は処理終了
//        if (gp == null) {
//            return;
//        }

        if (!action.equals(Action.LEFT_CLICK_AIR) && !action.equals(Action.LEFT_CLICK_BLOCK)) {
        	return;
        }
        if (!player.isSneaking()) {
        	return;
        }

        //プレイヤーインベントリを取得
        PlayerInventory inventory = player.getInventory();
        //メインハンドとオフハンドを取得
        ItemStack mainhanditem = inventory.getItemInMainHand();
        ItemStack offhanditem = inventory.getItemInOffHand();

        //メインハンドにブロックがあるか
        boolean mainhandtoolflag = BuildData.materiallist.contains(mainhanditem.getType());
        //オフハンドにブロックがあるか
        boolean offhandtoolflag = BuildData.materiallist.contains(offhanditem.getType());


        //場合分け
        if (offhandtoolflag) {
            //スキルフラグON以外のときは終了
            if (!zoneSetSkillFlag) {
                return;
            }

            //プレイヤーの足の座標を取得
            int playerlocx = player.getLocation().getBlockX();
            int playerlocy = player.getLocation().getBlockY();
            int playerlocz = player.getLocation().getBlockZ();

            //スキルの範囲設定用
            int AREAint = gp.getManager(BuildSkillManager.class).getAREAint();
            int SEARCHint = AREAint + 1;
            int AREAintB = (AREAint * 2) + 1;
            int SEARCHintB = (SEARCHint * 2) + 1;

            //同ブロック探索(7*6*7)の開始座標を計算
            int searchX = playerlocx - SEARCHint;
            int searchY = playerlocy - 4;
            int searchZ = playerlocz - SEARCHint;


            //同上(Y座標記録)
            int Y1 = 256;
            int Y2 = 256;

            //スキル発動条件を満たすか
            boolean SetReady = false;

            //
            int block_cnt = 0;
            //オフハンドアイテムと、範囲内のブロックに一致する物があるかどうか判別
            //同じ物がない場合・同じ物が3か所以上のY軸で存在する場合→SetReady = false
            for (; searchY < playerlocy + 2; ) {

                if (offhanditem.getType() == player.getWorld().getBlockAt(searchX, searchY, searchZ).getType() &&
                        offhanditem.getData().getData() == player.getWorld().getBlockAt(searchX, searchY, searchZ).getData()) {

                    if (Y1 == searchY || Y1 == 256) {
                        Y1 = searchY;
                        SetReady = true;
                    } else if (Y2 == searchY || Y2 == 256) {
                        Y2 = searchY;
                    } else {
                        SetReady = false;
                        player.sendMessage(ChatColor.RED + "範囲内に「オフハンドと同じブロック」が多すぎます。(Y軸2つ分以内にして下さい)");
                        break;
                    }
                }
                searchX++;

                if (searchX > playerlocx + SEARCHint) {
                    searchX = searchX - SEARCHintB;
                    searchZ++;
                    if (searchZ > playerlocz + SEARCHint) {
                        searchZ = searchZ - SEARCHintB;
                        searchY++;
                    }

                }
            }

            if (Y1 == 256) {
                player.sendMessage(ChatColor.RED + "範囲内に「オフハンドと同じブロック」を設置してください。(基準になります)");
                SetReady = false;
            }

            //上の処理で「スキル条件を満たしていない」と判断された場合、処理終了
            if (!SetReady) {
                player.sendMessage(ChatColor.RED + "発動条件が満たされませんでした。");
            }

            if (SetReady) {
                //実際に範囲内にブロックを設置する処理
                //設置範囲の基準となる座標
                int setblockX = playerlocx - AREAint;
                int setblockY = Y1;
                int setblockZ = playerlocz - AREAint;
                int setunder = 1;

                int searchedInv = 9;

                ItemStack ItemInInv = null;
                int ItemInInvAmount = 0;

                Location WGloc = new Location(playerworld, 0, 0, 0);


                for (; setblockZ < playerlocz + SEARCHint; ) {
                    //ブロック設置座標のブロック判別
                    if (PutMaterialList.contains(player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).getType())) {
                        setunder = 1;
                        if (zoneSkillDirtFlag) {
                            for (; setunder < 5; ) {
                                //設置対象の[setunder]分の下のブロックが空気かどうか
                                if (UnderMaterialType.contains(player.getWorld().getBlockAt(setblockX, (setblockY - setunder), setblockZ).getType())) {                                    WGloc.setX(setblockX);
                                    WGloc.setY(setblockY - setunder);
                                    WGloc.setZ(setblockZ);
                                    //他人の保護がかかっている場合は処理を終了
                                    if (!Util.getWorldGuard().canBuild(player, WGloc)) {
                                        player.sendMessage(ChatColor.RED + "付近に誰かの保護がかかっているようです");
                                    } else {
                                        //保護のない場合、土を設置する処理
                                        player.getWorld().getBlockAt(setblockX, (setblockY - setunder), setblockZ).setType(Material.DIRT);
                                    }
                                }
                                setunder++;

                            }
                        }

                        //他人の保護がかかっている場合は処理を終了
                        WGloc.setX(setblockX);
                        WGloc.setY(setblockY);
                        WGloc.setZ(setblockZ);
                        if (!Util.getWorldGuard().canBuild(player, WGloc)) {
                            player.sendMessage(ChatColor.RED + "付近に誰かの保護がかかっているようです");
                            break;
                        } else if (zoneSkillMinestackFlag) minestack:{
                            //ここでMineStackの処理。flagがtrueならInvに関係なしにここに持ってくる
                            //label指定は基本的に禁じ手だが、今回は後付けなので使わせてもらう。(解読性向上のため、1箇所のみの利用)
                            for (StackType st : StackType.values()) {
                                currentStackType = st;
                                if (offhanditem.getType().equals(st.getMaterial()) &&
                                        offhanditem.getData().getData() == st.getDurability()) {
                                    check = true;
                                    break;
                                }
                            }
                            if (check) {
                                //設置するブロックがMineStackに登録済み
                                //1引く
                                if (gp.getManager(MineStackManager.class).datamap.get(currentStackType).getNum() > 0) {
                                    //debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "MineStackよりブロック消費");
                                    //debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "MineStackブロック残量(前):"
                                    //        + gp.getManager(MineStackManager.class).datamap.get(currentStackType).getNum());
                                    gp.getManager(MineStackManager.class).datamap.get(currentStackType).add(-1);
                                    //debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "MineStackブロック残量(後):"
                                    //        + gp.getManager(MineStackManager.class).datamap.get(currentStackType).getNum());

                                    //設置処理
                                    player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).setType(offhanditem.getType());
                                    player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).setData(offhanditem.getData().getData());

                                    //ブロックカウント
                                    block_cnt++;

                                    //あとの設定
                                    setblockX++;

                                    if (setblockX > playerlocx + AREAint) {
                                        setblockX = setblockX - AREAintB;
                                        setblockZ++;
                                    }
                                    continue;
                                } else {
                                    debug.sendMessage(player, DebugManager.DebugEnum.BUILD,
                                            "MineStackのブロックがありません。インベントリより消費します。");

                                    break minestack;//minestack処理はなかったことにして次のfor分に飛ぶ。(label:minestackだけから抜ける)
                                }
                            }
                        }
                        else {

                            //インベントリの左上から一つずつ確認する。
                            //※一度「該当アイテムなし」と判断したスロットは次回以降スキップする様に組んであるゾ
                            for (; searchedInv < 36; ) {
                                //該当スロットのアイテムデータ取得
                                ItemInInv = player.getInventory().getItem(searchedInv);
                                if (ItemInInv == null) {
                                } else {
                                    ItemInInvAmount = ItemInInv.getAmount();
                                }
                                //スロットのアイテムが空白だった場合の処理(エラー回避のため)
                                if (ItemInInv == null) {
                                    //確認したスロットが空気だった場合に次スロットへ移動
                                	searchedInv = checkSearchedInv(player, searchedInv);
                                    //スロットアイテムがオフハンドと一致した場合
                                } else if (ItemInInv.getType() == offhanditem.getType()) {
                                    //数量以外のデータ(各種メタ)が一致するかどうか検知(仮)
                                    ItemStack ItemInInvCheck = ItemInInv;
                                    ItemStack offhandCheck = offhanditem;
                                    ItemInInvCheck.setAmount(1);
                                    offhandCheck.setAmount(1);

                                    if (!(ItemInInvCheck.equals(offhandCheck))) {
                                    	searchedInv = checkSearchedInv(player, searchedInv);
                                    } else {
                                        //取得したインベントリデータから数量を1ひき、インベントリに反映する
                                        if (ItemInInvAmount == 1) {
                                            ItemInInv.setType(Material.AIR);
                                            ItemInInv.setAmount(1);
                                        } else {
                                            ItemInInv.setAmount(ItemInInvAmount - 1);
                                        }
                                        player.getInventory().setItem(searchedInv, ItemInInv);
                                        //ブロックを設置する
                                        player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).setType(offhanditem.getType());
                                        player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).setData(offhanditem.getData().getData());

                                        block_cnt++;    //設置数カウント
                                        break;

                                    }
                                } else {
                                    //確認したスロットが違うアイテムだった場合に、次のスロットへと対象を移す
                                	searchedInv = checkSearchedInv(player, searchedInv);
                                }
                            }
                        }
                    }
                    if (searchedInv == 36) {
                        break;
                    }

                    setblockX++;

                    if (setblockX > playerlocx + AREAint) {
                        setblockX = setblockX - AREAintB;
                        setblockZ++;

                    }
                }
            }
            player.sendMessage(ChatColor.RED + "敷き詰めスキル：処理終了");
            debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "設置ブロック数:" + block_cnt);

            if (BuildData.isBlockCount(player)) {
                BigDecimal add = blockCountMag.multiply(new BigDecimal(block_cnt));
                gp.getManager(BuildManager.class).addBuild_num_1min(add);//設置した数を足す
                debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "建築量上昇:" + add.doubleValue());
            }

            return;


        } else if (mainhandtoolflag) {
            //メインハンドの時
            return;
        } else {
            //どちらにももっていない時処理を終了
            return;
        }
    }

    // 次の検索スロット番号
    private int checkSearchedInv(Player player, int searchedInv){
        //確認したスロットが違うアイテムだった場合に、次のスロットへと対象を移す
        if (searchedInv == 35) {
            searchedInv = 0;
        } else if (searchedInv == 8) {
            searchedInv = 36;
            player.sendMessage(ChatColor.RED + "アイテムが不足しています!");
        } else {
            searchedInv++;
        }
        return searchedInv;
    }

    /**
     * 液体ブロックを手動で埋めた時に整地量を増加させる処理
     * @param event
     */
    @SuppressWarnings("deprecation")  //データ値なしでは水流・水源の区別がつかないため。
    @EventHandler
    public void onPlayerFillLiquid(PlayerInteractEvent event) {
        //ブロックを右クリックした時の動作
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            //プレイヤーを取得
            Player player = event.getPlayer();
            //プレイヤーデータを取得
            GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
            //プレイヤーインベントリを取得
            PlayerInventory playerinv = player.getInventory();
            //オフハンド・メインハンドのアイテム取得
            ItemStack offhand = playerinv.getItemInOffHand();
            ItemStack mainhand = playerinv.getItemInMainHand();
            //クリックしたブロック・面を取得
            Block clickblock = event.getClickedBlock();
            BlockFace face = event.getBlockFace();

            if (BuildData.isMineIncreaseBlock(mainhand)) {
                Location loc = BuildData.getClickNextBlock(player,clickblock, face);
                Block checkblock = loc.getBlock();
                if (!BuildData.isPlayerInLocation(player, loc)) {
                    if (checkblock.isLiquid()) {
                        if (checkblock.getData() == 0) {
                            gp.getManager(MineBlockManager.class).increase(checkblock.getType());
                        }
                    }
                }
            } else if (BuildData.isMineIncreaseBlock(offhand)) {
                Location loc = BuildData.getClickNextBlock(player,clickblock, face);
                Block checkblock = loc.getBlock();
                if (!BuildData.isPlayerInLocation(player, loc)) {
                    if (checkblock.isLiquid()) {
                        if (checkblock.getData() == 0) {
                            gp.getManager(MineBlockManager.class).increase(checkblock.getType());
                        }
                    }
                }
            }
        }
    }

    /**
     * SkyWalkスキルのListener
     */
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

        gp.getManager(SkyWalkManager.class).run(player);
    }
}
