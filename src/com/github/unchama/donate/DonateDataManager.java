package com.github.unchama.donate;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.sql.donate.DonateTableManager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mon_chi on 2017/06/07.
 */
public class DonateDataManager extends DataManager {

    private DonateTableManager tableManager;
    private Map<LocalDateTime, DonateData> donateMap;

    public DonateDataManager(GiganticPlayer gp) {
        super(gp);
        this.tableManager = sql.getManager(DonateTableManager.class);
        this.donateMap = new HashMap<>();
    }

    public void loadDonateData(LocalDateTime time, DonateData data) {
        donateMap.put(time, data);
    }

    public void putDonateData(DonateData data) {
        donateMap.put(LocalDateTime.now(), data);
        tableManager.saveDonateData(gp.uuid.toString().toLowerCase(), data);
    }
}
