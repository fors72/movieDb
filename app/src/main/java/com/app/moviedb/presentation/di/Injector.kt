package com.app.moviedb.presentation.di

import com.app.moviedb.MovieDBApp
import com.app.moviedb.presentation.di.component.ActivityComponent
import com.app.moviedb.presentation.di.component.ApplicationComponent
import com.app.moviedb.presentation.di.component.DaggerApplicationComponent
import com.app.moviedb.presentation.di.module.ApplicationModule


object Injector {
    public lateinit var appComponent: ApplicationComponent
    private var dialogComponent: ActivityComponent? = null

//    public var dialogComponent: Lazy<ActivityComponent> = lazy { DaggerDialogComponent.builder().applicationComponent(appComponent).build() }

    fun init(context: MovieDBApp){
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(context))
                .build()
    }

//    fun getActivityComponent(): ActivityComponent {
//        return if (dialogComponent == null){
//            dialogComponent = DaggerActivityComponent.builder().applicationComponent(appComponent).dialogModule(DialogModule()).build()
//            dialogComponent!!
//        }else{
//            dialogComponent!!
//        }
//    }
//
//    fun clearActivityComponent(){
//        dialogComponent = null
//    }





}