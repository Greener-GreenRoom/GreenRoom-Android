package com.greener.presentation.ui.home.register.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greener.presentation.databinding.FragmentPlantRegistrationSearchBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.register.InitRegistrationIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationSearchFragment: BaseFragment<FragmentPlantRegistrationSearchBinding> (
    FragmentPlantRegistrationSearchBinding::inflate
) {
    private val viewModel: RegistrationSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        InitRegistrationIndicator.initRegistrationIndicator(
            binding.includePlantRegistrationSearchIndicator,
            requireContext(),
            SEARCH_POSITION
        )
    }

    override fun initListener() {
        binding.tbPlantRegistrationSearch.setNavigationOnClickListener{
            findNavController().popBackStack()
        }

        // 임시 이동 버튼
        binding.btnPlantRegistrationSearchGoNext.setOnClickListener{
            val action = RegistrationSearchFragmentDirections.actionRegistrationSearchFragmentToRegistrationNicknameImageFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        private const val SEARCH_POSITION = 1
    }
}
