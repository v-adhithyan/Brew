package ceg.avtechlabs.brew.fragments

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ceg.avtechlabs.brew.R
import ceg.avtechlabs.brew.commons.listeners.NavBarTabListener
import ceg.avtechlabs.brew.commons.utilities.Common
import ceg.avtechlabs.brew.commons.utilities.inflate
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_brew.*
import kotlinx.android.synthetic.main.content_network_unavailable.*

/**
 * Created by adhithyan-3592 on 25/07/16.
 */

class NoNetworkFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.content_network_unavailable)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addBottomBar()
        rippleView.startRippleAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun addBottomBar() {
        var bottomBar = Common.bottomBar
        bottomBar?.clearAll()
        bottomBar
                ?.addItem(BottomNavigationItem(R.drawable.ic_autorenew_black_24dp, "Refresh"))
                ?.initialise()
        bottomBar?.setTabSelectedListener(NavBarTabListener(context as Activity, imageView, noNetwork = true, fragmentManager = fragmentManager))
    }
}