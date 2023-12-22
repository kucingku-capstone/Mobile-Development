package com.example.kucingku

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
class LoadingDialog(context: Context) : Dialog(context) {
    override fun show() {
        super.show()
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setContentView(R.layout.dialog_progress)
        setCancelable(false)
    }
    
    override fun dismiss() {
        super.dismiss()
    }
}
