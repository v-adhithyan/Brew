package ceg.avtechlabs.brew.commons.listeners

import android.app.Activity
import android.widget.ImageView
import ceg.avtechlabs.brew.commons.extensions.save
import ceg.avtechlabs.brew.commons.extensions.setWallpaper
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.avtechlabs.peacock.showLongToast

/**
 * Created by adhithyan-3592 on 23/07/16.
 */


class NavBarTabListener(val activity: Activity, val image: ImageView ): BottomNavigationBar.OnTabSelectedListener {
    override fun onTabUnselected(position: Int) {

    }

    override fun onTabSelected(position: Int) {
        doAction(position)
    }

    override fun onTabReselected(position: Int) {
        doAction(position)
    }

    private fun doAction(position: Int) {
        when {
            position == 0 -> image.save()
            position == 1 -> image.setWallpaper(activity)
            position == 2 -> activity.showLongToast("Favorite")
            position == 3 -> activity.showLongToast("Refresh")
            position == 4 -> activity.showLongToast("Feedback")
        }
    }
}