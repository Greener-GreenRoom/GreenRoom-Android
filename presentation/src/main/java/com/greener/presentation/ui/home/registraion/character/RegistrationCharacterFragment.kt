package com.greener.presentation.ui.home.registraion.character

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.greener.presentation.databinding.FragmentPlantRegistrationCharacterBinding
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.registraion.InitRegistrationIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationCharacterFragment : BaseFragment<FragmentPlantRegistrationCharacterBinding>(
    FragmentPlantRegistrationCharacterBinding::inflate,
) {

    private val viewModel: RegistrationCharacterViewModel by viewModels()
    private val args: RegistrationCharacterFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        InitRegistrationIndicator.initRegistrationIndicator(
            binding.includePlantRegistrationCharacterLastIndicator,
            requireContext(),
            CHARACTER_POSITION,
        )

//        highlightNickname(args.nickname)
    }

    override fun initListener() {
        binding.tbPlantRegistrationCharacterLast.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

//    private fun highlightNickname(nickname: String) {
//        val mainText: String = requireContext().getString(R.string.plant_registration_character, nickname)
//        val spannableStringBuilder = SpannableStringBuilder(mainText)
//        spannableStringBuilder.apply {
//            setSpan(
//                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary)),
//                0,
//                nickname.length,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }
//        binding.tvPlantRegistrationCharacter.text = spannableStringBuilder
//    }

    companion object {
        private const val CHARACTER_POSITION = 5
    }
}
