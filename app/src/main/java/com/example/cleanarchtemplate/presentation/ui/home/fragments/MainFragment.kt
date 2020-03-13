package com.example.cleanarchtemplate.presentation.ui.home.fragments

import android.os.Bundle
import android.view.View
import com.example.cleanarchtemplate.R
import com.example.cleanarchtemplate.presentation.ui.core.BaseFragment
import com.example.cleanarchtemplate.presentation.ui.core.extension.onTabSelected
import com.example.cleanarchtemplate.presentation.ui.home.fragments.main.adapters.MainPageAdapter
import com.example.cleanarchtemplate.presentation.viewmodel.common.ToolbarSharedViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home_main.*

/**
 * Example work with tabs
 *
 * @author EvgenySamarin
 * @since 20190919 v5
 */
class MainFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_home_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pager.adapter = MainPageAdapter(childFragmentManager, tabs_main.tabCount)
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs_main))

        tabs_main.onTabSelected {
            pager.currentItem = it.position
        }
    }
}