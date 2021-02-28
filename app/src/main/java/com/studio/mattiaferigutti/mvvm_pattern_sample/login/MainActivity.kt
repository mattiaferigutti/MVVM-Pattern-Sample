package com.studio.mattiaferigutti.mvvm_pattern_sample.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.studio.mattiaferigutti.mvvm_pattern_sample.R
import com.studio.mattiaferigutti.mvvm_pattern_sample.ScreenState
import com.studio.mattiaferigutti.mvvm_pattern_sample.details.DetailsActivity
import com.studio.mattiaferigutti.mvvm_pattern_sample.makeToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, LoginViewModelFactory(LoginInteractor()))
            .get(MainViewModel::class.java)

        //more information https://stackoverflow.com/questions/57698979/how-to-convert-java-method-reference-code-to-kotlin
        viewModel.currentState.observe(this, Observer(::updateUI))

        button?.setOnClickListener {
            viewModel.login(editUsername.text.toString(), editTextTextPassword.text.toString())
        }
    }

    private fun updateUI(state: ScreenState<LoginState>) {
        when(state) {
            is ScreenState.Loading -> showProgress()
            is ScreenState.Rendering -> handleLoginState(state.type)
        }
    }

    private fun handleLoginState(state: LoginState) {
        hideProgress()
        when(state) {
            LoginState.SUCCESS -> {
                makeToast(this, "success")
                startActivity(Intent(this, DetailsActivity::class.java))
            }
            LoginState.WRONG_PASSWORD -> {
                makeToast(this, "wrong password")
            }
            LoginState.WRONG_USER_NAME -> {
                makeToast(this, "wrong user name")
            }
        }
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }
}