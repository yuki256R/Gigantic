package com.github.unchama.gui.minestack.otherwise;

import com.github.unchama.gui.moduler.MineStackMenuManager;
import com.github.unchama.gui.moduler.StackCategory;

/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryOtherwiseMenuManager extends MineStackMenuManager {

    public CategoryOtherwiseMenuManager(){
        super();
    }

    @Override
    public StackCategory getCategory() {
        return StackCategory.OTHERWISE;
    }
}
