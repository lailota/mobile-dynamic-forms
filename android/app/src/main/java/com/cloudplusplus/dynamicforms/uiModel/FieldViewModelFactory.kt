package com.cloudplusplus.dynamicforms.uiModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FieldViewModelFactory(
    private val app: Application,
    private val asset: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FieldViewModel(app, asset) as T
    }
}

