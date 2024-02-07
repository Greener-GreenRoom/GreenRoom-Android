package com.greener.presentation.ui.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.greener.presentation.databinding.FragmentHomeBinding
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        setGreenRoomViewPager()
        setBottomProfileAdapter()
        setFABClickEvent()
        observeFAB()

        initListener()


    }

    override fun initListener() {
        binding.viewBackground.setOnClickListener {
            viewModel.setIsFabOpen()
        }

        binding.tvComplete.setOnClickListener {
            viewModel.setIsFabOpen()
            showActionDialog()
        }
        viewModel.initFab()
    }

    override fun onStart() {
        super.onStart()
        //viewModel.initFab()
    }

    private fun setGreenRoomViewPager() {
        binding.vpGreenRoom.adapter = GreenRoomViewPagerAdapter(
            this,
            viewModel.myPlants.value
        )
        binding.vpGreenRoom.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        /**
         * 하단 코드는 옵션
         * 적용할지 말지는 선택입니다.
         */

        binding.vpGreenRoom.isUserInputEnabled = false
        binding.vpGreenRoom.setPageTransformer(ZoomOutPageTransformer())

    }

    private fun setBottomProfileAdapter() {
        binding.bottomSheet.rvProfile.adapter =
            ProfileRVAdapter(
                viewModel.myPlants.value,
                viewModel.currentPlant.value,
                { onClickProfile(it) },
                { unSelect(it) })
    }

    private fun onClickProfile(position: Int) {
        binding.vpGreenRoom.currentItem = position
        viewModel.currentPlant.value = viewModel.myPlants.value[position]
        select(position)
    }
    private fun select(position: Int) {
        val viewHolder =
            binding.bottomSheet.rvProfile.findViewHolderForAdapterPosition(position) as ProfileRVAdapter.ProfileViewHolder

        viewHolder.binding.imgProfile.strokeWidth =5f
    }
    private fun unSelect(position: Int) {
        val viewHolder =
            binding.bottomSheet.rvProfile.findViewHolderForAdapterPosition(position) as ProfileRVAdapter.ProfileViewHolder

        viewHolder.binding.imgProfile.strokeWidth =0f
    }

    private fun showActionDialog() {
        val dialog = ActionDialog(requireActivity())

        dialog.setItemClickListener(object : ActionDialog.ClickListener {
            override fun onClick() {

            }
        })
        dialog.show()
    }

    private fun observeFAB() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFabOpen.collect {
                    if (it) {
                        openActionMenu()
                    } else {
                        shutActionMenu()
                    }
                }
            }
        }
    }

    private fun setFABClickEvent() {
        binding.btnAction.setOnClickListener {
            viewModel.setIsFabOpen()
        }
    }

    private fun openActionMenu() {
        ObjectAnimator.ofFloat(binding.btnAction, View.ROTATION, 0f, 45f).apply { start() }
        binding.layoutActionDialog.fadeIn()
        binding.viewBackground.visibility = View.VISIBLE
    }

    private fun shutActionMenu() {
        ObjectAnimator.ofFloat(binding.btnAction, View.ROTATION, 45f, 0f).apply { start() }
        binding.layoutActionDialog.visibility = View.GONE
        //binding.layoutActionDialog.fadeOut(50)
        binding.viewBackground.visibility = View.GONE
    }

    private fun View.fadeIn(duration: Long = 500): ViewPropertyAnimator {
        this.alpha = 0f
        this.visibility = View.VISIBLE
        return animate()
            .alpha(1f)
            .setDuration(duration)
    }

    private fun View.fadeOut(duration: Long = 100): ViewPropertyAnimator {
        return animate()
            .alpha(0f)
            .withEndAction { visibility = View.GONE }
            .setDuration(duration)
    }


    /**
     * 식물 전환 시 애니메이션 적용을 위한 코드
     * 옵션입니다.
     * 하단 코드는 줌인 줌아웃을 위한 코드이며, 다른 애니메이션을 적용할 수도 있습니다.
     */
    inner class ZoomOutPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }

                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }

                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }
}