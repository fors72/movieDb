package com.app.moviedb.presentation.di.component

import com.app.moviedb.presentation.di.module.DialogModule
import com.app.moviedb.presentation.di.scope.PerActivity
import dagger.Component


@PerActivity
@Component(dependencies = [ApplicationComponent::class],modules = [DialogModule::class])
interface ActivityComponent {
//    fun inject(act:DialogFragment)
//    fun inject(act:UserListFragment)
}