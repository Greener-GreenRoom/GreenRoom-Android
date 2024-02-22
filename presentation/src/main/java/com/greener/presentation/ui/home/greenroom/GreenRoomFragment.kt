package com.greener.presentation.ui.home.greenroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.greener.domain.model.ExampleModel
import com.greener.presentation.databinding.FragmentGreenRoomBinding

import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GreenRoomFragment constructor(val myPlant:ExampleModel) : BaseFragment<FragmentGreenRoomBinding>(
    FragmentGreenRoomBinding::inflate
) {

    @Inject
    lateinit var greenRoomViewModelFactory: GreenRoomViewModel.GreenRoomViewModelFactory

    private val viewModel: GreenRoomViewModel by viewModels {
        GreenRoomViewModel.providesFactory(
            assistedFactory = greenRoomViewModelFactory,
            plant = myPlant
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPlant = viewModel.myPlant.value
        binding.lifecycleOwner = this
    }
}