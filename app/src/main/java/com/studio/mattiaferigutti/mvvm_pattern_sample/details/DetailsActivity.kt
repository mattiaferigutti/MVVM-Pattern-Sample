package com.studio.mattiaferigutti.mvvm_pattern_sample.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.studio.mattiaferigutti.mvvm_pattern_sample.R
import com.studio.mattiaferigutti.mvvm_pattern_sample.ScreenState
import com.studio.mattiaferigutti.mvvm_pattern_sample.login.LoginInteractor
import com.studio.mattiaferigutti.mvvm_pattern_sample.login.LoginViewModelFactory
import com.studio.mattiaferigutti.mvvm_pattern_sample.login.MainViewModel
import com.studio.mattiaferigutti.mvvm_pattern_sample.makeToast
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel = ViewModelProvider(this, DetailsViewModelFactory(FindItemsInteractor()))
            .get(DetailsViewModel::class.java)

        //more information https://stackoverflow.com/questions/57698979/how-to-convert-java-method-reference-code-to-kotlin
        viewModel.currentState.observe(this, Observer(::updateUI))
    }

    private fun updateUI(state: ScreenState<MainState<String>>) {
        hideProgress()
        when(state) {
            is ScreenState.Loading -> showProgress()
            is ScreenState.Rendering -> handleRendering(state.type)
        }
    }

    private fun handleRendering(state: MainState<String>) {
        when(state) {
            is MainState.ItemClicked -> {
                showMessage(state.item)
            }
            is MainState.ItemsLoaded -> {
                createAdapter(state.list)
            }
        }
    }

    private fun createAdapter(list: List<String>) {
        val adapter = ListAdapter(list, viewModel::onClickEvent)
        this.list?.apply {
            layoutManager = LinearLayoutManager(this@DetailsActivity)
            this.adapter = adapter
        }
    }

    private fun showMessage(message: String) {
        makeToast(this, message)
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }
}