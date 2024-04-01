package com.greener.presentation.ui.home.decoration.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.greener.presentation.ui.home.decoration.background.DecorationBackgroundPreviewFragment
import com.greener.presentation.ui.home.decoration.plant.DecorationPlantPreviewFragment

class DecorationPreviewAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val decorationPlantPreviewFragment = DecorationPlantPreviewFragment()
    private val decorationBackgroundPreviewFragment = DecorationBackgroundPreviewFragment()

    override fun getItemCount(): Int = DECORATION_VIEW_COUNT

    override fun createFragment(position: Int): Fragment =
        when (position) {
            2 -> { DecorationBackgroundPreviewFragment() }
            else -> { DecorationPlantPreviewFragment() }
        }

    companion object {
        private const val DECORATION_VIEW_COUNT = 3
    }
}
