package com.greener.presentation.ui.home.greenroom

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.greener.presentation.R


@BindingAdapter("setPlantType")
fun setPlantType(view: ImageView, id: Int) {

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

@BindingAdapter("setPlantFace")
fun setPlantFace(view: ImageView, id: Int) {

    when (id) {
        1 -> {
            view.setImageResource(R.drawable.img_face_default)
        }

        2 -> {
            view.setImageResource(R.drawable.img_face_happy)
        }

        3 -> {
            view.setImageResource(R.drawable.img_face_angry)
        }

        else -> {
            view.setImageResource(R.drawable.img_face_default)
        }
    }
}

@BindingAdapter("setWindowImg")
fun setWindowImg(view: ImageView, id: Int) {


    when (id) {
        1 -> {
            view.setImageResource(R.drawable.img_window_1)

        }

        2 -> {
            view.setImageResource(R.drawable.img_window_2)
        }

        3 -> {
            view.setImageResource(R.drawable.img_window_3)
        }

        4 -> {
            view.setImageResource(R.drawable.img_window_4)
        }

    }
}


@BindingAdapter("setShelfImg")
fun setShelfImg(view: ImageView, id: Int) {

    when (id) {
        1 -> {
            view.setImageResource(R.drawable.img_shelf_1)
        }

        2 -> {
            view.setImageResource(R.drawable.img_shelf_2)
        }

        3 -> {
            view.setImageResource(R.drawable.img_shelf_3)
        }

        4 -> {
            view.setImageResource(R.drawable.img_shelf_4)
        }

        5 -> {
            view.setImageResource(R.drawable.img_shelf_5)
        }

    }
}


