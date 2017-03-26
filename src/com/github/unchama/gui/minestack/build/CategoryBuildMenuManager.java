package com.github.unchama.gui.minestack.build;

<<<<<<< Updated upstream
/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryBuildMenuManager {
=======
import com.github.unchama.enumdata.StackCategory;
import com.github.unchama.gui.moduler.MineStackMenuManager;

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
>>>>>>> Stashed changes
}
