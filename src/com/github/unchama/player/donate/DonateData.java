package com.github.unchama.player.donate;

import java.time.LocalDateTime;

/**
 * Created by Mon_chi on 2017/06/07.
 */
public class DonateData {

    public LocalDateTime time;
    public int money;
    public int point;

    public DonateData(LocalDateTime time, int money, int point) {
        this.time = time;
        this.money = money;
        this.point = point;
    }
}
