package com.gargour.warehouse.domain.use_case.settings.import

import android.annotation.SuppressLint
import android.os.Environment
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.data.data_source.WarehouseDb
import kotlinx.coroutines.flow.flow
import java.io.*

class ImportDatabase {
    operator fun invoke(databasePath: File) = flow<Response<Any>> {
        emit(
            try {
                val dir =
                    File(Environment.getExternalStorageDirectory().path + "/Gargour/Input/${WarehouseDb.DATABASE_NAME}")
                if (dir.exists()) {
                    import(databasePath)
                    dir.delete()
                    Response.Success(true)
                } else {
                    Response.Error("File not found!")
                }
            } catch (e: Exception) {
                Response.Error(e.message ?: "Failed to import data.")
            }
        )
    }

    @SuppressLint("SdCardPath")
    private fun import(databasePath: File) {
        val mInput: InputStream =
            FileInputStream(Environment.getExternalStorageDirectory().path + "/Gargour/Input/${WarehouseDb.DATABASE_NAME}")
//        "/data/data/com.gargour.egoscanandprin/databases/${EgoDatabase.DATABASE_NAME}"
        val mOutput: OutputStream = FileOutputStream(databasePath)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) {
            mOutput.write(mBuffer, 0, mLength)
        }
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }
}