package com.abhishek.gitexplorer.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.abhishek.gitexplorer.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_base)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupActionBarWithNavController(findNavController(R.id.fragment_nav_host))
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_nav_host).navigateUp()
    }
}
