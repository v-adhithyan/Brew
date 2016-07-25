package ceg.avtechlabs.brew.commons.utilities

import com.ashokvarma.bottomnavigation.BottomNavigationBar

/**
 * Created by adhithyan-3592 on 25/07/16.
 */

public class Common private constructor() {
    private object Holder { val INSTANCE = Common() }

    companion object {
        val instance: Common by lazy { Holder.INSTANCE }
    }

    var bottomBar: BottomNavigationBar? = null
}
