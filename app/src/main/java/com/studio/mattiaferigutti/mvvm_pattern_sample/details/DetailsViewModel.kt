package com.studio.mattiaferigutti.mvvm_pattern_sample.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.studio.mattiaferigutti.mvvm_pattern_sample.ScreenState

class DetailsViewModel(val findItemsInteractor: FindItemsInteractor) : ViewModel() {

    private val _currentValue = MutableLiveData<ScreenState<MainState<String>>>().apply {
        value = ScreenState.Loading
    }

    init {
        //only for this example
        loadItems()
    }

    val currentState: LiveData<ScreenState<MainState<String>>>
        get() = _currentValue

    fun loadItems() {
        findItemsInteractor.findItems(::listReady)
    }

    private fun listReady(list: List<String>) {
        _currentValue.postValue(ScreenState.Rendering(MainState.ItemsLoaded(list)))
    }

    fun onClickEvent(item: String) {
        _currentValue.postValue(ScreenState.Rendering(MainState.ItemClicked(item)))
    }
}

class DetailsViewModelFactory(private val findItemsInteractor: FindItemsInteractor) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(findItemsInteractor) as T
    }
}