package com.greener.presentation.ui.mypage.edit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.greener.presentation.R
import com.greener.presentation.databinding.BottomSheetImageBinding
import com.greener.presentation.databinding.BottomSheetPlantRegistrationImageBinding

class EditProfileModalBottomSheet constructor(
    val getImageFromPictures: () -> Unit,
    val takePhoto: () -> Unit,
): BottomSheetDialogFragment() {
    lateinit var binding: BottomSheetImageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = BottomSheetImageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnImageChoosePicture.setOnClickListener {
            getImageFromPictures()
            dismiss()
        }

        binding.btnImageTakePicture.setOnClickListener {
            takePhoto()
            dismiss()
        }


    }
}