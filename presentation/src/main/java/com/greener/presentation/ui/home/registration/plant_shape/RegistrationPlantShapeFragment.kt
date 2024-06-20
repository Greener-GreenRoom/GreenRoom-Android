package com.greener.presentation.ui.home.registration.plant_shape

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    private val registrationTypesAdapter = RegistrationTypesAdapter{ target ->
        viewModel.onChangeType(target)
    }
    private val registrationAllShapeAdapter = RegistrationAllShapeAdapter()
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
            viewModel.shapeDetailTypes.collectLatest {
                registrationTypesAdapter.submitList(it)
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.allPlantShape.collectLatest {
                registrationAllShapeAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPlantRegistrationShapeType.adapter = null
        binding.rvPlantRegistrationAllItem.adapter = null
        binding.rvPlantRegistrationDetailItem.adapter = null
    }


    private fun initRV() {
        binding.rvPlantRegistrationShapeType.run {
            adapter = registrationTypesAdapter
            addItemDecoration(SpaceDecoration(resources, leftDP = R.dimen.asset_detail_type_left_padding))
        }

        binding.rvPlantRegistrationAllItem.run {
            adapter = registrationAllShapeAdapter
            addItemDecoration(SpaceDecoration(resources, bottomDP = R.dimen.asset_all_view_bottom_padding))
        }
    }

    companion object {
        private const val CHARACTER_POSITION = 5
    }
}
