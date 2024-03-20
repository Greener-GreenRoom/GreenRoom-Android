package com.greener.presentation.ui.home.registraion.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greener.presentation.databinding.FragmentPlantRegistrationSearchBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.registraion.InitRegistrationIndicator
import com.greener.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegistrationSearchFragment : BaseFragment<FragmentPlantRegistrationSearchBinding> (
    FragmentPlantRegistrationSearchBinding::inflate,
) {
    private val viewModel: RegistrationSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        InitRegistrationIndicator.initRegistrationIndicator(
            binding.includePlantRegistrationSearchIndicator,
            requireContext(),
            SEARCH_POSITION,
        )
    }

    override fun initListener() {
        binding.tbPlantRegistrationSearch.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.event.collectLatest { event ->
                handleEvent(event)
            }
        }
    }

    private fun handleEvent(event: RegistrationSearchViewModel.Event) {
        when (event) {
            is RegistrationSearchViewModel.Event.GoToRegistrationNicknameImage -> {
                val action = RegistrationSearchFragmentDirections.actionRegistrationSearchFragmentToRegistrationNicknameImageFragment(event.plantRegistrationInfo)
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        private const val SEARCH_POSITION = 1
    }
}
