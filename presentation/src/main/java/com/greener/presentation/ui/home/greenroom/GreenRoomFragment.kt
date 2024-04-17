package com.greener.presentation.ui.home.greenroom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.greener.domain.model.ExampleModel
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import com.greener.presentation.databinding.FragmentGreenRoomBinding
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GreenRoomFragment constructor(val myGreenRoom: GreenRoomTotalInfo) : BaseFragment<FragmentGreenRoomBinding>(
    FragmentGreenRoomBinding::inflate,
) {
    init{
        Log.d("확인","GreenRoomFragemt: ${myGreenRoom}")
    }


    @Inject
    lateinit var greenRoomViewModelFactory: GreenRoomViewModel.GreenRoomViewModelFactory

    private val viewModel: GreenRoomViewModel by viewModels {
        GreenRoomViewModel.providesFactory(
            assistedFactory = greenRoomViewModelFactory,
            greenRoom = myGreenRoom,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.greenRoom = viewModel.myGreenRoom.value
        binding.lifecycleOwner = this
    }
}
