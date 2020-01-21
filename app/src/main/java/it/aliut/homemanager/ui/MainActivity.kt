package it.aliut.homemanager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import it.aliut.homemanager.R

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.main_navhostfragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupActionBarWithNavController(
            this,
            navController
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}
