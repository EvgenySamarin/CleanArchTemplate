package com.example.cleanarchtemplate.di

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Provides annotation of view model for data binding. Create map key from ViewModel class in
 * runtime
 *
 * @since 20190905 v1
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
@Keep
annotation class ViewModelKey(val value: KClass<out ViewModel>)