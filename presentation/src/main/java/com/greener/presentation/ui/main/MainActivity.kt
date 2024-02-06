package com.greener.presentation.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.greener.presentation.R
import com.greener.presentation.databinding.ActivityMainBinding
import com.greener.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    private lateinit var navController: NavController
    private val destinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            setBottomNavigationVisibility(destination)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBottomNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }

    private fun setUpBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_main) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationViewMain.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    private fun setBottomNavigationVisibility(destination: NavDestination) {
//        binding.bottomNavigationView.isVisible = exceptBottomNavigationSet.contains(destination.id).not()
    }
}
