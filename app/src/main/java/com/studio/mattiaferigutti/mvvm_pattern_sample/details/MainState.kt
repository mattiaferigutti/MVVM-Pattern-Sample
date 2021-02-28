package com.studio.mattiaferigutti.mvvm_pattern_sample.details

sealed class MainState<out T> {
    class ItemsLoaded<T>(val list: List<T>) : MainState<T>()
    class ItemClicked<T>(val item: T) : MainState<T>()
}
