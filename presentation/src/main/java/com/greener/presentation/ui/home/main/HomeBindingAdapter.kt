package com.greener.presentation.ui.home.main

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingAdapter
import com.greener.presentation.R


@BindingAdapter("setBottomSheetSize")
fun setBottomSheetSize(view: View, isAnyPlants: Boolean) {

    view.layoutParams = view.layoutParams.apply {
        this.height =
            if (isAnyPlants) view.resources.getDimensionPixelSize(R.dimen.bottom_sheet_home_size_not_empty)
            else view.resources.getDimensionPixelSize(R.dimen.bottom_sheet_home_size_empty)
    }
}