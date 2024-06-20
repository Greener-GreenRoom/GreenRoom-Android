package com.greener.presentation.ui.home.registration.water

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.greener.presentation.R
import com.greener.presentation.databinding.FragmentPlantRegistrationWaterBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.registration.InitRegistrationIndicator
import com.greener.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationWaterFragment : BaseFragment<FragmentPlantRegistrationWaterBinding>(
    FragmentPlantRegistrationWaterBinding::inflate,
) {
    @Inject
    lateinit var viewModelFactory: RegistrationWaterViewModel.PlantRegistrationInfoFactory

    private val args: RegistrationWaterFragmentArgs by navArgs()
    private val viewModel: RegistrationWaterViewModel by viewModels{
        RegistrationWaterViewModel.provideFactory(viewModelFactory, args.PlantRegistraionInfo)
    }
    private val datePicker = MaterialDatePicker.Builder.datePicker().build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        InitRegistrationIndicator.initRegistrationIndicator(
            binding.includePlantRegistrationWaterIndicator,
            requireContext(),
            WATERING_POSITION,
        )
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.viewLastWatering.collectLatest {date ->
                if (date.isNotBlank()) {
                    binding.btnPlantRegistrationWaterChooseDate.apply {
                        text = date
                        iconTint = requireContext().getColorStateList(R.color.gray700)
                        setTextColor(requireContext().getColor(R.color.gray700))
                    }
                } else {
                    binding.btnPlantRegistrationWaterChooseDate.apply {
                        text = getString(R.string.util_choose_date)
                        iconTint = requireContext().getColorStateList(R.color.primary)
                        setTextColor(requireContext().getColor(R.color.primary))
                    }
                }
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.event.collectLatest { event ->
                handleEvent(event)
            }
        }
    }

    override fun initListener() {
        binding.tbPlantRegistrationWater.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.tePlantRegistrationWaterCycle.addTextChangedListener { duration ->
            duration?.let {
                viewModel.onUpdateDuration(duration.toString())
            }
        }
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())

        if (datePicker.isAdded.not()) {
            datePicker.show(childFragmentManager, TAG)
        }
        datePicker.addOnPositiveButtonClickListener {
            viewModel.onUpdateSelectDate(it)
        }
    }

    private fun handleEvent(event: RegistrationWaterViewModel.Event) {
        when(event) {
            is RegistrationWaterViewModel.Event.MoveToPlantShape -> {
                val action = RegistrationWaterFragmentDirections.actionRegistrationWaterFragmentToRegistrationPlantShapeFragment(
                    event.plantRegistrationInfo
                )
                findNavController().navigate(action)
            }
            RegistrationWaterViewModel.Event.ShowDatePicker -> {
                showDatePicker()
            }
            RegistrationWaterViewModel.Event.AllInputState -> {
                binding.btnPlantRegistrationWaterGoNext.setBackgroundColor(requireContext().getColor(R.color.primary))
                binding.btnPlantRegistrationWaterGoNext.isClickable = true
            }
            RegistrationWaterViewModel.Event.PartialInputState -> {
                binding.btnPlantRegistrationWaterGoNext.setBackgroundColor(requireContext().getColor(R.color.gray200))
                binding.btnPlantRegistrationWaterGoNext.isClickable = false
            }
            RegistrationWaterViewModel.Event.InputMaxDuration -> {
                Toast.makeText(requireContext(), getText(R.string.warning_max_water_duration), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val WATERING_POSITION = 3
        private const val TAG = "tag"

    }
}
