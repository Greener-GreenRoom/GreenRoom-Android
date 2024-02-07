package com.greener.presentation.ui.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.greener.presentation.R


@BindingAdapter("setProfile")
fun setProfile(view: ImageView, id:Int ) {
    when(id) {
        1 ->{
            view.setImageResource(R.drawable.img_plant_1_profile)
        }
        2-> {
            view.setImageResource(R.drawable.img_plant_2_profile)

        }
        3-> {
            view.setImageResource(R.drawable.img_plant_3_profile)

        }
        4 -> {
            view.setImageResource(R.drawable.img_plant_4_profile)
        }
    }
}