package com.example.cleanarchtemplate.di

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchtemplate.presentation.viewmodel.core.ViewModelFactory
import com.example.cleanarchtemplate.presentation.viewmodel.example.ExampleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Provides dependencies of view model
 *
 * @since 20190905 v8
 */
@Module
@Keep
abstract class ViewModelModule {
    /** @since 20200313 v1: bind view model factory */
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    /** предоставляет зависимость для AccountViewModel */
    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel::class)
    abstract fun bindAccountViewModel(viewModel: ExampleViewModel): ViewModel

}