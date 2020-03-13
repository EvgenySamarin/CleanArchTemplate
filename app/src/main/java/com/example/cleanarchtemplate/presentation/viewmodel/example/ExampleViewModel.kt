package com.example.cleanarchtemplate.presentation.viewmodel.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cleanarchtemplate.domain.core.None
import com.example.cleanarchtemplate.domain.example.GetExampleData
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity
import com.example.cleanarchtemplate.presentation.viewmodel.core.BaseViewModel
import javax.inject.Inject

/**
 * View model, which work with example data
 *
 * @author EvgenySamarin
 * @since 20200130 v2
 */
class ExampleViewModel @Inject constructor(
    private val getExampleData: GetExampleData
) : BaseViewModel() {
    private val _exampleData: MutableLiveData<List<ExampleEntity>> = MutableLiveData()

    val exampleData: LiveData<List<ExampleEntity>> get() = _exampleData

    fun getExampleData() = getExampleData(None()) {
        it.either(::handleFailure, ::handleExampleResponse)
    }

    private fun handleExampleResponse(response: List<ExampleEntity>?) {
        _exampleData.value = response
    }

    override fun onCleared() {
        super.onCleared()
        getExampleData.unsubscribe()
    }
}