package it.aliut.homemanager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.aliut.homemanager.R
import it.aliut.homemanager.ui.devicedetails.DeviceDetailsFragment

class DeviceDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DEVICE_ID = "device_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devicedetails)
        if (savedInstanceState == null) {
            val deviceId = intent?.extras?.getString(EXTRA_DEVICE_ID) ?: ""

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DeviceDetailsFragment.newInstance(deviceId))
                .commitNow()
        }
    }

}
