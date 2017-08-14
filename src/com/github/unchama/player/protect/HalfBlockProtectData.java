package com.github.unchama.player.protect;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

/**
 * Y5ハーフブロック破壊可能フラグ保持用
 * 権限を持っている人のみtrueにすることが可能
 * @author karayuu
 */
public class HalfBlockProtectData extends DataManager {

    //フラグ
    private boolean halfBreakFlag;

    public HalfBlockProtectData(GiganticPlayer gp) {
        super(gp);
        halfBreakFlag = false;
    }

    public boolean canBreakHalfBlock() {
        return halfBreakFlag;
    }

    public void toggleHalfBreakFlag() {
        if (halfBreakFlag) {
            halfBreakFlag = false;
        } else {
            halfBreakFlag = true;
        }
    }
}
