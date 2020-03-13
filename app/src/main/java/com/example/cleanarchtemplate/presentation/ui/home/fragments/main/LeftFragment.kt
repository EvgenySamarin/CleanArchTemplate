package com.example.cleanarchtemplate.presentation.ui.home.fragments.main

import android.os.Bundle
import android.view.View
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity
import com.example.cleanarchtemplate.presentation.ui.App
import com.example.cleanarchtemplate.presentation.ui.core.BaseListFragment
import com.example.cleanarchtemplate.presentation.ui.core.extension.onFailure
import com.example.cleanarchtemplate.presentation.ui.core.extension.onSuccess
import com.example.cleanarchtemplate.presentation.ui.home.fragments.main.adapters.LeftAdapter
import com.example.cleanarchtemplate.presentation.viewmodel.example.ExampleViewModel

/**
 * Example work with list
 *
 * @author EvgenySamarin
 * @since 20190924 v6
 */
class LeftFragment : BaseListFragment() {
    override val viewAdapter = LeftAdapter()

    private lateinit var exampleViewModel: ExampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        exampleViewModel = viewModel {
            onSuccess(exampleData,::handleExampleData)
            onFailure(failureData, ::handleFailure)
        }
    }

    private fun handleExampleData(list: List<ExampleEntity>?) {
        hideProgress()
        list?.let {
            viewAdapter.clear()
            viewAdapter.add(it)
            viewAdapter.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgress()
        exampleViewModel.getExampleData()

        setOnItemClickListener { item, _ ->
            (item as? ExampleEntity)?.let {
                //BACKLOG handle item click here
            }
        }
    }
}