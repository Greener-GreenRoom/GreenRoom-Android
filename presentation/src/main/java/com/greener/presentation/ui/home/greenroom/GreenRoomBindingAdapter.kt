package com.greener.presentation.ui.home.greenroom

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
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
fun setWindowImg(view: ImageView, window: String) {

    val windowLowerCase = window.lowercase()
    val context = view.context
    val resId =
        context.resources.getIdentifier(
            "asset_window_${windowLowerCase}",
            "drawable",
            context.packageName
        )
    if (resId == 0) {
        view.setImageResource(R.drawable.img_window_1)
    } else {
        view.setImageResource(resId)
    }
}

@BindingAdapter("setShelfImg")
fun setShelfImg(view: ImageView, shelf: String) {
    val shelfLowerCase = shelf.lowercase()
    val context = view.context
    val resId =
        context.resources.getIdentifier(
            "asset_shelf_${shelfLowerCase}",
            "drawable",
            context.packageName
        )
    if(resId == 0) {
        view.setImageResource(R.drawable.img_shelf_1)
    }
    else {
        view.setImageResource(resId)
    }
}

@BindingAdapter("setHairAccessory")
fun setHairAccessory(view: ImageView, hairAccessory: String) {
    val hairAccessoryLowerCase = hairAccessory.lowercase()
    val context = view.context
    val resId =
        context.resources.getIdentifier(
            "asset_head_${hairAccessoryLowerCase}",
            "drawable",
            context.packageName
        )
    if (resId == 0) {
        Log.d("확인", "헤어 악세사리 resId = 0")
        view.visibility = View.GONE
    } else {
        view.setImageResource(resId)
    }
}

@BindingAdapter("setGlasses")
fun setGlasses(view: ImageView, glasses: String) {
    val glassesLowerCase = glasses.lowercase()
    val context = view.context
    val resId =
        context.resources.getIdentifier(
            "asset_glass_${glassesLowerCase}",
            "drawable",
            context.packageName
        )
    if (resId == 0) {
        Log.d("확인", "안경 resId = 0")
        view.visibility = View.GONE
    } else {
        view.setImageResource(resId)
    }
}

@BindingAdapter("setTextBalloon")
fun setTextBalloon(view: TextView, actionTodo: ActionTodo?) {
    val context = view.context
    val packageName = view.context.packageName
    try {
        val textResId = context.resources.getIdentifier(
            "${actionTodo!!.activity}_text_balloon",
            "string",
            packageName
        )
        val text = context.getString(textResId)
        val colorResId = context.resources.getIdentifier(actionTodo.color, "color", packageName)
        val color = context.getColor(colorResId)
        val spannableString = SpannableString(text)

        spannableString.setSpan(
            ForegroundColorSpan(color),
            0, // 시작 위치
            actionTodo.size, // 끝 위치 (exclusive)
            0
        )
        view.text = spannableString
    } catch (e: Exception) {
        Log.d("확인", "${e.stackTrace}")
        Log.d("확인", e.message.toString())
    }
}
