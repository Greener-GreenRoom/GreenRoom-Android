package com.greener.presentation.ui.home

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingAdapter


@BindingAdapter("setBottomSheetSize")
fun setBottomSheetSize(view: View, isAnyPlants: Boolean) {
    val layout = view.parent as ConstraintLayout
    val cs = ConstraintSet()
    cs.clone(layout)

    if(isAnyPlants) {
        cs.constrainPercentHeight(view.id, 0.3435f)
        cs.applyTo(layout)
    } else{
        cs.constrainPercentHeight(view.id, 0.2453f)
        cs.applyTo(layout)
    }
}