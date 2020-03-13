package com.example.cleanarchtemplate.presentation.viewmodel.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Responsible to create view model instances
 *
 * @param creators map contains pair ViewModel classes (keys) and providers (values) like below
 * type: Map<Class<ViewModel>, Provider<ViewModel>>. Map was got from dagger generated view model
 * map
 *
 * @author EvgenySamarin
 * @since 20190905 v1
 */
@Singleton
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    /**
     * Create view model implementation
     * get dagger provider from map creator and passes view model class implementation by its help
     *
     * @return ViewModel implementation special type
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw  RuntimeException(e)
        }
    }
}