package com.greener.presentation.ui.community.suggest

import androidx.navigation.fragment.findNavController
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentCommunitySuggestBinding
import com.greener.presentation.ui.base.BaseFragment

class CommunitySuggestFragment : BaseFragment<FragmentCommunitySuggestBinding>(
    FragmentCommunitySuggestBinding::inflate
) {
    override fun initListener() {
        softInputAdjustResize2()
        binding.btnCommunitySuggest.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_communityRegisterPlantFragment)
        }
    }


}