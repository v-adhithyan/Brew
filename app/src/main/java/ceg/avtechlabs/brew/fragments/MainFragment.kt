package ceg.avtechlabs.brew.fragments

import android.app.ProgressDialog
import android.graphics.Typeface
import android.os.Bundle
import android.support.annotation.DimenRes
import android.support.annotation.IntegerRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ceg.avtechlabs.brew.R
import ceg.avtechlabs.brew.commons.features.BrewDataManager
import ceg.avtechlabs.brew.commons.utilities.*
import ceg.avtechlabs.brew.model.Data
import com.avtechlabs.peacock.showLongToast
import com.dd.morphingbutton.MorphingButton
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.content_brew.*
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.*

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
        hideButtons()
        showAd()
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
            var response = transformData(data.info)
            transformTextView()
            showButtons()
            setListeners()
            progress.dismiss()

            textview_title.text = (response.get(0))
            textview_info.text = (response.get(1))
            imageView.loadImage(data.url)
            startService(context, 10)
        }
    }

    private fun showProgressBar() {
        progress.setMessage("Brewing ...")
        progress.setCancelable(false)
        progress.show()
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
        setQuiveraFont(textview_title)
        setQuiveraFont(textview_info)
    }

    private fun setQuiveraFont(text: TextView) {
        var quivera = Typeface.createFromAsset(context.getAssets(), "quivira.otf")
        text.typeface = quivera
    }

    private fun setListeners(){
        button_save.setOnClickListener({
            val saved = imageView?.save(Date().toString()) ?: false
            when {
                saved -> {
                    button_save.morph(circle)
                    activity.showLongToast("Image saved to gallery.")
                }
                else -> activity.showLongToast("Unable to save image. Please try again after sometime.")
            }
        })

        button_set.setOnClickListener({
            imageView?.setWallpaper(activity)
            button_set.morph(circle)
        })
    }

    private val progress by lazy { ProgressDialog(context) }

    private fun hideButtons() {
        button_save.visibility = View.INVISIBLE
        button_set.visibility = View.INVISIBLE
    }

    private fun showButtons() {
        button_save.visibility = View.VISIBLE
        button_set.visibility = View.VISIBLE
    }

    private val circle by lazy {
        MorphingButton.Params.create()
        .duration(500)
        .cornerRadius(dimen(R.dimen.btn_dimen))
        .width(dimen(R.dimen.btn_dimen))
        .height(dimen(R.dimen.btn_dimen))
        .color(color(R.color.colorPrimary))
        .colorPressed(R.color.colorPrimaryDark)
        .icon(R.drawable.ic_done_white_24dp)
    }

    private fun integer(@IntegerRes resId: Int): Int {
        return resources.getInteger(resId)
    }

    private fun color(@IntegerRes resId: Int): Int {
        return resources.getColor(resId)
    }

    private fun dimen(@DimenRes resId: Int): Int{
        return resources.getDimension(resId).toInt()
    }

    private fun transformData(info: String): List<String> {
        val content = info.split("\n")

        return listOf(content.get(0), content.get(1))
    }
}