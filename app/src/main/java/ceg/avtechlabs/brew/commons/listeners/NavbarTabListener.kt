package ceg.avtechlabs.brew.commons.listeners

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v4.app.FragmentManager
import android.widget.ImageView
import ceg.avtechlabs.brew.commons.utilities.changeFragment
import ceg.avtechlabs.brew.commons.utilities.save
import ceg.avtechlabs.brew.commons.utilities.setWallpaper
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.avtechlabs.peacock.showLongToast
import net.hockeyapp.android.FeedbackManager
import java.util.*

/**
 * Created by adhithyan-3592 on 23/07/16.
 */


class NavBarTabListener(val activity: Activity, val image: ImageView?, val noNetwork: Boolean = false, val fragmentManager: FragmentManager): BottomNavigationBar.OnTabSelectedListener {
    override fun onTabUnselected(position: Int) {

    }

    override fun onTabSelected(position: Int) {
        selectAction(position)
    }

    override fun onTabReselected(position: Int) {
        selectAction(position)
    }

    private fun selectAction(position: Int) {
        if(noNetwork) {
            refresh()
        }else {
            doAction(position)
        }
    }

    private fun doAction(position: Int) {
        when {
            position == 0 -> saveImage()
            position == 1 -> image?.setWallpaper(activity)
            position == 2 -> rate()
            position == 3 -> FeedbackManager.showFeedbackActivity(activity)
        }
    }

    private fun saveImage() {
        val saved = image?.save(Date().toString()) ?: false
        when {
            saved -> activity.showLongToast("Image saved to gallery.")
            else -> activity.showLongToast("Image saving failed")
        }
    }

    private fun rate() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("market://details?id=ceg.avtechlabs.brew"))
        activity.startActivity(intent)
    }

    private fun refresh() {
        changeFragment(activity, fragmentManager)
    }

}