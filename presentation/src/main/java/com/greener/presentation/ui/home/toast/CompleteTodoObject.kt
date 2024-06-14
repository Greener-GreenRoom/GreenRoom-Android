package com.greener.presentation.ui.home.toast

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.greener.presentation.R
import com.greener.presentation.databinding.SnackbarCompleteTodoBinding

class CompleteTodoSnackBar(view: View, val seedPoint: Int) {
    private val context = view.context
    private val snackbar = Snackbar.make(view, "", 3000)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackBarBinding: SnackbarCompleteTodoBinding =
        DataBindingUtil.inflate(inflater, R.layout.snackbar_complete_todo, null, false)

    companion object {
        fun make(view: View, seedPoint: Int) = CompleteTodoSnackBar(view, seedPoint)
    }

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackBarBinding.root, 0)
        }
    }

    private fun initData() {
        snackBarBinding.seedPoint = seedPoint
    }

    fun show() {
        snackbar.show()
    }
}

object CompleteTodoToast {
    fun createToast(context: Context, seedPoint: Int): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: SnackbarCompleteTodoBinding =
            DataBindingUtil.inflate(inflater, R.layout.snackbar_complete_todo, null, false)

        binding.seedPoint = seedPoint

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 315.toPx())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
