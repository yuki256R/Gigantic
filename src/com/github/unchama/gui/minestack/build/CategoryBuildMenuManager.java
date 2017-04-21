package com.github.unchama.gui.minestack.build;

import com.github.unchama.gui.moduler.MineStackMenuManager;
import com.github.unchama.gui.moduler.StackCategory;

/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryBuildMenuManager extends MineStackMenuManager {

    public CategoryBuildMenuManager(){
        super();
    }

    @Override
    public StackCategory getCategory() {
        return StackCategory.BUILD;
    }
}
