package com.greener.presentation.ui.tutorial

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentTutorialStartBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.main.MainActivity
import com.greener.presentation.ui.mypage.main.LogoutDialog

class TutorialStartFragment : BaseFragment<FragmentTutorialStartBinding>(
    FragmentTutorialStartBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTutorialStartSkip.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun initListener() {
        binding.btnTutorialStart.setOnClickListener {
            findNavController().navigate(R.id.action_tutorialStartFragment_to_tutorialFragment)
        }
        binding.tvTutorialStartSkip.setOnClickListener {
            showSkipDialog()
        }
    }

    private fun showSkipDialog() {
        val dialog = SkipDialog(requireActivity())

        dialog.setItemClickListener(object : SkipDialog.ClickListener {
            override fun onClick() {
                moveToMainActivity()
            }
        })
        dialog.show()
    }

    private fun moveToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}