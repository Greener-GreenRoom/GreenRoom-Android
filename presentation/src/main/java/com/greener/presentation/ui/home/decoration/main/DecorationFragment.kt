package com.greener.presentation.ui.home.decoration.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentDecorationBinding
import com.greener.domain.model.asset.AssetType
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.util.SpaceDecoration
import com.greener.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DecorationFragment : BaseFragment<FragmentDecorationBinding>(
    FragmentDecorationBinding::inflate,
) {
    private val viewModel: DecorationViewModel by viewModels()

    private val decorationAssetDetailTypeAdapter = DecorationAssetDetailTypeAdapter{
        viewModel.changeAssetDetailTypeCheck(it)
    }
    private val decorationAllViewAdapter = DecorationAssetAllViewAdapter()
    private val decorationViewAdapter = DecorationAssetViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDecorationPreviewViewPager()
        initRecyclerView()
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.assetDetailTypes.collectLatest {
                decorationAssetDetailTypeAdapter.submitList(it)
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.choiceAllViewAssets.collectLatest {
                decorationAllViewAdapter.submitList(it)
            }
        }
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.choiceViewAssets.collectLatest {
                decorationViewAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvDecorationAssetDetailType.adapter = null
        binding.rvDecorationAssetDetailItem.adapter = null
        binding.rvDecorationAssetDetailAllItem.adapter = null
    }

    private fun initRecyclerView() {
        val flexboxLayout = FlexboxLayoutManager(requireContext())
        flexboxLayout.justifyContent = JustifyContent.CENTER

        binding.rvDecorationAssetDetailType.run {
            adapter = decorationAssetDetailTypeAdapter
            addItemDecoration(SpaceDecoration(resources, leftDP = R.dimen.asset_detail_type_left_padding))
        }

        binding.rvDecorationAssetDetailAllItem.run {
            adapter = decorationAllViewAdapter
            addItemDecoration(SpaceDecoration(resources, bottomDP = R.dimen.asset_all_view_bottom_padding))
        }

        binding.rvDecorationAssetDetailItem.run {
            layoutManager = flexboxLayout
            adapter = decorationViewAdapter
            addItemDecoration(SpaceDecoration(resources, rightDP = R.dimen.asset_view_left_padding, bottomDP = R.dimen.asset_view_bottom_padding))
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initDecorationPreviewViewPager() {
        val viewPager = binding.vpDecorationPreview
        val tabLayout = binding.tblDecorationAssetType

        viewPager.apply {
            adapter = DecorationPreviewAdapter(childFragmentManager, lifecycle)
            isUserInputEnabled = false
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                AssetType.PLANT_SHAPE.ordinal -> { tab.text = getString(R.string.decoration_plant_shape) }
                AssetType.PLANT_ACCESSORY.ordinal -> { tab.text = getString(R.string.decoration_plant_accessory) }
                AssetType.BACKGROUND_ACCESSORY.ordinal -> { tab.text = getString(R.string.decoration_background_accessory) }
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab?.position) {
                    AssetType.PLANT_SHAPE.ordinal -> {
                        binding.tbDecoration.apply {
                            setTitleTextColor(requireContext().getColor(R.color.white))
                            setNavigationIconTint(requireContext().getColor(R.color.white))
                        }
                        viewModel.onChangeAsseType(AssetType.PLANT_SHAPE)
                    }
                    AssetType.PLANT_ACCESSORY.ordinal -> {
                        binding.tbDecoration.apply {
                            setTitleTextColor(requireContext().getColor(R.color.white))
                            setNavigationIconTint(requireContext().getColor(R.color.white))
                        }
                        viewModel.onChangeAsseType(AssetType.PLANT_ACCESSORY)
                    }
                    AssetType.BACKGROUND_ACCESSORY.ordinal -> {
                        binding.tbDecoration.apply {
                            setTitleTextColor(requireContext().getColor(R.color.gray700))
                            setNavigationIconTint(requireContext().getColor(R.color.gray700))
                        }
                        viewModel.onChangeAsseType(AssetType.BACKGROUND_ACCESSORY)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}
