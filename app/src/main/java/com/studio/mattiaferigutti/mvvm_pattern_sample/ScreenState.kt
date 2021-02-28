package com.studio.mattiaferigutti.mvvm_pattern_sample

sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    class Rendering<T>(val type: T) : ScreenState<T>()
}
