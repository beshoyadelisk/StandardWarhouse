package com.gargour.warehouse.domain.use_case.settings.export

import android.os.Environment
import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.data.data_source.WarehouseDb
import kotlinx.coroutines.flow.flow
import java.io.*

@Suppress("BlockingMethodInNonBlockingContext")
class ExportDatabase {
    operator fun invoke(databasePath: File) = flow<Response<Any>> {
        emit(
            try {


                Response.Loading(View.VISIBLE)

                val file =
                    File(Environment.getExternalStorageDirectory().path + "/Gargour/Output/")
                val file2 =
                    File(Environment.getExternalStorageDirectory().path + "/Gargour/Input/")
                if (!file.exists()) {
                    file.mkdirs()
                }
                if (!file2.exists()) {
                    file2.mkdirs()
                }
                val mInput: InputStream = FileInputStream(databasePath)
                val outFileName =
                    Environment.getExternalStorageDirectory().path + "/Gargour/Output/${WarehouseDb.DATABASE_NAME}"

                val mOutput: OutputStream = FileOutputStream(outFileName)
                val mBuffer = ByteArray(1024)
                var mLength: Int
                while (mInput.read(mBuffer).also { mLength = it } > 0) {
                    mOutput.write(mBuffer, 0, mLength)
                }
                mOutput.flush()
                mOutput.close()
                mInput.close()
                Response.Success(true)
            } catch (e: Exception) {
                Response.Loading(View.GONE)
                Response.Error(e.message ?: "Failed to export data.")
            }
        )
    }
}