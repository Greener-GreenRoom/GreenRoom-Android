package com.greener.presentation.ui.home.main

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.greener.presentation.R

@BindingAdapter("setBottomSheetSize")
fun setBottomSheetSize(view: View, isAnyPlants: Boolean) {
    view.layoutParams = view.layoutParams.apply {
        this.height =
            if (isAnyPlants) {
                view.resources.getDimensionPixelSize(R.dimen.bottom_sheet_home_size_not_empty)
            } else view.resources.getDimensionPixelSize(R.dimen.bottom_sheet_home_size_empty)
    }
}

@BindingAdapter("setMemo")
fun setMemo(view: TextView, memo: String?) {
    if (memo == "" || memo == null) {
        val context = view.context
        val textColor = ContextCompat.getColor(context, R.color.gray300)
        view.setText(R.string.home_bottom_sheet_memo_guide)
        view.setTextColor(textColor)
    } else {
        view.text = memo
    }
}

@BindingAdapter("setRealPlantImg")
fun setRealPlantImg(view: ImageView, url: String?) {
    Log.d("확인", "setRealPlantImg 실행")
    if (url == "" || url == null) {
        view.setImageResource(R.drawable.img_default_real_profile)
    } else {
        Glide.with(view).load(url).into(view);
    }
}
