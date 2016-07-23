package ceg.avtechlabs.brew

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.Menu
import android.view.MenuItem
import ceg.avtechlabs.brew.api.RestApi
import ceg.avtechlabs.brew.commons.extensions.loadImage
import ceg.avtechlabs.brew.commons.extensions.setQuiveraFont
import ceg.avtechlabs.brew.commons.features.BrewDataManager
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.avtechlabs.peacock.isInternetConnected
import com.avtechlabs.peacock.showLongToast
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /*if(isInternetConnected()){
            imageView.loadImage("http://www.bing.com/az/hprichbg/rb/BadlandsHeadlights_EN-US10459083918_1920x1080.jpg")
        }else{
            showLongToast("Connect to Internet!")
        }*/
        val progress = ProgressDialog(this)
        progress.setCancelable(false)
        progress.setMessage("Please wait")
        progress.show()


        val subscriptions = CompositeSubscription()
        val subscription = BrewDataManager().pour()
            .subscribeOn(Schedulers.io())
            .subscribe(
                    {
                       brew ->
                        progress.dismiss()
                        runOnUiThread {
                            textview_title.setQuiveraFont()
                            textview_info.setQuiveraFont()
                            textview_title.setText(brew.data.title)
                            textview_info.setText(brew.data.info)
                            imageView.loadImage(brew.data.url)
                        }

                    },
                    {
                        e ->
                        progress.dismiss()
                        showLongToast("error")
                    }

            )
        subscriptions.add(subscription)
        addBottomBar()

    }

    fun addBottomBar(){
        bottom_navigation_bar
                .addItem(BottomNavigationItem(R.drawable.ic_save_black_24dp, "Save"))
                .addItem(BottomNavigationItem(R.drawable.ic_wallpaper_black_24dp, "Set as wallpaper"))
                .addItem(BottomNavigationItem(R.drawable.ic_favorite_black_24dp, "Rate & Review"))
                .addItem(BottomNavigationItem(R.drawable.ic_autorenew_black_24dp, "Refresh"))
                .addItem(BottomNavigationItem(R.drawable.ic_feedback_black_24dp, "Feedback"))
                .initialise()
    }
}

