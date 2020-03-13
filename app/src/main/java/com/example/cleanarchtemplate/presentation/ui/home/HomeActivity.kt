package com.example.cleanarchtemplate.presentation.ui.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cleanarchtemplate.R
import com.example.cleanarchtemplate.presentation.ui.core.BaseActivity
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Main activity
 *
 * @author EvgenySamarin
 * @since 20190919 v11
 */
class HomeActivity : BaseActivity() {
    override val contentId: Int = R.layout.activity_home
    override var navGraphId: Int = R.navigation.nav_graph_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigationController()
    }

    private fun initNavigationController() {
        val navController = nav_host_fragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.main_dest,R.id.second_dest),
            drawer_layout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view?.let {
            it.setupWithNavController(navController)
            disableNavigationViewScrollbars(it)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            //BACKLOG handling navigation changes here
        }
    }

    private fun disableNavigationViewScrollbars(navigationView: NavigationView) {
        val navigationMenuView = navigationView.getChildAt(0) as? NavigationMenuView
        navigationMenuView?.let {
            it.isVerticalScrollBarEnabled = false
        }
    }
}