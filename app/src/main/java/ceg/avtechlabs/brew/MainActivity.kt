package ceg.avtechlabs.brew

import android.Manifest
import android.app.ProgressDialog
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import ceg.avtechlabs.brew.api.RestApi
import ceg.avtechlabs.brew.commons.utilities.loadImage
import ceg.avtechlabs.brew.commons.features.BrewDataManager
import ceg.avtechlabs.brew.commons.listeners.NavBarTabListener
import ceg.avtechlabs.brew.commons.utilities.Common
import ceg.avtechlabs.brew.commons.utilities.changeFragment
import ceg.avtechlabs.brew.fragments.MainFragment
import ceg.avtechlabs.brew.fragments.NoNetworkFragment
import ceg.avtechlabs.brew.model.Data
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.avtechlabs.peacock.checkAndAskPermission
import com.avtechlabs.peacock.isInternetConnected
import com.avtechlabs.peacock.showLongToast
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_brew.*
import kotlinx.android.synthetic.main.content_main.*
import net.hockeyapp.android.CrashManager
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Common.bottomBar = bottom_navigation_bar
        changeFragment(this, supportFragmentManager)
        checkAndAskPermission(permissionsList)
    }

    private fun checkForCrashes() {
        CrashManager.register(this)
    }


    private val permissionsList by lazy {
        listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).toTypedArray()
    }

}