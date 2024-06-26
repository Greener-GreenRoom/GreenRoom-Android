package com.greener.presentation.ui.home.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.greener.domain.model.ActionTodo
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentHomeBinding
import com.greener.presentation.model.UiState
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.greenroom.GreenRoomFragment
import com.greener.presentation.ui.home.greenroom.GreenRoomViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate,
) {
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserGreenRoomsInfo()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.myGreenRooms.collect {
                binding.vm = viewModel
                setFABClickEvent()
                observeFAB()
                setGreenRoomViewPager()
                setBottomProfileAdapter()
            }
        }
    }

    override fun initListener() {
        observeUiState()
        binding.viewHomeWallpaper.setOnClickListener {
            viewModel.setIsFabOpen()
        }
        binding.tvHomeActionComplete.setOnClickListener {
            viewModel.setIsFabOpen()
            completeAllTodoAtGreenRoom()
        }
        binding.includeHomeBottomSheet.btnBottomSheetHomeAddButton.setOnClickListener {
            moveToRegistration()
        }
        binding.includeHomeBottomSheet.tvBottomSheetHomeAddPlant.setOnClickListener {
            moveToRegistration()
        }
        binding.tbHomeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_home_toolbar_my_info -> {
                    moveToMyPage()
                    true
                }

                else -> {
                    true
                }
            }
        }
        viewModel.initFab()
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect {
                if (it is UiState.Error) {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onChangedTodo(position: Int, actionTodo: ActionTodo) {
        viewModel.changeTodo(position, actionTodo)
        binding.includeHomeBottomSheet.rvBottomSheetHomeProfile.adapter!!.notifyItemChanged(position)
    }

    private fun setGreenRoomViewPager() {
        binding.vpHomeGreenRoom.adapter = GreenRoomViewPagerAdapter(
            this@HomeFragment,
            viewModel.myGreenRooms.value,

        ) { position, actionTodo ->
            onChangedTodo(position, actionTodo)
        }
        binding.vpHomeGreenRoom.isUserInputEnabled = false
        // binding.vpHomeGreenRoom.offscreenPageLimit = 100
        // binding.vpHomeGreenRoom.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun setBottomProfileAdapter() {
        binding.includeHomeBottomSheet.rvBottomSheetHomeProfile.adapter =
            ProfileRVAdapter(
                viewModel.myGreenRooms.value,
                viewModel.currentGreenRoom.value,
            ) { onClickProfile(it) }
    }

    private fun onClickProfile(position: Int) {
        binding.vpHomeGreenRoom.currentItem = position
        viewModel.updateCurrentGreenRoom(position)
        select(position)
        scrollToCenter(position)
    }

    private fun scrollToCenter(position: Int) {
        val recyclerView = binding.includeHomeBottomSheet.rvBottomSheetHomeProfile
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        layoutManager.scrollToPositionWithOffset(position, recyclerView.width / 2)
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
        binding.viewHomeWallpaper.visibility = View.GONE
    }

    private fun moveToRegistration() {
        findNavController().navigate(R.id.action_homeFragment_to_registrationSearchFragment)
    }
    private fun moveToMyPage() {
        findNavController().navigate(R.id.action_homeFragment_to_myPageMainFragment)
    }

    private fun completeAllTodoAtGreenRoom() {
        val currentItem = binding.vpHomeGreenRoom.currentItem
        val myFragment =
            childFragmentManager.findFragmentByTag("f$currentItem") as GreenRoomFragment
        myFragment.completeAllTodo()
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

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }
}
