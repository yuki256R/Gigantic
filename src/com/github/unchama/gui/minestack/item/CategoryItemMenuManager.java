package com.github.unchama.gui.minestack.item;

import com.github.unchama.gui.moduler.MineStackMenuManager;
import com.github.unchama.gui.moduler.StackCategory;

/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryItemMenuManager extends MineStackMenuManager {

    public CategoryItemMenuManager() {
        super();
    }

    @Override
    public StackCategory getCategory() {
        return StackCategory.ITEM;
    }
}
