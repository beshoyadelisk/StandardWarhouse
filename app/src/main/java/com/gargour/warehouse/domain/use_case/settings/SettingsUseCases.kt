package com.gargour.warehouse.domain.use_case.settings

import com.gargour.warehouse.domain.use_case.settings.export.ExportDatabase
import com.gargour.warehouse.domain.use_case.settings.import.ImportDatabase

data class SettingsUseCases(
    val importDatabase: ImportDatabase,
    val exportDatabase: ExportDatabase,
)