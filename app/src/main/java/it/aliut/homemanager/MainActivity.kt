package it.aliut.homemanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.aliut.homemanager.ui.deviceslist.DevicesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DevicesListFragment.newInstance())
                .commitNow()
        }
    }

}
