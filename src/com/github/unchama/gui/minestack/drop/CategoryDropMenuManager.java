package com.github.unchama.gui.minestack.drop;

import com.github.unchama.enumdata.StackCategory;
import com.github.unchama.gui.moduler.MineStackMenuManager;

/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryDropMenuManager extends MineStackMenuManager {

    public CategoryDropMenuManager() {
        super();
    }

    @Override
    public StackCategory getCategory() {
        return StackCategory.DROP;
    }
}
