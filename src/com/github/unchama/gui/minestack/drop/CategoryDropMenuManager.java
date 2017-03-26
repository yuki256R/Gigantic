<<<<<<< Updated upstream
package com.github.unchama.gui.minestack.mine.drop;
=======
package com.github.unchama.gui.minestack.drop;

import com.github.unchama.enumdata.StackCategory;
import com.github.unchama.gui.moduler.MineStackMenuManager;
>>>>>>> Stashed changes

/**
 * Created by Mon_chi on 2017/03/25.
 */
<<<<<<< Updated upstream
public class CategoryDropMenuManager {
}
=======
public class CategoryDropMenuManager extends MineStackMenuManager {

    public CategoryDropMenuManager(){
        super();
    }

    @Override
    public StackCategory getCategory() {
        return StackCategory.DROP;
    }
}
>>>>>>> Stashed changes
