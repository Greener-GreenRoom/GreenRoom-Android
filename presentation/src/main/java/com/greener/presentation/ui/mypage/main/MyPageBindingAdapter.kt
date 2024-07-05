package com.greener.presentation.ui.mypage.main

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
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
    val context = view.context
    Glide.with(context)
        .applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.img_default_profile)
                .error(R.drawable.img_default_profile),
        )
        .load(url)
        .into(view)
}

@BindingAdapter("setAppVersion")
fun setAppVersion(view: TextView, version: String) {
    val context = view.context
    val packageName = view.context.packageName
    try {
        val textResId = context.resources.getIdentifier(
            "my_page_app_version",
            "string",
            packageName,
        )
        val text = context.getString(textResId, version)
        val colorResId = context.resources.getIdentifier("gray300", "color", packageName)
        val color = context.getColor(colorResId)
        val spannableString = SpannableString(text)

        spannableString.setSpan(
            ForegroundColorSpan(color),
            5, // 시작 위치
            text.length, // 끝 위치 (exclusive)
            0,
        )
        view.text = spannableString
    } catch (e: Exception) {
    }
}
