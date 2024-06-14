package com.greener.presentation.ui.home.decoration.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.tabs.TabLayout
import com.greener.domain.model.asset.AssetType
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentDecorationBinding
import com.greener.presentation.model.decoration.DecorationTabState
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.decoration.background.DecorationBackgroundPreviewFragment
import com.greener.presentation.ui.home.decoration.main.adapter.DecorationAssetAllViewAdapter
import com.greener.presentation.ui.home.decoration.main.adapter.DecorationAssetDetailTypeAdapter
import com.greener.presentation.ui.home.decoration.main.adapter.DecorationAssetViewAdapter
import com.greener.presentation.ui.home.decoration.plant.DecorationPlantPreviewFragment
import com.greener.presentation.util.SpaceDecoration
import com.greener.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DecorationFragment : BaseFragment<FragmentDecorationBinding>(
    FragmentDecorationBinding::inflate,
) {
    private val viewModel: DecorationViewModel by viewModels()

    private val decorationAssetDetailTypeAdapter = DecorationAssetDetailTypeAdapter{ type, target ->
        viewModel.changeAssetDetailType(type, target)
    }
    private val decorationAllViewAdapter = DecorationAssetAllViewAdapter(
        { info, _ -> viewModel.updatePlantShapeAsset( targetPlantShape = info, isAll = true) },
        { info, type -> viewModel.updatePlantAccessoryAsset( accessoryType = type, targetPlantAccessory = info, isAll = true )},
        { info, type -> viewModel.updateBackgroundAccessoryAsset( accessoryType = type, targetPlantAccessory = info, isAll = true )},
        0
    )
    private val decorationViewAdapter = DecorationAssetViewAdapter(
        { info, type -> viewModel.updatePlantShapeAsset(plantType = type, targetPlantShape = info, isAll = false) },
        { info, type -> viewModel.updatePlantAccessoryAsset( accessoryType = type, targetPlantAccessory = info, isAll = false)},
        { info, type -> viewModel.updateBackgroundAccessoryAsset( accessoryType = type, targetPlantAccessory = info, isAll = false )},
        0
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initTabLayout()
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.assetDetailTypes.collectLatest {
                decorationAssetDetailTypeAdapter.submitList(it)
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.myLevel.collectLatest {
                decorationViewAdapter.myLevel = it
                decorationAllViewAdapter.myLevel = it
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

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.totalDecorationInfo.collectLatest { info ->
                if(info.decorationState == DecorationTabState.PLANT_DECORATION) {
                    updatePreview(DecorationPlantPreviewFragment(info.decorationDetailInfo!!))
                } else if (info.decorationState == DecorationTabState.BACKGROUND_DECORATION) {
                    updatePreview(DecorationBackgroundPreviewFragment(info.decorationDetailInfo!!))
                }
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
        val flexboxLayout = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
        }

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

    private fun initTabLayout() {
        val tabLayout = binding.tblDecorationAssetType
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    AssetType.PLANT_SHAPE.ordinal -> {
                        binding.tbDecoration.apply {
                            setTitleTextColor(requireContext().getColor(R.color.white))
                            setNavigationIconTint(requireContext().getColor(R.color.white))
                        }
                        viewModel.onChangeAssetType(AssetType.PLANT_SHAPE)
                    }
                    AssetType.PLANT_ACCESSORY.ordinal -> {
                        binding.tbDecoration.apply {
                            setTitleTextColor(requireContext().getColor(R.color.white))
                            setNavigationIconTint(requireContext().getColor(R.color.white))
                        }
                        viewModel.onChangeAssetType(AssetType.PLANT_ACCESSORY)
                    }
                    AssetType.BACKGROUND_ACCESSORY.ordinal -> {
                        binding.tbDecoration.apply {
                            setTitleTextColor(requireContext().getColor(R.color.gray700))
                            setNavigationIconTint(requireContext().getColor(R.color.gray700))
                        }
                        viewModel.onChangeAssetType(AssetType.BACKGROUND_ACCESSORY)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun updatePreview(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.apply {
            replace(binding.flDecorationPreview.id, fragment)
            commit()
        }
    }
}
