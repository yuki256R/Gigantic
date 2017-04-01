package com.github.unchama.util;

import java.lang.reflect.Modifier;

public class ClassUtil {


	/**clazzがintrfcを実装したクラスであるか確認します．
	 *
	 * @param clazz
	 * @param intrfc
	 * @return
	 */
	public static boolean isImplemented(Class<?> clazz, Class<?> intrfc) {
        if (clazz == null || intrfc == null) {
            return false;
        }
        // インターフェースを実装したクラスであるかどうかをチェック
        if (!clazz.isInterface() && intrfc.isAssignableFrom(clazz)
                && !Modifier.isAbstract(clazz.getModifiers())) {
            return true;
        }
        return false;
    }
}
