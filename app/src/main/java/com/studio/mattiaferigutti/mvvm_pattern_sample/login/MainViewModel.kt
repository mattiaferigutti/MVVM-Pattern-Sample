package com.studio.mattiaferigutti.mvvm_pattern_sample.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.studio.mattiaferigutti.mvvm_pattern_sample.ScreenState

class MainViewModel(val loginInteractor: LoginInteractor) : ViewModel(), LoginInteractor.OnLoginFinishedListener {

    private val _currentState = MutableLiveData<ScreenState<LoginState>>()

    val currentState: LiveData<ScreenState<LoginState>>
        get() = _currentState

    fun login(username: String, password: String) {
        _currentState.postValue(ScreenState.Loading)
        loginInteractor.login(username, password, this)
    }

    override fun onUsernameError() {
        _currentState.postValue(ScreenState.Rendering(LoginState.WRONG_USER_NAME))
    }

    override fun onPasswordError() {
        _currentState.postValue(ScreenState.Rendering(LoginState.WRONG_PASSWORD))
    }

    override fun onSuccess() {
        _currentState.postValue(ScreenState.Rendering(LoginState.SUCCESS))
    }
}

class LoginViewModelFactory(private val loginInteractor: LoginInteractor) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(loginInteractor) as T
    }
}