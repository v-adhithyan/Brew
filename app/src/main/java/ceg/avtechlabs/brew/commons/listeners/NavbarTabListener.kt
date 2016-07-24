package ceg.avtechlabs.brew.commons.listeners

import android.app.Activity
import android.widget.ImageView
import ceg.avtechlabs.brew.commons.utilities.save
import ceg.avtechlabs.brew.commons.utilities.setWallpaper
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.avtechlabs.peacock.showLongToast
import net.hockeyapp.android.FeedbackManager
import java.util.*

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
            position == 0 -> saveImage()
            position == 1 -> image?.setWallpaper(activity)
            position == 2 -> activity.showLongToast("Favorite")
            position == 3 -> activity.showLongToast("Refresh")
            position == 4 -> FeedbackManager.showFeedbackActivity(activity)
        }
    }

    private fun saveImage() {
        when {
            image?.save(Date().toString()) -> activity.showLongToast("Image saved to gallery.")
            else -> activity.showLongToast("Image saving failed")
        }
    }
}