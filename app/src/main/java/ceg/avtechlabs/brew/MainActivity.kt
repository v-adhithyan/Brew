package ceg.avtechlabs.brew

import android.Manifest
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
import android.widget.ProgressBar
import ceg.avtechlabs.brew.api.RestApi
import ceg.avtechlabs.brew.commons.extensions.loadImage
import ceg.avtechlabs.brew.commons.extensions.setQuiveraFont
import ceg.avtechlabs.brew.commons.extensions.update
import ceg.avtechlabs.brew.commons.features.BrewDataManager
import ceg.avtechlabs.brew.model.Data
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.avtechlabs.peacock.checkAndAskPermission
import com.avtechlabs.peacock.isInternetConnected
import com.avtechlabs.peacock.showLongToast
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import net.hockeyapp.android.CrashManager
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription


class MainActivity : AppCompatActivity() {

    protected var subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        subscribe()
        addBottomBar()
        checkAndAskPermission(permissionsList)
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


    fun checkForCrashes() {
        CrashManager.register(this)
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        if(!subscriptions.isUnsubscribed) {
            subscriptions.unsubscribe()
        }
        subscriptions.clear()
    }

    fun subscribe() {
        val subscription = BrewDataManager().pour()
                .subscribeOn(Schedulers.io())
                .subscribe(
                            { brew -> updateView(brew.data) },
                            { e -> }
                )
        subscriptions.add(subscription)
    }

    fun updateView(data: Data){
        runOnUiThread {

        }
    }

    private val progressBar by lazy {
        ProgressBar(this)
    }

    private val permissionsList by lazy {
        listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).toTypedArray()
    }

}

