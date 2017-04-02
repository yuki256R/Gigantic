package com.github.unchama.gui.minestack.material;

import com.github.unchama.gui.moduler.MineStackMenuManager;
import com.github.unchama.gui.moduler.StackCategory;

/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryMaterialMenuManager extends MineStackMenuManager{

    public CategoryMaterialMenuManager(){
        super();
    }

    @Override
    public StackCategory getCategory() {
        return StackCategory.MATERIAL;
    }
}
