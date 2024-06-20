package com.greener.presentation.ui.mypage.main

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.greener.presentation.databinding.DialogLogoutBinding

class LogoutDialog(context: Context) : Dialog(context) {

    private lateinit var clickListener: ClickListener
    private lateinit var binding: DialogLogoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLogoutBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        context.dialogResize(this@LogoutDialog, 0.777f, 0.23f)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setCanceledOnTouchOutside(true)

        setCancelable(true)

        binding.btnDialogLogoutConfirm.setOnClickListener {
            clickListener.onClick()
            dismiss()
        }

        binding.btnDialogLogoutDismiss.setOnClickListener {
            dismiss()
        }
    }

    private fun Context.dialogResize(dialog: Dialog, width: Float, height: Float) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialog.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()

            window?.setLayout(x, y)
        } else {
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialog.window
            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }
    interface ClickListener {
        fun onClick()
    }

    fun setItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }
}
