package com.greener.presentation.ui.community

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentCommunityBinding
import com.greener.presentation.ui.base.BaseFragment

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(
    FragmentCommunityBinding::inflate,
) {
    private val viewModel: CommunityViewModel by viewModels()

    override fun initListener() {
        softInputAdjustResize2()
        binding.vpCommunityMain.adapter = CommunityTabAdapter(requireActivity())
        binding.vpCommunityMain.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tlCommunityMain, binding.vpCommunityMain) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.community_tab_suggest)
                1 -> tab.text = getString(R.string.community_tab_boast)
                2 -> tab.text = getString(R.string.community_tab_green_ranking)
            }
        }.attach()
    }

}
