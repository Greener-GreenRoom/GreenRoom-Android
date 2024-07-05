package com.greener.presentation.ui.mypage.level

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.greener.domain.model.GreenRoomItem
import com.greener.domain.model.mypage.GradeTier
import com.greener.presentation.R

@BindingAdapter("setMyBadge")
fun setMyBadge(view: ImageView, myTier: GradeTier?) {
    val tier = myTier?.tierCode
    val context = view.context
    val resId =
        context.resources.getIdentifier("asset_badge_$tier", "drawable", context.packageName)
    view.setImageResource(resId)
}

@BindingAdapter("setBadgeImage")
fun setBadgeImage(view: ImageView, gradeTier: GradeTier?) {
    val tier = gradeTier?.tierCode
    val context = view.context
    val resId =
        context.resources.getIdentifier("asset_badge_$tier", "drawable", context.packageName)
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

    val color = context.getColor(R.color.primary)
    val spannableString = SpannableString(text)

    spannableString.setSpan(
        ForegroundColorSpan(color),
        startPosition, // 시작 위치
        endPosition, // 끝 위치 (exclusive)
        0,
    )
    view.text = spannableString
}

@BindingAdapter("setNextRewardItemImage")
fun setNextRewardItemImage(view: ImageView, greenRoomItem: GreenRoomItem) {
    val itemType = greenRoomItem.itemType.lowercase()
    val itemName = greenRoomItem.itemName.lowercase()
    val context = view.context
    var resId = 0
    when (itemType) {
        HAIR_ACCESSORY -> {
            resId = getAsset("head", itemName, context)
        }

        GLASSES -> {
            resId = getAsset("glass", itemName, context)
        }

        BACKGROUND_WINDOW -> {
            resId = getAsset("window", itemName, context)
        }

        BACKGROUND_SHELF -> {
            resId = getAsset("shelf", itemName, context)
        }
    }
    view.setImageResource(resId)
}

fun getAsset(type: String, name: String, context: Context): Int {
    val resId =
        context.resources.getIdentifier("asset_${type}_$name", "drawable", context.packageName)
    return resId
}

const val HAIR_ACCESSORY = "hair_accessory"
const val GLASSES = "glasses"
const val BACKGROUND_WINDOW = "background_window"
const val BACKGROUND_SHELF = "background_shelf"
const val SHAPE = "shape"
