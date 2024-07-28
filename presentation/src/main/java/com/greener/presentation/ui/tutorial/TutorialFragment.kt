package com.greener.presentation.ui.tutorial

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.greener.domain.model.ActionTodo
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentTutorialBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.dialog.ActionDialog
import com.greener.presentation.ui.main.MainActivity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class TutorialFragment : BaseFragment<FragmentTutorialBinding>(
    FragmentTutorialBinding::inflate
) {
    val viewModel: TutorialViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionTodo = ActionTodo.WATERING
        val fadeIn = AnimationUtils.loadAnimation(requireActivity(), R.anim.fade_in)
        binding.tvTutorialScript.startAnimation(fadeIn)
    }

    override fun initListener() {
        observeSteps()
        binding.btnTutorial.setOnClickListener {
            viewModel.nextStep()
        }
    }

    private fun observeSteps() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.step.collect {
                when (it) {
                    2 -> {
                        setStep2()
                    }

                    3 -> {
                        setStep3()
                    }

                    4 -> {
                        setStep4()
                    }

                    5 -> {
                        setStep5()
                    }

                    6 -> {
                        moveToMainActivity()
                    }
                }
            }
        }
    }

    private fun setStep2() {
        fadeOutInTextView(getString(R.string.tutorial_script2))
        binding.ivTutorialGreeny.setImageResource(R.drawable.img_greeny_sad)
        binding.includeTutorialBalloon.root.visibility = View.VISIBLE
    }

    private fun setStep3() {
        binding.btnTutorial.isEnabled = false
        binding.viewTutorialWallpaper.visibility = View.VISIBLE
        fadeOutInTextView(getString(R.string.tutorial_script3))
        binding.includeTutorialBalloon.root.setOnClickListener {
            showActionDialog(ActionTodo.WATERING)
        }
    }

    private fun setStep4() {
        binding.btnTutorial.isEnabled = true
        binding.viewTutorialWallpaper.visibility = View.GONE
        binding.includeTutorialBalloon.root.visibility = View.INVISIBLE
        binding.ivTutorialPlantFace.setImageResource(R.drawable.asset_face_happy)
        binding.ivTutorialGreeny.setImageResource(R.drawable.img_greeny_happy)
        val spannableString = getSpannableString(
            STEP4_GREEN_TEXT_START,
            STEP4_GREEN_TEXT_END,
            getString(R.string.tutorial_script4)
        )
        fadeOutInTextView(spannableString)
        binding.includeTutorialToast.root.visibility = View.VISIBLE


    }

    private fun setStep5() {
        binding.ivTutorialGreeny.setImageResource(R.drawable.img_greeny_wink)
        val spannableString = getSpannableString(
            STEP5_GREEN_TEXT_START,
            STEP5_GREEN_TEXT_END,
            getString(R.string.tutorial_script5)
        )
        fadeOutInTextView(spannableString)
        binding.btnTutorial.setText(R.string.tutorial_go_start)
        binding.includeTutorialToast.root.visibility = View.INVISIBLE

    }

    private fun moveToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun showActionDialog(actionTodo: ActionTodo) {
        val dialog = ActionDialog(requireActivity(), actionTodo)
        dialog.setItemClickListener(object : ActionDialog.ClickListener {
            override fun onClick() {
                viewModel.nextStep()
            }
        })
        dialog.show()
    }

    private fun getSpannableString(start: Int, end: Int, text: String): SpannableString {

        val spannableString = SpannableString(text)

        val color = ContextCompat.getColor(requireActivity(), R.color.green300)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannableString
    }

    private fun fadeOutInTextView(newText: String) {
        // Fade out animation
        val fadeOut = ObjectAnimator.ofFloat(binding.tvTutorialScript, "alpha", 1f, 0f)
        fadeOut.duration = 300 // duration in milliseconds

        // Fade in animation
        val fadeIn = ObjectAnimator.ofFloat(binding.tvTutorialScript, "alpha", 0f, 1f)
        fadeIn.duration = 300 // duration in milliseconds

        // Update text between fade out and fade in
        fadeOut.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                binding.tvTutorialScript.text = newText
            }
        })

        // Combine fade out and fade in
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(fadeOut, fadeIn)
        animatorSet.start()
    }

    private fun fadeOutInTextView(newText: SpannableString) {
        // Fade out animation
        val fadeOut = ObjectAnimator.ofFloat(binding.tvTutorialScript, "alpha", 1f, 0f)
        fadeOut.duration = 400 // duration in milliseconds

        // Fade in animation
        val fadeIn = ObjectAnimator.ofFloat(binding.tvTutorialScript, "alpha", 0f, 1f)
        fadeIn.duration = 400 // duration in milliseconds

        // Update text between fade out and fade in
        fadeOut.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                binding.tvTutorialScript.text = newText
            }
        })

        // Combine fade out and fade in
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(fadeOut, fadeIn)
        animatorSet.start()
    }
    companion object {
        const val STEP4_GREEN_TEXT_START = 13
        const val STEP4_GREEN_TEXT_END = 18
        const val STEP5_GREEN_TEXT_START = 5
        const val STEP5_GREEN_TEXT_END = 15
    }
}