package com.greener.presentation.ui.home.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.greener.domain.model.ActionTodo
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentHomeBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.dialog.ActionDialog
import com.greener.presentation.ui.home.greenroom.GreenRoomViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate,
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
        binding.viewHomeWallpaper.setOnClickListener {
            viewModel.setIsFabOpen()
        }

        binding.tvHomeActionComplete.setOnClickListener {
            viewModel.setIsFabOpen()
            showActionDialog()
        }
        viewModel.initFab()

        binding.includeHomeBottomSheet.tvBottomSheetHomeAddPlant.setOnClickListener {
            moveToRegisterSearchFragment()
        }
        binding.includeHomeBottomSheet.btnBottomSheetHomeAddButton.setOnClickListener {
            moveToRegisterSearchFragment()
        }
    }

    private fun moveToRegisterSearchFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_registrationSearchFragment)
    }

    private fun setGreenRoomViewPager() {
        binding.vpHomeGreenRoom.adapter = GreenRoomViewPagerAdapter(
            this,
            viewModel.myPlants.value,
        )
        binding.vpHomeGreenRoom.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        /**
         * 하단 코드는 옵션
         * 적용할지 말지는 선택입니다.
         */

        binding.vpHomeGreenRoom.isUserInputEnabled = false
        binding.vpHomeGreenRoom.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun setBottomProfileAdapter() {
        binding.includeHomeBottomSheet.rvBottomSheetHomeProfile.adapter =
            ProfileRVAdapter(
                viewModel.myPlants.value,
                viewModel.currentPlant.value,
                { onClickProfile(it) },
                { unSelect(it) },
            )
    }

    private fun onClickProfile(position: Int) {
        binding.vpHomeGreenRoom.currentItem = position
        viewModel.currentPlant.value = viewModel.myPlants.value[position]
        select(position)
    }

    private fun select(position: Int) {
        val viewHolder =
            binding.includeHomeBottomSheet.rvBottomSheetHomeProfile.findViewHolderForAdapterPosition(
                position,
            ) as ProfileRVAdapter.ProfileViewHolder
        viewHolder.binding.ivItemProfileBackground.setImageResource(R.drawable.img_profile_background_circle_selected)
        viewHolder.binding.tvItemProfilePlantName.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.green300,
            ),
        )
    }

    private fun unSelect(position: Int) {
        val viewHolder =
            binding.includeHomeBottomSheet.rvBottomSheetHomeProfile.findViewHolderForAdapterPosition(
                position,
            ) as ProfileRVAdapter.ProfileViewHolder

        viewHolder.binding.ivItemProfileBackground.setImageResource(R.drawable.img_profile_background_circle_non_selected)
        viewHolder.binding.tvItemProfilePlantName.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.gray700,
            ),
        )
    }

    private fun showActionDialog() {
        val dialog = ActionDialog(requireActivity(),ActionTodo.COMPLETE_ALL)

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
        binding.fabHomeActions.setOnClickListener {
            viewModel.setIsFabOpen()
        }
    }

    private fun openActionMenu() {
        ObjectAnimator.ofFloat(binding.fabHomeActions, View.ROTATION, 0f, 45f).apply { start() }
        binding.lyHomeActionList.fadeIn()
        binding.viewHomeWallpaper.visibility = View.VISIBLE
    }

    private fun shutActionMenu() {
        ObjectAnimator.ofFloat(binding.fabHomeActions, View.ROTATION, 45f, 0f).apply { start() }
        binding.lyHomeActionList.visibility = View.GONE
        // binding.layoutActionDialog.fadeOut(50)
        binding.viewHomeWallpaper.visibility = View.GONE
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
                        alpha = (
                            MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA))
                            )
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
