package com.greener.presentation.ui.home.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("setProfilePlantType")
fun setProfilePlantType(view: ImageView, type: String) {
    val plantType = type.lowercase()
    val context = view.context
    val resId =
        context.resources.getIdentifier("asset_plant_$plantType", "drawable", context.packageName)
    view.setImageResource(resId)
}
