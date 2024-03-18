package com.greener.presentation.ui.diary

import androidx.fragment.app.viewModels
import com.greener.presentation.databinding.FragmentDiaryBinding
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryFragment : BaseFragment<FragmentDiaryBinding> (
    FragmentDiaryBinding::inflate,
) {
    private val viewModel: DiaryViewModel by viewModels()
}
