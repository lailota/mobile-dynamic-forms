package com.cloudplusplus.dynamicforms.uiModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FormViewModelFactory(
    private val app: Application,
    private val assetFileName: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FormViewModel(app, assetFileName) as T
    }
}
