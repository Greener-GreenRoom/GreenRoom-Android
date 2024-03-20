package com.greener.presentation.ui.home.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.greener.presentation.R

@BindingAdapter("setProfilePlantType")
fun setProfilePlantType(view: ImageView, id: Int) {
    when (id) {
        1 -> {
            view.setImageResource(R.drawable.img_plant_1_empty)
        }
        2 -> {
            view.setImageResource(R.drawable.img_plant_2_empty)
        }
        3 -> {
            view.setImageResource(R.drawable.img_plant_3_empty)
        }
        4 -> {
            view.setImageResource(R.drawable.img_plant_4_empty)
        }
    }
}
