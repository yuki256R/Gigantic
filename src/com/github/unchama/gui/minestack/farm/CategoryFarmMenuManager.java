package com.github.unchama.gui.minestack.farm;

<<<<<<< Updated upstream
/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryFarmMenuManager {
=======
import com.github.unchama.enumdata.StackCategory;
import com.github.unchama.gui.moduler.MineStackMenuManager;

/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryFarmMenuManager extends MineStackMenuManager {

    public CategoryFarmMenuManager(){
        super();
    }

    @Override
    public StackCategory getCategory() {
        return StackCategory.FARM;
    }
>>>>>>> Stashed changes
}
