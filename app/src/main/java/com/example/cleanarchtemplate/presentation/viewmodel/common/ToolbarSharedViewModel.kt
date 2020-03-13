package com.example.cleanarchtemplate.presentation.viewmodel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Модель для хранения общих данных о toolbar а также для возможности изменять эти данные
 * (заголовки и проч.). Если в приложении используется Collapsing Toolbar, к сожалению navigation
 * components не будет менять заголовки, и их придется менять вручную. Для этих целей отлично
 * подойдет данная view model
 *
 * @author EvgenySamarin
 * @since 20191202 v1
 */
class ToolbarSharedViewModel : ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    fun updateTitle(title: String) {
        _title.value = title
    }
}