package ceg.avtechlabs.brew.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.view.*
import ceg.avtechlabs.brew.R
import ceg.avtechlabs.brew.commons.utilities.changeFragment
import ceg.avtechlabs.brew.commons.utilities.inflate
import kotlinx.android.synthetic.main.content_network_unavailable.*

/**
 * Created by adhithyan-3592 on 25/07/16.
 */

class NoNetworkFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return container?.inflate(R.layout.content_network_unavailable)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when {
            id == R.id.action_refresh -> { changeFragment(activity, fragmentManager) }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setListeners() {
        turnOnMobileData()
        turnOnWifi()
    }

    fun turnOnMobileData() {
        button_md.setOnClickListener({
            val intent = Intent(Settings.ACTION_SETTINGS)
            startActivity(intent)
        })
    }

    fun turnOnWifi() {
        button_wifi.setOnClickListener({
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(intent)
        })
    }
}