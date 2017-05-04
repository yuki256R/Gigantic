package com.github.unchama.util;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

/**
 * Mobheadの追加方法 1.giveコマンドで使われるvalue:以降のデータをコピー（以下，素材集リンク）
 * http://heads.freshcoal.com/maincollection.php http://www.freshcoal.com/heads/
 *
 * 2.コピーしたデータを以下URLでデコード https://www.base64decode.org/
 *
 * 3.得られたurlをURLMAPの初期化処理内に追加
 *
 * @author tar0ss
 *
 */
public class MobHead {
	private static HashMap<String, String> URLMap = new HashMap<String, String>() {
		{
			put("left",
					"http://textures.minecraft.net/texture/3ebf907494a935e955bfcadab81beafb90fb9be49c7026ba97d798d5f1a23");
			put("right",
					"http://textures.minecraft.net/texture/1b6f1a25b6bc199946472aedb370522584ff6f4e83221e5946bd2e41b5ca13b");
			put("up",
					"http://textures.minecraft.net/texture/d48b768c623432dfb259fb3c3978e98dec111f79dbd6cd88f21155374b70b3c");
			put("down",
					"http://textures.minecraft.net/texture/2dadd755d08537352bf7a93e3bb7dd4d733121d39f2fb67073cd471f561194dd");
			put("grass",
					"http://textures.minecraft.net/texture/349c63bc508723328a19e597f40862d27ad5c1d545663ac24466582f568d9");
			put("zero",
					"http://textures.minecraft.net/texture/0ebe7e5215169a699acc6cefa7b73fdb108db87bb6dae2849fbe24714b27");
			put("one",
					"http://textures.minecraft.net/texture/71bc2bcfb2bd3759e6b1e86fc7a79585e1127dd357fc202893f9de241bc9e530");
			put("two",
					"http://textures.minecraft.net/texture/4cd9eeee883468881d83848a46bf3012485c23f75753b8fbe8487341419847");
			put("three",
					"http://textures.minecraft.net/texture/1d4eae13933860a6df5e8e955693b95a8c3b15c36b8b587532ac0996bc37e5");
			put("four",
					"http://textures.minecraft.net/texture/d2e78fb22424232dc27b81fbcb47fd24c1acf76098753f2d9c28598287db5");
			put("five",
					"http://textures.minecraft.net/texture/6d57e3bc88a65730e31a14e3f41e038a5ecf0891a6c243643b8e5476ae2");
			put("six",
					"http://textures.minecraft.net/texture/334b36de7d679b8bbc725499adaef24dc518f5ae23e716981e1dcc6b2720ab");
			put("seven",
					"http://textures.minecraft.net/texture/6db6eb25d1faabe30cf444dc633b5832475e38096b7e2402a3ec476dd7b9");
			put("eight",
					"http://textures.minecraft.net/texture/59194973a3f17bda9978ed6273383997222774b454386c8319c04f1f4f74c2b5");
			put("nine",
					"http://textures.minecraft.net/texture/e67caf7591b38e125a8017d58cfc6433bfaf84cd499d794f41d10bff2e5b840");
			put("letterbox",
					"http://textures.minecraft.net/texture/70799f5effc891e334faa7f45d0bdd956d4e215d8a7ad55c310941aa96723");
			put("pouch",
					"http://textures.minecraft.net/texture/c6e69b1c7e69bcd49ed974f5ac36ea275efabb8c649cb2b1fe9d6ea6166ec3");
			put("pc",
					"http://textures.minecraft.net/texture/8d19c68461666aacd7628e34a1e2ad39fe4f2bde32e231963ef3b35533");
			put("red_present",
					"http://textures.minecraft.net/texture/f0afa4fffd10863e76c698da2c9c9e799bcf9ab9aa37d8312881734225d3ca");
			put("blue_bottle",
					"http://textures.minecraft.net/texture/d5f3602ec661c7f4beec88c12a5297d877822b8861920bacf49ae2077bb2b3");
			put("green_bottle",
					"http://textures.minecraft.net/texture/6daaea616947e00d97f8caa358261fa83e99d65527b8ef522cceffefa38f7");
			put("green_present",
					"http://textures.minecraft.net/texture/5a646f3ed5423b68b47b858c33331c982065ea1dd6272952184b64584f5191d");
			put("red_house",
					"http://textures.minecraft.net/texture/c5a35b5ca15268685c4660535e5883d21a5ec57c55d397234269acb5dc2954f");
			put("mechanic_box",
					"http://textures.minecraft.net/texture/23d3908722e8275ca195d2f49ed4f1c988bd27d4fc4a5993acfc392ac2c2c");
			put("orange",
					"http://textures.minecraft.net/texture/fba8eabaf9c34eb4b9a53199213b6657637b7a5f121986b52f7dd25a584c26d");
			put("corn",
					"http://textures.minecraft.net/texture/99dfac171e3c2029676ecf8d3a0fd9b7bb2857b95efdefc59e0f252576b5c68");
			put("riceball",
					"http://textures.minecraft.net/texture/69c2ddf2bd74a4655e8f0153a7453e67db2a21dbfac6756789481adbec483a");
			put("grape",
					"http://textures.minecraft.net/texture/f71cc917159f14565f6afe22b9e33d5a23e423ecd8cb5dfba0b3f66d825389ca");
			put("tomato",
					"http://textures.minecraft.net/texture/ec29b6926e25a7c725e959648bd4f2a8eee3f2921cbdc45d399df62786a77c");
			put("milk_chocolate",
					"http://textures.minecraft.net/texture/819f948d17718adace5dd6e050c586229653fef645d7113ab94d17b639cc466");
			put("sushi_roll",
					"http://textures.minecraft.net/texture/2e12f267953e76ae66a8dd025a3286aecbc64b4ad98eeb10b3c67a69aae15");
		}

	};

	private static HashMap<String, ItemStack> mobMap = new HashMap<String, ItemStack>() {
		{
			URLMap.forEach((n, url) -> {
				put(n, getSkull(url));
			});
		}
	};

	/**
	 * 与えられた文字に合致する頭を取得する．
	 *
	 * @param s
	 * @return
	 */
	public static ItemStack getMobHead(String s) {
		ItemStack ans = mobMap.get(s);
		if (ans == null) {
			Bukkit.getServer().getLogger()
					.warning(s + "という名前のMobHeadは見つかりません．");
			return new ItemStack(mobMap.get("grass"));
		} else {
			return new ItemStack(ans);
		}
	}

	/**
	 * 与えられた文字に合致する頭のURLを取得する．
	 *
	 * @param s
	 * @return
	 */
	public static String getMobURL(String s) {
		return URLMap.get(s);
	}

	/**
	 * 与えられたMOBHEADにURLを付与する．
	 *
	 * @param skull
	 * @param url
	 */
	public static void setURL(ItemStack skull, String url) {
		ItemMeta meta = skull.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.getEncoder()
				.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url)
						.getBytes());
		profile.getProperties().put("textures",
				new Property("textures", new String(encodedData)));
		Field profileField;
		try {
			profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException
				| IllegalAccessException e1) {
			e1.printStackTrace();
		}
		skull.setItemMeta(meta);
	}

	// カスタム頭取得
	private static ItemStack getSkull(String url) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		setURL(skull, url);
		return skull;
	}
}
