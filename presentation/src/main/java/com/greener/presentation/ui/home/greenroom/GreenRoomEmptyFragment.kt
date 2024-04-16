package com.greener.presentation.ui.home.greenroom

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentGreenRoomEmptyBinding
import com.greener.presentation.ui.base.BaseFragment

class GreenRoomEmptyFragment : BaseFragment<FragmentGreenRoomEmptyBinding>(
    FragmentGreenRoomEmptyBinding::inflate,
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initListener() {
        binding.btnGreenRoomEmptyRegister.setOnClickListener {
            moveToRegistration()
        }
    }

    private fun moveToRegistration() {
        findNavController().navigate(R.id.action_homeFragment_to_registrationSearchFragment)
    }
}
