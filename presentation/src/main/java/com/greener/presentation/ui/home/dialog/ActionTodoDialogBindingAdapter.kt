package com.greener.presentation.ui.home.dialog

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.greener.domain.model.ActionTodo

@BindingAdapter("setDialogTitle")
fun setTitle(view: TextView, actionTodo: ActionTodo) {
    val context = view.context
    val packageName = context.packageName
    val textId =
        context.resources.getIdentifier("dialog_${actionTodo.activity}_title", "string", packageName)
    val colorId =
        context.resources.getIdentifier(actionTodo.color, "color", packageName)
    val textColor = context.getColor(colorId)
    view.setText(textId)
    view.setTextColor(textColor)
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
