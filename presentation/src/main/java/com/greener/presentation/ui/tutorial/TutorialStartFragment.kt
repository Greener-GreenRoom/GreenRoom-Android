package com.greener.presentation.ui.tutorial

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
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
        changeTextColor()
        val fadeIn = AnimationUtils.loadAnimation(requireActivity(), R.anim.fade_in)
        binding.tvTutorialStartScript.startAnimation(fadeIn)
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

    private fun changeTextColor() {
        val text = binding.tvTutorialStartScript.text.toString()

        val spannableString = SpannableString(text)

        val start = GREEN_TEXT_START
        val end = GREEN_TEXT_END
        val color = ContextCompat.getColor(requireActivity(), R.color.green300)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvTutorialStartScript.text = spannableString
    }

    companion object {
        const val GREEN_TEXT_START = 9
        const val GREEN_TEXT_END = 12
    }

}