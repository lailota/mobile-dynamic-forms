package com.cloudplusplus.dynamicforms.uiModel

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cloudplusplus.dynamicforms.data.db.AppDatabaseHolder
import com.cloudplusplus.dynamicforms.data.db.DataSeeder
import com.cloudplusplus.dynamicforms.data.db.FieldDao
import com.cloudplusplus.dynamicforms.data.db.SectionDao
import com.cloudplusplus.dynamicforms.data.model.Field
import com.cloudplusplus.dynamicforms.data.model.Section
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FormViewModel(
    app: Application,
    private val assetFileName: String
) : AndroidViewModel(app) {

    private val db = AppDatabaseHolder.getInstance(app)
    private val fieldDao: FieldDao = db.fieldDao()
    private val sectionDao: SectionDao = db.sectionDao()

    private val _fields = MutableStateFlow<List<Field>>(emptyList())
    val fields: StateFlow<List<Field>> = _fields

    private val _sections = MutableStateFlow<List<Section>>(emptyList())
    val sections: StateFlow<List<Section>> = _sections
    val values: SnapshotStateMap<String, String> = mutableStateMapOf()

    init {
        viewModelScope.launch {
            // 1) popula o DB, se ainda não tiver sido populado
            DataSeeder(
                context = app,
                assetFileName = assetFileName,
                fieldDao = fieldDao,
                sectionDao = sectionDao
            ).seedIfNeeded()

            // 2) carrega do banco
            _fields.value = fieldDao.getAll()
            _sections.value = sectionDao.getAll()
        }
    }
}
