package com.greener.presentation.ui.community.register

import androidx.navigation.fragment.findNavController
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentCommunityRegisterPlantBinding
import com.greener.presentation.ui.base.BaseFragment

class CommunityRegisterPlantFragment : BaseFragment<FragmentCommunityRegisterPlantBinding>(
    FragmentCommunityRegisterPlantBinding::inflate
) {

    override fun initListener() {
        binding.tbCommunityRegisterPlantToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCommunityRegisterPlantConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_communityRegisterPlantFragment_to_communityRegisterCompleteFragment)
        }
    }
}