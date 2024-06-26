package com.greener.presentation.ui.home.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.greener.domain.model.ActionTodo
import com.greener.presentation.databinding.DialogActionTodoBinding

class ActionDialog(context: Context, val actionTodo: ActionTodo) : Dialog(context) {

    private lateinit var clickListener: ClickListener
    private lateinit var binding: DialogActionTodoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogActionTodoBinding.inflate(LayoutInflater.from(context))
        binding.actionTodo = actionTodo
        setContentView(binding.root)

        context.dialogResize(this@ActionDialog, 0.777f, 0.23f)

        // 배경을 투명하게
        // 다이얼로그를 둥글게 표현하기 위해 필요
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 다이얼로그 바깥쪽 클릭시 종료
        setCanceledOnTouchOutside(true)
        // 취소 가능 유무
        setCancelable(true)

        binding.btnDialogCompleteTodoNotYet.setOnClickListener {
            dismiss() // 다이얼로그 닫기 (Close the dialog)
        }

        binding.btnDialogCompleteTodoConfirm.setOnClickListener {
            clickListener.onClick()
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
