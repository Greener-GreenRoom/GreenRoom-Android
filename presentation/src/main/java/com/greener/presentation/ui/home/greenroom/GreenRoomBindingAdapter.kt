package com.greener.presentation.ui.home.greenroom

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.greener.domain.model.ActionTodo
import com.greener.presentation.R

@BindingAdapter("setPlantType")
fun setPlantType(view: ImageView, type: String) {
    val plantType = type.lowercase()
    val context = view.context
    val resId =
        context.resources.getIdentifier("asset_plant_${plantType}", "drawable", context.packageName)
    view.setImageResource(resId)
}

@BindingAdapter("setPlantFace")
fun setPlantFace(view: ImageView, id: Int) {
    when (id) {
        0 -> {
            view.setImageResource(R.drawable.asset_face_happy)
        }

        1, 2 -> {
            view.setImageResource(R.drawable.asset_face_default)
        }

        3, 4 -> {
            view.setImageResource(R.drawable.asset_face_sad)
        }

        else -> {
            view.setImageResource(R.drawable.asset_face_angry)
        }

    }
}

@BindingAdapter("setWindowImg")
fun setWindowImg(view: ImageView, type: String) {
    val windowType = type.lowercase()
    val context = view.context
    val resId =
        context.resources.getIdentifier(
            "asset_window_${windowType}",
            "drawable",
            context.packageName
        )
    view.setImageResource(resId)
}

@BindingAdapter("setShelfImg")
fun setShelfImg(view: ImageView, type: String) {
    val shelfType = type.lowercase()
    val context = view.context
    val resId =
        context.resources.getIdentifier("asset_shelf_${shelfType}", "drawable", context.packageName)
    view.setImageResource(resId)
}


@BindingAdapter("setTextBalloon")
fun setTextBalloon(view: TextView, actionTodo: ActionTodo) {
    val context = view.context
    val textResId = context.resources.getIdentifier(
        "${actionTodo.activity}_text_balloon",
        "string",
        context.packageName
    )
    val colorResId = context.resources.getIdentifier(actionTodo.color, "color", context.packageName)
    view.setText(textResId)
    view.setTextColor(colorResId)
}
