package com.greener.presentation.ui.home.dialog

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import dagger.hilt.android.internal.Contexts


@BindingAdapter("setDialogTitle")
fun setTitle(view: TextView, todoName: String) {
    val context = view.context
    val resId =
        context.resources.getIdentifier("dialog_${todoName}_title", "string", context.packageName)

    view.setText(resId)
}

@BindingAdapter("setDialogAsk")
fun setAsk(view: TextView, todoName: String) {
    val context = view.context
    val resId =
        context.resources.getIdentifier("dialog_${todoName}_ask", "string", context.packageName)

    view.setText(resId)
}

@BindingAdapter("setDialogConfirm")
fun setConfirm(view: Button, todoName: String) {
    val context = view.context
    val resId =
        context.resources.getIdentifier("dialog_${todoName}_confirm", "string", context.packageName)

    view.setText(resId)
}

