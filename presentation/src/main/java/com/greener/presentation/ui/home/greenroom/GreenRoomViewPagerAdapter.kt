package com.greener.presentation.ui.home.greenroom

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.greener.domain.model.ExampleModel

class GreenRoomViewPagerAdapter(frag: Fragment, myPlants: List<ExampleModel>) : FragmentStateAdapter(frag) {
    private val items = myPlants

    override fun getItemCount(): Int {
        return if (items.isNotEmpty()) {
            items.size
        } else {
            1
        }
    }
    override fun createFragment(position: Int): Fragment {
        if (items.isEmpty()) {
            return GreenRoomEmptyFragment()
        }
        return GreenRoomFragment(items[position])
    }
}
