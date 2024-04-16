package com.greener.presentation.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.greener.presentation.R

@BindingAdapter("image_url")
fun bindImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.color.gray200)
        .into(view)
}
