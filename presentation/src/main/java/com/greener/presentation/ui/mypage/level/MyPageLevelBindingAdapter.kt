package com.greener.presentation.ui.mypage.level

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.greener.domain.model.mypage.GradeTier
import com.greener.presentation.R


@BindingAdapter("setMyBadge")
fun setMyBadge(view: ImageView, myTier: GradeTier?) {
    val tier = myTier?.tierCode
    val context = view.context
    val resId =
        context.resources.getIdentifier("asset_badge_${tier}", "drawable", context.packageName)
    view.setImageResource(resId)
}

@BindingAdapter("setBadgeImage")
fun setBadgeImage(view: ImageView, gradeTier: GradeTier?) {
    val tier = gradeTier?.tierCode
    val context = view.context
    val resId =
        context.resources.getIdentifier("asset_badge_${tier}", "drawable", context.packageName)
    view.setImageResource(resId)
}

@BindingAdapter("setNextRewardGuide")
fun setNextRewardGuide(view: TextView, nextLevel: Int) {
    val context = view.context
    val levelSize = nextLevel.toString().length
    val startPosition = 7
    val defaultSize = 3
    val endPosition = startPosition + defaultSize + levelSize
    val text = context.getString(R.string.my_page_next_reward_guide, nextLevel)
    Log.d("확인", "text: $text")

    val color = context.getColor(R.color.primary)
    val spannableString = SpannableString(text)

    spannableString.setSpan(
        ForegroundColorSpan(color),
        startPosition, // 시작 위치
        endPosition, // 끝 위치 (exclusive)
        0
    )
    view.text = spannableString
}