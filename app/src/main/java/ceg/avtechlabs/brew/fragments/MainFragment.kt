package ceg.avtechlabs.brew.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ceg.avtechlabs.brew.R
import ceg.avtechlabs.brew.commons.features.BrewDataManager
import ceg.avtechlabs.brew.commons.listeners.NavBarTabListener
import ceg.avtechlabs.brew.commons.utilities.Common
import ceg.avtechlabs.brew.commons.utilities.inflate
import ceg.avtechlabs.brew.commons.utilities.loadImage
import ceg.avtechlabs.brew.model.Data
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_brew.*
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by adhithyan-3592 on 25/07/16.
 */

class MainFragment: Fragment() {

    protected var subscriptions = CompositeSubscription()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.content_brew)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showProgressBar()
        subscribe()
        addBottomBar()
    }

    private fun addBottomBar() {
        var bottomBar = Common.bottomBar
        bottomBar?.clearAll()
        bottomBar
                ?.addItem(BottomNavigationItem(R.drawable.ic_save_black_24dp, "Save"))
                ?.addItem(BottomNavigationItem(R.drawable.ic_wallpaper_black_24dp, "Set as wallpaper"))
                ?.addItem(BottomNavigationItem(R.drawable.ic_favorite_black_24dp, "Rate & Review"))
                ?.addItem(BottomNavigationItem(R.drawable.ic_feedback_black_24dp, "Feedback"))
                ?.initialise()
        bottomBar?.setTabSelectedListener(NavBarTabListener(context as Activity, imageView, fragmentManager = fragmentManager))
    }

    private fun subscribe() {
        val subscription = BrewDataManager().pour()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { brew -> updateView(brew.data) },
                        { e -> }
                )
        subscriptions.add(subscription)
    }

    private fun updateView(data: Data){
        //runOnUiThread {
            setQuiveraFont(textview_title, bold = true)
            setQuiveraFont(textview_info)

            progress?.dismiss()

            textview_title.setText(data.title)
            textview_info.setText(data.info)
            imageView.loadImage(data.url)
            showAd()
        //}
    }

    private fun showProgressBar() {
        progress?.setTitle("Brewing ...")
        progress?.setCancelable(false)
        progress?.show()
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

    private fun showAd() {
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
    }

    private fun setQuiveraFont(text: TextView, bold: Boolean = false) {
        var quivera = Typeface.createFromAsset(context.getAssets(), "quivira.otf")
        when { bold -> quivera = Typeface.create(quivera, Typeface.BOLD) }
        text.typeface = quivera
    }

    private val progress by lazy { ProgressDialog(context) }

}