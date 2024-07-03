package com.greener.presentation.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.greener.presentation.R

@BindingAdapter("image_url")
fun bindImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.color.gray200)
        .into(view)
}

@BindingAdapter("numberNullable")
fun setText(editText: TextInputEditText, text: String?) {
    val newText = text ?: "0"
    if (editText.text.toString() != newText) {
        editText.setText(newText)
    }
}

@InverseBindingAdapter(attribute = "numberNullable")
fun getText(editText: TextInputEditText) =
    editText.text.toString()
