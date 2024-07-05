package com.greener.presentation.ui.community.register

import androidx.navigation.fragment.findNavController
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentCommunityRegisterCompleteBinding
import com.greener.presentation.ui.base.BaseFragment

class CommunityRegisterCompleteFragment:BaseFragment<FragmentCommunityRegisterCompleteBinding>(
    FragmentCommunityRegisterCompleteBinding::inflate
) {

    override fun initListener() {
        binding.btnCommunityRegisterComplete.setOnClickListener {
            findNavController().navigate(R.id.action_communityRegisterCompleteFragment_to_communityFragment)
        }
    }
}