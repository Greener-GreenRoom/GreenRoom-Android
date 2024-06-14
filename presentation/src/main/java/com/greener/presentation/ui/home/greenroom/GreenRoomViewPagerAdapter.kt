package com.greener.presentation.ui.home.greenroom

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.greener.domain.model.ActionTodo
import com.greener.domain.model.greenroom.GreenRoomTotalInfo

class GreenRoomViewPagerAdapter(
    frag: Fragment,
    myGreenRooms: List<GreenRoomTotalInfo>,
    val onChangedTodo: (Int, ActionTodo) -> Unit,
) : FragmentStateAdapter(frag) {
    private val items = myGreenRooms

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
        return GreenRoomFragment(items[position], position) { position, actionTodo ->
            onChangedTodo(position, actionTodo)
        }
    }
}
