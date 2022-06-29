package com.gargour.warehouse.view.scan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import cn.guanmai.scanner.IScannerManager
import cn.guanmai.scanner.SupporterManager
import com.gargour.warehouse.util.ViewExt.showToast

abstract class ScannerFragment:Fragment(), SupporterManager.IScanListener  {
    private var supporterManager: SupporterManager<IScannerManager>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScanner()
    }
    private fun initScanner() {
        supporterManager = SupporterManager(context, this)
    }

    override fun onScannerServiceConnected() {
        showToast("Scanner connected.")
    }

    override fun onScannerServiceDisconnected() {
        showToast("Scanner disconnected.")
    }

    override fun onScannerInitFail() {
        showToast("Failed to initialize scanner.")
    }
}