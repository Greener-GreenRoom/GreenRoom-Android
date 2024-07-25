package com.greener.presentation.ui.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
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
        binding.tvTutorialScript.setText(R.string.tutorial_script2)
        binding.ivTutorialGreeny.setImageResource(R.drawable.img_greeny_sad)
        binding.includeTutorialBalloon.root.visibility = View.VISIBLE
    }

    private fun setStep3() {
        binding.btnTutorial.isEnabled = false
        binding.viewTutorialWallpaper.visibility = View.VISIBLE
        binding.tvTutorialScript.setText(R.string.tutorial_script3)
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
        binding.tvTutorialScript.setText(R.string.tutorial_script4)
        binding.includeTutorialToast.root.visibility = View.VISIBLE
        // 씨앗 3개 초록색으로
    }

    private fun setStep5() {
        binding.ivTutorialGreeny.setImageResource(R.drawable.img_greeny_wink)
        binding.tvTutorialScript.setText(R.string.tutorial_script5)
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
}