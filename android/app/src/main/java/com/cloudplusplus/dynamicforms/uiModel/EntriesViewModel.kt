package com.cloudplusplus.dynamicforms.uiModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cloudplusplus.dynamicforms.data.db.AppDatabaseHolder
import com.cloudplusplus.dynamicforms.data.model.FormEntry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class EntriesViewModel(
    app: Application,
    private val asset: String
) : AndroidViewModel(app) {
    private val dao = AppDatabaseHolder.getInstance(app).formEntryDao()
    val entries: StateFlow<List<FormEntry>> = dao.entriesFor(asset)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}