package com.greener.presentation.ui.home.registration.plant_shape

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentPlantRegistrationPlantShapeBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.registration.InitRegistrationIndicator
import com.greener.presentation.util.SpaceDecoration
import com.greener.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationPlantShapeFragment : BaseFragment<FragmentPlantRegistrationPlantShapeBinding>(
    FragmentPlantRegistrationPlantShapeBinding::inflate,
) {
    @Inject
    lateinit var viewModelFactory: RegistrationPlantShapeViewModel.PlantRegistrationInfoFactory

    private val args: RegistrationPlantShapeFragmentArgs by navArgs()
    private val viewModel: RegistrationPlantShapeViewModel by viewModels {
        RegistrationPlantShapeViewModel.provideFactory(viewModelFactory, args.PlantRegistrationInfo)
    }

    private val registrationTypesAdapter = RegistrationTypesAdapter { target ->
        viewModel.onChangeType(target)
    }
    private val registrationAllShapeAdapter = RegistrationAllShapeAdapter { info, _ ->
        viewModel.updatePlantShapeAsset(targetPlantShape = info, isAll = true)
    }
    private val registrationShapeAdapter = RegistrationShapeAdapter { info, type ->
        viewModel.updatePlantShapeAsset(plantType = type, targetPlantShape = info, isAll = false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        InitRegistrationIndicator.initRegistrationIndicator(
            binding.includePlantRegistrationCharacterLastIndicator,
            requireContext(),
            CHARACTER_POSITION,
        )

        initRV()
    }

    override fun initListener() {
        binding.tbPlantRegistrationCharacterLast.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.choicePlantShape.collectLatest {
                Glide.with(requireContext())
                    .load(it?.drawableID)
                    .into(binding.ivPlantRegistrationCharacterPreview)
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.shapeDetailTypes.collectLatest {
                registrationTypesAdapter.submitList(it)
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.allPlantShape.collectLatest {
                registrationAllShapeAdapter.submitList(it)
            }
        }
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.plantShape.collectLatest {
                registrationShapeAdapter.submitList(it)
            }
        }
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.event.collectLatest { event ->
                handleEvent(event)
            }
        }
    }

    override fun onDestroyView() {
        binding.rvPlantRegistrationShapeType.adapter = null
        binding.rvPlantRegistrationAllItem.adapter = null
        binding.rvPlantRegistrationDetailItem.adapter = null
        super.onDestroyView()
    }

    private fun initRV() {
        val flexboxLayout = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
        }

        binding.rvPlantRegistrationShapeType.run {
            adapter = registrationTypesAdapter
            addItemDecoration(SpaceDecoration(resources, leftDP = R.dimen.asset_detail_type_left_padding))
        }

        binding.rvPlantRegistrationAllItem.run {
            adapter = registrationAllShapeAdapter
            addItemDecoration(SpaceDecoration(resources, bottomDP = R.dimen.asset_all_view_bottom_padding))
        }

        binding.rvPlantRegistrationDetailItem.run {
            layoutManager = flexboxLayout
            adapter = registrationShapeAdapter
            addItemDecoration(SpaceDecoration(resources, rightDP = R.dimen.asset_view_left_padding, bottomDP = R.dimen.asset_view_bottom_padding))
        }
    }

    private fun handleEvent(event: RegistrationPlantShapeViewModel.Event) {
        when (event) {
            is RegistrationPlantShapeViewModel.Event.MoveToComplete -> {
                val action = RegistrationPlantShapeFragmentDirections.actionRegistrationPlantShapeFragmentToRegistrationCompleteFragment(
                    event.plantRegistrationInfo,
                )
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        private const val CHARACTER_POSITION = 5
    }
}
