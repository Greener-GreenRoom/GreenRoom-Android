package com.greener.presentation.ui.mypage.level

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.greener.domain.model.GreenRoomItem
import com.greener.presentation.databinding.FragmentMyPageLevelBinding
import com.greener.presentation.model.UiState
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageLevelFragment : BaseFragment<FragmentMyPageLevelBinding>(
    FragmentMyPageLevelBinding::inflate
) {
    private val viewModel: MyPageLevelViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        softInputAdjustResize()
    }

    override fun initListener() {
        observeApiResult()
        binding.tbMyPageLevelToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeApiResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if(it == UiState.Success) {
                        binding.vm = viewModel
                        setProgress()
                        if(viewModel.myLevelInfo.value !=null) {
                            setNextRewardItemAdapter(viewModel.myLevelInfo.value!!.greenRoomItems)
                        }
                    } else if(it is UiState.Error) {
                        Toast.makeText(requireActivity(),it.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    private fun setNextRewardItemAdapter(greenRoomItems:List<GreenRoomItem>) {
        binding.rvMyPageLevelNextRewardItem.adapter = NextRewardRVAdapter(greenRoomItems)
    }


    private fun setProgress() {
        val anim = AnimateProgressBar(
            binding.pbMyPageLevelProgressbar,
            0f,
            viewModel.getProgress().toFloat()
        )
        anim.duration = 500
        binding.pbMyPageLevelProgressbar.startAnimation(anim)
    }

    private fun softInputAdjustResize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(false)
            binding.root.setOnApplyWindowInsetsListener { _, insets ->
                val topInset = insets.getInsets(WindowInsets.Type.statusBars()).top
                val imeHeight = insets.getInsets(WindowInsets.Type.ime()).bottom
                val navigationHeight = insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                val bottomInset = if (imeHeight == 0) navigationHeight else imeHeight
                binding.root.setPadding(0, topInset, 0, bottomInset)
                insets
            }
        } else {
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }
}

class AnimateProgressBar(
    private var progressBar: ProgressBar,
    private var from: Float,
    private var to: Float
) : Animation() {
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value = from + (to - from) * interpolatedTime
        progressBar.progress = value.toInt()
    }
}