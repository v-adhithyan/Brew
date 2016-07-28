package ceg.avtechlabs.brew

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ceg.avtechlabs.brew.commons.utilities.changeFragment
import com.avtechlabs.peacock.checkAndAskPermission
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        changeFragment(this, supportFragmentManager)
        checkAndAskPermission(permissionsList)
    }

    private val permissionsList by lazy {
        listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).toTypedArray()
    }

}