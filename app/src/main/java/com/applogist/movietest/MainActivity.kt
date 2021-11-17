package com.applogist.movietest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.applogist.movietest.base.BaseActivity
import com.applogist.movietest.databinding.ActivityMainBinding
import com.applogist.movietest.utils.viewBinding

class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController
    override fun prepareView(savedInstanceState: Bundle?) {
        setUpNavigation()
    }

    override fun getRootView(): View {
        return binding.root
    }

    private fun setUpNavigation() {
        val navHostFragment = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)

    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {

            R.id.movieDetailFragment -> {
            }

            R.id.movieSearchFragment -> {
            }
        }
    }

}