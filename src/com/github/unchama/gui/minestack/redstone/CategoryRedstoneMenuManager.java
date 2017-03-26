package com.github.unchama.gui.minestack.redstone;

<<<<<<< Updated upstream
/**
 * Created by Mon_chi on 2017/03/25.
 */
public class CategoryRedstoneMenuManager {
=======
import com.github.unchama.enumdata.StackCategory;
import com.github.unchama.gui.moduler.MineStackMenuManager;

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
>>>>>>> Stashed changes
}
