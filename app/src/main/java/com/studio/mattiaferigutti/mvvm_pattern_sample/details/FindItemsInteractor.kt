package com.studio.mattiaferigutti.mvvm_pattern_sample.details

import com.studio.mattiaferigutti.mvvm_pattern_sample.postDelayed

class FindItemsInteractor {

    fun findItems(callback: (List<String>) -> Unit) {
        postDelayed(2000) { callback(createArrayList()) }
    }

    private fun createArrayList(): List<String> = (1..10).map { "Item $it" }
}