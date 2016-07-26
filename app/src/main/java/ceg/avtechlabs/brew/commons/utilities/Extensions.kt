package ceg.avtechlabs.brew.commons.utilities

import android.app.Activity
import android.app.WallpaperManager
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import ceg.avtechlabs.brew.R
import ceg.avtechlabs.brew.fragments.MainFragment
import ceg.avtechlabs.brew.fragments.NoNetworkFragment
import com.avtechlabs.peacock.isInternetConnected
import com.avtechlabs.peacock.showLongToast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_main.view.*

/**
 * Created by adhithyan-3592 on 23/07/16.
 */

fun ImageView.loadImage(url: String){
    if(TextUtils.isEmpty(url))
        Picasso.with(context).load(R.mipmap.ic_launcher).into(this)
    else
        Picasso.with(context).load(url).placeholder(R.drawable.progress_animation).into(this)
}


fun Activity.onMainThread(func: () -> Unit) {
    runOnUiThread { func() }
}


fun ImageView.setWallpaper(activity: Activity) {
    val wallpaperSetter = Runnable {
        val wallpaperManager = WallpaperManager.getInstance(context)
        val bitmap = (drawable as BitmapDrawable).bitmap
        wallpaperManager.setBitmap(bitmap)
        activity.onMainThread {  activity.showLongToast("Wallpaper set");  }
    }
    Thread(wallpaperSetter).start()
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun changeFragment(activity: Activity, supportFragmentManager: FragmentManager) {
    var ft = supportFragmentManager.beginTransaction()
    ft.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit)

    if(activity.isInternetConnected()) {
        ft.replace(R.id.fragment_container, MainFragment())
    } else {
        ft.replace(R.id.fragment_container, NoNetworkFragment())
    }

    ft.commit()
}
