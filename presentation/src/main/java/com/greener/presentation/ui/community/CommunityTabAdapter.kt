package com.greener.presentation.ui.community

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.greener.presentation.ui.community.boast.CommunityBoastFragment
import com.greener.presentation.ui.community.greenranking.CommunityGreenRankingFragment
import com.greener.presentation.ui.community.suggest.CommunitySuggestFragment

const val TAB_NUM = 3
class CommunityTabAdapter constructor(frag: FragmentActivity):FragmentStateAdapter(frag) {
    override fun getItemCount(): Int = TAB_NUM

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                CommunitySuggestFragment()
            }
            1-> {
                CommunityBoastFragment()
            }
            2-> {
                CommunityGreenRankingFragment()
            }
            else -> {
                CommunitySuggestFragment()
            }
        }
    }
}