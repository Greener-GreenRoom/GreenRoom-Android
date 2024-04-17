package com.greener.presentation.ui.home.greenroom

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.greener.domain.model.ExampleModel
import com.greener.domain.model.greenroom.GreenRoomTotalInfo

class GreenRoomViewPagerAdapter(frag: Fragment, myGreenRooms: List<GreenRoomTotalInfo>) : FragmentStateAdapter(frag) {
    private val items = myGreenRooms

    override fun getItemCount(): Int {
        return if (items.isNotEmpty()) {
            items.size
        } else {
            1
        }
    }
    override fun createFragment(position: Int): Fragment {
        Log.d("확인","${items}")
        if (items.isEmpty()) {
            Log.d("확인","item empty")
            return GreenRoomEmptyFragment()
        }
        return GreenRoomFragment(items[position])
    }
}
