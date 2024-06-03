package com.greener.presentation.ui.mypage.main

import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.greener.domain.model.mypage.Grade
import com.greener.presentation.R


@BindingAdapter("setMyGrade")
fun setMyGrade(view: TextView, grade: Grade) {
    view.text =
        view.context.getString(R.string.my_page_my_grade, grade.description, grade.currentLevel)
}

@BindingAdapter("setMyProfileImage")
fun setMyProfileImage(view: ShapeableImageView, url: String?) {
    Log.d("확인","url: $url")
    val context = view.context

    /*if(url == "" ) {
        view.setImageResource(R.drawable.img_edit_default)
        return
    }*/
    Glide.with(context)
        .applyDefaultRequestOptions( RequestOptions()
            .placeholder(R.drawable.img_default_profile)
            .error(R.drawable.img_default_profile))
        .load(url)
        .into(view)

}

