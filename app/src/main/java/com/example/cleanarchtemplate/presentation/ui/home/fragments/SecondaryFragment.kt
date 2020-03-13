package com.example.cleanarchtemplate.presentation.ui.home.fragments

import android.os.Bundle
import com.example.cleanarchtemplate.R
import com.example.cleanarchtemplate.presentation.ui.core.BaseFragment
import com.example.cleanarchtemplate.presentation.viewmodel.common.ToolbarSharedViewModel


/**
 * Отвечает за отображение графика посещений ребёнка
 *
 * @author EvgenySamarin
 * @since 20191129 v5
 */
class SecondaryFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_home_secondary

    private lateinit var toolbarSharedViewModel: ToolbarSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        base { toolbarSharedViewModel = getViewModel() }
    }

    override fun onResume() {
        super.onResume()
        if (::toolbarSharedViewModel.isInitialized)
            toolbarSharedViewModel.updateTitle(getString(R.string.TITLE_TOOLBAR_SECONDARY))

    }
}
