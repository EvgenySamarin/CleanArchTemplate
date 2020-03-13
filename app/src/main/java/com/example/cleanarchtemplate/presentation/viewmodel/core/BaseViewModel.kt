package com.example.cleanarchtemplate.presentation.viewmodel.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanarchtemplate.domain.core.Failure

/**
 * Общая логика работы всех ViewModel проекта
 *
 * @author EvgenySamarin [github](https://github.com/EvgenySamarin)
 * @since 20190905 v1
 */
abstract class BaseViewModel : ViewModel() {
    /** список ошибок сервера */
    var failureData: MutableLiveData<HandleOnce<Failure>> = MutableLiveData()
    var progressData: MutableLiveData<Boolean> = MutableLiveData()

    /** неизменяемая функция ручной обработки ошибок */
    protected fun handleFailure(failure: Failure) {
        this.failureData.value = HandleOnce(failure)
    }

    /** 2019.11.13 v1 хранит состояние загрузки данных */
    protected fun updateProgress(progress: Boolean) {
        this.progressData.value = progress
    }
}