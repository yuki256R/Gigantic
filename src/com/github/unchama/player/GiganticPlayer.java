package com.github.unchama.player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.gigantic.GiganticManager;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.mineboost.MineBoostManager;
import com.github.unchama.util.ClassUtil;
import com.github.unchama.util.Converter;




public class GiganticPlayer{

	enum DataManagerType{
		/**
		 * Managerを追加するときはここに書く．
		 */
		GIGANTIC(GiganticManager.class),
		MINEBLOCK(MineBlockManager.class),
		MINEBOOST(MineBoostManager.class),


		;

		private Class<? extends DataManager> managerClass;

		DataManagerType(Class<? extends DataManager> managerClass){
			this.managerClass = managerClass;
		}

		public Class<? extends DataManager> getManagerClass(){
			return managerClass;
		}

	}


	Gigantic plugin = Gigantic.plugin;

	public final String name;
	public final UUID uuid;

	private HashMap<Class<? extends DataManager>,DataManager> managermap = new HashMap<Class<? extends DataManager>,DataManager>();



	public GiganticPlayer(Player player){
		this.name = Converter.toString(player);
		this.uuid = player.getUniqueId();
		for(DataManagerType mt : DataManagerType.values()){
			try {
				this.managermap.put(mt.getManagerClass(),mt.getManagerClass().getConstructor(GiganticPlayer.class).newInstance(this));
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				plugin.getLogger().warning("Failed to create new Instance of player:" + this.name);
				e.printStackTrace();
				plugin.getPluginLoader().disablePlugin(plugin);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends DataManager> T getManager(Class<T> type){
		return (T) managermap.get(type);
	}


	public void save() {
		for(Class<? extends DataManager> mc : this.managermap.keySet()){
			if(ClassUtil.isImplemented(mc, UsingSql.class)){
				try {
					mc.getMethod("save").invoke(this.managermap.get(mc));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					plugin.getLogger().warning("Failed to save data of player:" + this.name);
					e.printStackTrace();
					plugin.getPluginLoader().disablePlugin(plugin);
				}
			}
		}
	}

	public void load() {
		for(Class<? extends DataManager> mc : this.managermap.keySet()){
			if(ClassUtil.isImplemented(mc, UsingSql.class)){
				try {
					mc.getMethod("load").invoke(this.managermap.get(mc));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					plugin.getLogger().warning("Failed to load data of player:" + this.name);
					e.printStackTrace();
					plugin.getPluginLoader().disablePlugin(plugin);
				}
			}
		}
	}




	/*
	//３０分間のデータを保存する．
	public MineBlock halfhourblock;




	//MineStack
	//public MineStack minestack;

	//public MineStack minestack = new MineStack();
	//MineStackFlag

	public boolean minestackflag;
	//プレイ時間差分計算用int
	public int servertick;
	//プレイ時間
	public int playtick;
	//キルログ表示トグル
	public boolean dispkilllogflag;
	//全体通知音消音トグル
	public boolean everysoundflag;
	//ワールドガード保護ログ表示トグル
	public boolean dispworldguardlogflag;
	//複数種類破壊トグル
	public boolean multipleidbreakflag;

	//PvPトグル
	public boolean pvpflag;

	//放置時間
	public int idletime;
	//トータル破壊ブロック
	public int totalbreaknum;
	//整地量バー
	public ExpBar expbar;
	//合計経験値
	public int totalexp;
	//経験値マネージャ
	public ExperienceManager expmanager;
	//合計経験値統合済みフラグ
	public byte expmarge;
	//各統計値差分計算用配列
	private List<Integer> staticdata;
	//特典受け取り済み投票数
	public int p_givenvote;
	//投票受け取りボタン連打防止用
	public boolean votecooldownflag;

	//アクティブスキル関連データ
	public ActiveSkillData activeskilldata;

	//MebiusTask
	public MebiusTaskRunnable mebius;

	//ガチャボタン連打防止用
	public boolean gachacooldownflag;

	//インベントリ共有トグル
	public boolean shareinv;
	//インベントリ共有ボタン連打防止用
	public boolean shareinvcooldownflag;

	//サブのホームポイント
	private Location[] sub_home = new Location[SeichiAssist.config.getSubHomeMax()];

	//LV・二つ名表示切替用
	public boolean displayTypeLv;
	//表示二つ名の指定用
	public int displayTitleNo;
	//二つ名解禁フラグ保存用
	public BitSet TitleFlags;
	//二つ名関連用にp_vote(投票数)を引っ張る。(予期せぬエラー回避のため名前を複雑化)
	public int p_vote_forT ;


	//建築LV
	private int build_lv;
	//設置ブロック数
	private int build_count;
	//設置ブロックサーバー統合フラグ
	private byte build_count_flg;
*/
}
