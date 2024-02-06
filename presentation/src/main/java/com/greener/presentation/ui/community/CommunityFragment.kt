package com.greener.presentation.ui.community

import androidx.fragment.app.viewModels
import com.greener.presentation.databinding.FragmentCommunityBinding
import com.greener.presentation.ui.base.BaseFragment

class CommunityFragment : BaseFragment<FragmentCommunityBinding> (
    FragmentCommunityBinding::inflate
) {
    private val viewModel: CommunityViewModel by viewModels()
}
