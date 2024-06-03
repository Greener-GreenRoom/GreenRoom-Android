package com.greener.presentation.ui.mypage.edit

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.greener.presentation.R


@BindingAdapter("setClearVisibility")
fun setClearVisibility(view: ImageView,profileUrl:String?) {
    if(profileUrl.isNullOrBlank()) {
        view.visibility= View.GONE
        return
    }
    view.visibility=View.VISIBLE
}

@BindingAdapter("setMyEditProfileImage")
fun setMyEditProfileImage(view: ShapeableImageView, url: String?) {
    Log.d("확인","url: $url")
    val context = view.context

    Glide.with(context)
        .applyDefaultRequestOptions( RequestOptions()
            .placeholder(R.drawable.img_edit_default)
            .error(R.drawable.img_edit_default))
        .load(url)
        .into(view)

}