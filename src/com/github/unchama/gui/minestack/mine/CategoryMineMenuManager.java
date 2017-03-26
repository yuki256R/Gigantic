package com.github.unchama.gui.minestack.mine;

<<<<<<< Updated upstream
/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryMineMenuManager {
=======
import com.github.unchama.enumdata.StackCategory;
import com.github.unchama.gui.moduler.MineStackMenuManager;

/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryMineMenuManager extends MineStackMenuManager{

    public CategoryMineMenuManager(){
        super();
    }

    @Override
    public StackCategory getCategory() {
        return StackCategory.MINE;
    }
>>>>>>> Stashed changes
}
