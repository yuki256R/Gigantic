package com.github.unchama.gui.minestack.redstone;

import com.github.unchama.gui.moduler.MineStackMenuManager;
import com.github.unchama.gui.moduler.StackCategory;

/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryRedstoneMenuManager extends MineStackMenuManager {

    public CategoryRedstoneMenuManager(){
        super();
    }

    @Override
    public StackCategory getCategory() {
        return StackCategory.REDSTONE;
    }
}
