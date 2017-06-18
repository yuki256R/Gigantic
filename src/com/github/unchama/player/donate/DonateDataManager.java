package com.github.unchama.player.donate;

import java.util.ArrayList;
import java.util.List;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.sql.donate.DonateTableManager;

/**
 * Created by Mon_chi on 2017/06/07.
 */
public class DonateDataManager extends DataManager {

    private DonateTableManager tableManager;
    private List<DonateData> donateList;

    public DonateDataManager(GiganticPlayer gp) {
        super(gp);
        this.tableManager = sql.getManager(DonateTableManager.class);
        this.donateList = new ArrayList<>();
    }

    public void loadDonateData(DonateData data) {
        donateList.add(data);
    }

    public void putDonateData(DonateData data) {
        donateList.add(data);
        tableManager.saveDonateData(gp.uuid.toString().toLowerCase(), data);
    }

    public ArrayList<DonateData> getDonateList() {
        return new ArrayList<>(donateList);
    }
}
