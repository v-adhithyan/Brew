package ceg.avtechlabs.brew.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ceg.avtechlabs.brew.R
import ceg.avtechlabs.brew.commons.features.BrewDataManager
import ceg.avtechlabs.brew.commons.utilities.inflate
import ceg.avtechlabs.brew.commons.utilities.loadImage
import ceg.avtechlabs.brew.model.Data
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
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

        showAd()
        showProgressBar()
        subscribe()

    }

    private fun subscribe() {
        val subscription = BrewDataManager().pour()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { brew -> updateView(brew.data) },
                        { e -> Log.d("brewdata", e.message)}
                )
        subscriptions.add(subscription)
    }

    private fun updateView(data: Data){
        activity.runOnUiThread {
            transformTextView()

            progress?.dismiss()

            textview_title.text = (data.title)
            textview_info.text = (data.info)
            imageView.loadImage(data.url)
        }
    }

    private fun showProgressBar() {
        progress?.setMessage("Brewing ...")
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
        adView.loadAd(adRequest)
    }

    private fun transformTextView() {
        val metrics = getResources().displayMetrics
        val dp = TypedValue.COMPLEX_UNIT_PX

        setQuiveraFont(textview_title, bold = true)
        setQuiveraFont(textview_info)

        textview_title.textSize = TypedValue.applyDimension(dp, 30f, metrics)
        textview_info.textSize = TypedValue.applyDimension(dp, 20f, metrics)
    }

    private fun setQuiveraFont(text: TextView, bold: Boolean = false) {
        var quivera = Typeface.createFromAsset(context.getAssets(), "quivira.otf")
        when { bold -> quivera = Typeface.create(quivera, Typeface.BOLD) }
        text.typeface = quivera
    }

    private val progress by lazy { ProgressDialog(context) }

}