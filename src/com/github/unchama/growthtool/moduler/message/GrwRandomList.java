package com.github.unchama.growthtool.moduler.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.unchama.growthtool.GrowthTool;

/**
 * ランダム取得、重み付きランダム取得、及びディープコピー機能付きのArrayList。ユーティリティクラス。<br />
 * オリジナルのArrayListを継承し、ランダム取得機能とメンバーのディープコピーを実施。<br />
 * またランダム取得の際に重みリストを与えることで、選択率を設定することが可能。<br />
 * プリミティブな不変オブジェクトでNotCloneableなメンバー―クラスを対象としている。例: String<br />
 * Cloneableオブジェクトの場合は別途非プリミティブ可変オブジェクト対応ディープコピーの実装を推奨。<br />
 *
 * @param <T> 対象Listの型
 *
 * @author CrossHearts
 */
public class GrwRandomList<T> extends ArrayList<T> implements Cloneable {
	// ランダム取得用乱数インスタンス
	private static final Random random = new Random();

	/**
	 * 空リスト用コンストラクタ。emptyリストを生成する際に呼び出される。<br />
	 */
	public GrwRandomList() {
		super();
	}

	/**
	 * リスト型初期値付きインスタンスを生成するコンストラクタ。既存のリストをGrwRandomList化する際に呼び出される。<br />
	 *
	 * @param list
	 */
	public GrwRandomList(List<T> list) {
		super(list);
	}

	/**
	 * ランダム取得メソッド。このリストのメンバーからランダムに1つ取得し、返却する。<br />
	 *
	 * @return ランダムに選択されたメンバー <null: 未登録状態での呼び出し(仕様)>
	 */
	public T getRandom() {
		if (size() > 0) {
			return get(random.nextInt(size()));
		} else {
			return null;
		}
	}

	/**
	 * 重み付きランダム取得メソッド。このリストのメンバーからランダムに1つ取得し、返却する。<br />
	 * 取得の際は引数で指定されたweightに準じ、weightが大きいほど選択されやすい。<br />
	 * weightはこのリストのメンバー数と同じメンバー数を持つ必要がある。<br />
	 *
	 * @param weight 選択率の重みづけ
	 * @return 重み付きランダムで選択されたメンバー <null: 未登録状態での呼び出し(仕様)>
	 */
	public T getRandom(List<Integer> weight) {
		if (size() <= 0) {
			return null;
		}
		if (size() != weight.size()) {
			GrowthTool.GrwDebugWarning("weightの要素数がインスタンスの要素数と一致しません。");
			return null;
		}
		int sum = 0;
		for (int w : weight) {
			sum += w;
		}
		int key = random.nextInt(sum);
		sum = 0;
		for (int index = 0; index <= weight.size(); index++) {
			if ((sum += weight.get(index)) > key) {
				return get(index);
			}
		}
		GrowthTool.GrwDebugWarning("想定外の異常です。");
		return null;
	}

	/**
	 * このインスタンスに対してディープコピーを生成するためのcloneメソッド。<br />
	 * プリミティブな不変オブジェクトでNotCloneableなメンバーを想定しているため、直接addによるコピーでディープコピーとなる。<br />
	 *
	 * @return このインスタンスのディープコピーインスタンス
	 */
	@Override
	public GrwRandomList<T> clone() {
		GrwRandomList<T> clone = new GrwRandomList<T>();
		for (T member : this) {
			clone.add(member);
		}
		return clone;
	}
}
