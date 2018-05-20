package com.firebase.messenger.presentation.view.impl.base

interface LoadListView<in T> {

    fun renderObject(objects: List<T>)

    fun addObjects(objects: List<T>)

}